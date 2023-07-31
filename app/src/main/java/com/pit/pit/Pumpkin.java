package com.pit.pit;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class Pumpkin extends Application {

    static {
        System.loadLibrary("native-lib");
    }

    private int pe = -1;
    private boolean on;
    private boolean paused;
    private Handler handler;
    private Runnable r;
    private PumpkinUpdate updater;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Application", "onCreate");

        on = false;
        paused = true;
        handler = new Handler();
        installFiles();

        r = new Runnable() {
            public void run() {
                if (on && !paused && updater != null) {
                    updater.updateDisplay(false);
                    handler.postDelayed(this, 100);
                }
            }
        };
    }

    public void start(Bitmap bitmap, int width, int height) {
        PumpkinTask task = new PumpkinTask(this, bitmap, width, height);
        task.execute("");
    }

    public void pumpkinSetUpdate(PumpkinUpdate updater) {
        this.updater = updater;
    }

    public void pumpkinInit(int width, int height) {
        pe = pitInit(width, height);
    }

    public void pumpkinFinish() {
        pitFinish(pe);
    }

    public boolean pumpkinOn() {
        return on;
    }

    public void pumpkinSetOn(boolean on) {
        this.on = on;
    }

    public boolean pumpkinPaused() {
        return paused;
    }

    public void pumpkinSetPaused(boolean paused) {
        this.paused = paused;
        if (!paused) {
            Log.i("Pumpkin", "pumpkinSetPaused");
            handler.post(r);
        }
    }

    private void copyFile(int id, File dir, String name) throws IOException {
        if (dir.exists()) {
            File f = new File(dir, name);
            if (!f.exists() && f.createNewFile()) {
                FileOutputStream os = new FileOutputStream(f);
                Resources r = getResources();
                InputStream is = r.openRawResource(id);
                Log.i("Pumpkin", "copying file " + name);
                copyFile(is, os);
            }
        }
    }

    private void copyFile(File from, File to) throws IOException {
        if (to.exists()) {
            to.delete();
        }
        if (to.createNewFile()) {
            FileOutputStream os = new FileOutputStream(to);
            Resources r = getResources();
            InputStream is = new FileInputStream(from);
            Log.i("Pumpkin", "copying file " + from.getName());
            copyFile(is, os);
        }
    }

    private void copyFile(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        is.close();
        os.close();
    }

    private void installFiles() {
        try {
            // getFilesDir: /data/user/0/com.pit.pit/files
            // getDir("storage", Context.MODE_PRIVATE): /data/user/0/com.pit.pit/app_storage
            // files downloaded via Bluetooth: /storage/emulated/0/Download

            getDir("storage", Context.MODE_PRIVATE);
            getDir("card", Context.MODE_PRIVATE);
            getDir("registry", Context.MODE_PRIVATE);
            File dir = getDir("install", Context.MODE_PRIVATE);
            copyFile(R.raw.boot, dir, "BOOT.prc");
            copyFile(R.raw.launcher, dir, "Launcher.prc");
            copyFile(R.raw.minehunt, dir, "MINEHUNT.prc");

        } catch (Exception ex) {
            Log.e("Pumpkin", Objects.requireNonNull(ex.getMessage()));
        }
    }

    public void installFile(File from, String name) {
        if (name.endsWith(".prc") || name.endsWith(".pdb")) {
            File dir = getDir("install", Context.MODE_PRIVATE);
            File to = new File(dir, name);
            try {
                copyFile(from, to);
                if (pe != -1) {
                    pitDeploy("/app_install/" + name);
                }
            } catch (IOException ex) {
                Log.e("Pumpkin", ex.getMessage());
            }
        }
    }

    // application/vnd.palm

    /*
    private void sendFile(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        PackageManager pm = getPackageManager();
        List<ResolveInfo> appsList = pm.queryIntentActivities(intent, 0);
        if (appsList.size() > 0) {
            String packageName = null;
            String className = null;

            for (ResolveInfo info: appsList) {
                packageName = info.activityInfo.packageName;
                if (packageName.equals("com.android.bluetooth")) {
                    className = info.activityInfo.name;
                    break;
                }
            }
            if (className != null) {
                intent.setClassName(packageName, className);
                startActivity(intent);
            }
        }
    }
    */

    private native int pitInit(int width, int height);
    private native void pitFinish(int pe);
    private native void pitDeploy(String path);
    public native int pitUpdate(Bitmap bitmap, int invalidate);
    public native void pitPause(boolean paused);
    public native void pitKey(int key);
    public native void pitTouch(int action, int x, int y);
}