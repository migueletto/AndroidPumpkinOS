package com.pit.pit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PumpkinUpdate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_MAIN)) {
            Log.i("MainActivity", "onCreate action " + action);
            setContentView(R.layout.activity_main);
            getPumpkin().pumpkinSetUpdate(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
        Pumpkin pumpkin = getPumpkin();
        CustomView cv = findViewById(R.id.customView);

        if (pumpkin.pumpkinOn()) {
            updateDisplay(false);
        } else {
            pumpkin.start(cv.getBitmap(), cv.getScreenWidth(), cv.getScreenHeight());
            pumpkin.pitPause(false);
            pumpkin.pumpkinSetPaused(false);
            pumpkin.pumpkinSetOn(true);
            Log.i("MainActivity", "onStart pumpkinSetOn");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
        Pumpkin pumpkin = getPumpkin();
        pumpkin.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
        Pumpkin pumpkin = getPumpkin();
        if (pumpkin.pumpkinOn()) {
            pumpkin.pitPause(false);
        }
        pumpkin.pumpkinSetPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
        Pumpkin pumpkin = getPumpkin();
        if (pumpkin.pumpkinOn()) {
            pumpkin.pitPause(true);
        }
        pumpkin.pumpkinSetPaused(true);
    }

    @Override
    public void updateDisplay(boolean finish) {
        CustomView cv = findViewById(R.id.customView);
        cv.invalidate();
        if (finish) {
            Log.i("MainActivity", "finishAndRemoveTask");
            finishAndRemoveTask();
        }
    }

    private Pumpkin getPumpkin() {
        return (Pumpkin)getApplication();
    }
}
