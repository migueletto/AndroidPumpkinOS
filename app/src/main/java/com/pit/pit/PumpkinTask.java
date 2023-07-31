package com.pit.pit;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class PumpkinTask extends AsyncTask<String, Void, String> {
    private Pumpkin pumpkin;
    private Bitmap bitmap;
    private int width, height;

    public PumpkinTask(Pumpkin pumpkin, Bitmap bitmap, int width, int height) {
       this.pumpkin = pumpkin;
       this.bitmap = bitmap;
       this.width = width;
       this.height = height;
        Log.i("PumpkinTask", "create");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("PumpkinTask", "onPreExecute");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("PumpkinTask", "doInBackground");
        pumpkin.pitUpdate(bitmap, 1);
        pumpkin.pumpkinInit(width, height);
        return "ok";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("PumpkinTask", "onPostExecute");
    }
}
