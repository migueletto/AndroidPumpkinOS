package com.pit.pit;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pumpkin extends Application {

    static {
        System.loadLibrary("native-lib");
    }

    private int pe = -1;
    private boolean on;
    private boolean paused;
    private boolean exited;
    private Handler handler;
    private Runnable r;
    private PumpkinUpdate updater;
    private ExecutorService exec;

    @Override
    public void onCreate() {
        super.onCreate();
        PumpkinLog.log(PumpkinLog.INFO, "Application", "onCreate");

        on = false;
        paused = true;
        exited = false;
        handler = new Handler();
        installFiles();

        r = new Runnable() {
            public void run() {
                if (updater != null && !paused) {
                    updater.updateDisplay(exited);
                    if (!exited) handler.postDelayed(this, 100);
                }
            }
        };
    }

    public void start(Bitmap bitmap) {
        Runnable r = () -> {
            PumpkinLog.log(PumpkinLog.INFO, "Application", "pumpkin thread begin");
            pitUpdate(bitmap);
            pe = pitInit();
            if (pe != -1) pitFinish(pe);
            pumpkinSetOn(false);
            exited = true;
            PumpkinLog.log(PumpkinLog.INFO, "Application", "pumpkin thread end");
        };
        PumpkinLog.log(PumpkinLog.INFO, "Application", "start");
        exec = Executors.newSingleThreadExecutor();
        exec.execute(r);
    }

    public void stop() {
        PumpkinLog.log(PumpkinLog.INFO, "Application", "stop");
        pumpkinSetOn(false);
        if (!exited) pitRequestFinish();
        try {
            if (!exec.awaitTermination(1, TimeUnit.SECONDS)) {
                PumpkinLog.log(PumpkinLog.ERROR, "Application", "stop timeout");
            }
        } catch (Exception ex) {
            PumpkinLog.log(PumpkinLog.ERROR, "Application", "stop error " + ex.getMessage());
        }
    }

    public void pumpkinSetUpdate(PumpkinUpdate updater) {
        this.updater = updater;
    }

    public boolean pumpkinOn() {
        return on;
    }

    public void pumpkinSetOn(boolean on) {
        this.on = on;
    }

    public void pumpkinSetPaused(boolean paused) {
        PumpkinLog.log(PumpkinLog.INFO, "Application", "pumpkinSetPaused " + paused);
        this.paused = paused;
        if (!paused) {
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
            if (!to.delete()) {
                Log.e("Pumpkin", "could noe delete file " + to.getName());
            }
        }
        if (to.createNewFile()) {
            FileOutputStream os = new FileOutputStream(to);
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
            copyFile(R.raw.memopad, dir, "MemoPad.prc");

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

    private native int pitInit();
    private native void pitFinish(int pe);
    private native void pitDeploy(String path);
    private native void pitRequestFinish();
    public native void pitUpdate(Bitmap bitmap);
    public native void pitPause(boolean paused);
    public native void pitTouch(int action, int x, int y);
    public native void pitSetBattery(int level);
}
