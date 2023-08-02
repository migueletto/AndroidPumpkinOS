package com.pit.pit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PumpkinUpdate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_MAIN)) {
            PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onCreate");
            setContentView(R.layout.activity_main);
            Pumpkin pumpkin = getPumpkin();
            pumpkin.pumpkinSetUpdate(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onDestroy");
        Pumpkin pumpkin = getPumpkin();
        pumpkin.pumpkinSetUpdate(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onStart");
        CustomView cv = findViewById(R.id.customView);
        Pumpkin pumpkin = getPumpkin();
        pumpkin.start(cv.getBitmap());
    }

    @Override
    protected void onStop() {
        super.onStop();
        PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onStop");
        Pumpkin pumpkin = getPumpkin();
        pumpkin.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onResume");
        Pumpkin pumpkin = getPumpkin();
        if (pumpkin.pumpkinOn()) {
            pumpkin.pitPause(false);
        }
        pumpkin.pumpkinSetPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "onPause");
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
            PumpkinLog.log(PumpkinLog.INFO, "MainActivity", "finishAndRemoveTask");
            finishAndRemoveTask();
        }
    }

    private Pumpkin getPumpkin() {
        return (Pumpkin)getApplication();
    }
}
