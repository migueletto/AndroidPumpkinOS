package com.pit.pit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PumpkinUpdate, PitKeys {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_MAIN)) {
            Log.i("MainActivity", "onCreate action " + action);
            setContentView(R.layout.activity_main);

            ImageButton hard1Button = (ImageButton) findViewById(R.id.hard1Button);
            hard1Button.setOnClickListener(this);
            ImageButton hard2Button = (ImageButton) findViewById(R.id.hard2Button);
            hard2Button.setOnClickListener(this);
            ImageButton hard3Button = (ImageButton) findViewById(R.id.hard3Button);
            hard3Button.setOnClickListener(this);
            ImageButton hard4Button = (ImageButton) findViewById(R.id.hard4Button);
            hard4Button.setOnClickListener(this);
            ImageButton upButton = (ImageButton) findViewById(R.id.upButton);
            upButton.setOnClickListener(this);
            ImageButton downButton = (ImageButton) findViewById(R.id.downButton);
            downButton.setOnClickListener(this);

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
            updateDisplay(true);
        } else {
            pumpkin.start(cv.getBitmap(), cv.getScreenWidth(), cv.getScreenHeight());
            pumpkin.pitPause(false);
            pumpkin.pumpkinSetPaused(false);
            pumpkin.pumpkinSetOn(true);
            Log.i("MainActivity", "onStart pumpkinSetOn");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hard1Button:
                getPumpkin().pitKey(KEY_F1);
                break;
            case R.id.hard2Button:
                getPumpkin().pitKey(KEY_F2);
                break;
            case R.id.hard3Button:
                getPumpkin().pitKey(KEY_F3);
                break;
            case R.id.hard4Button:
                getPumpkin().pitKey(KEY_F4);
                break;
            case R.id.upButton:
                getPumpkin().pitKey(KEY_UP);
                break;
            case R.id.downButton:
                getPumpkin().pitKey(KEY_DOWN);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
            case R.id.reset:
                break;
            case R.id.exit:
                getPumpkin().pumpkinFinish();
                finishAndRemoveTask();
                return true;
            case R.id.about:
                aboutBox();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "resume");
        Pumpkin pumpkin = getPumpkin();
        if (pumpkin.pumpkinOn()) {
            pumpkin.pitPause(false);
        }
        pumpkin.pumpkinSetPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "pause");
        Pumpkin pumpkin = getPumpkin();
        if (pumpkin.pumpkinOn()) {
            pumpkin.pitPause(true);
        }
        pumpkin.pumpkinSetPaused(true);
    }

    @Override
    public void updateDisplay(boolean invalidate) {
        //Pumpkin pumpkin = getPumpkin();
        CustomView cv = findViewById(R.id.customView);
        //Bitmap bitmap = cv.getBitmap();
        //int r = pumpkin.pitUpdate(bitmap, invalidate ? 1 : 0);
        cv.invalidate();
        /*if (r == -1) {
            pumpkin.pumpkinFinish();
            finishAndRemoveTask();
        }*/
    }

    private void aboutBox() {
        AlertDialog.Builder aboutWindow = new AlertDialog.Builder(this);
        final String msg = "PalmOS for Android\npmig96.wordpress.com";
        //aboutWindow.setIcon(R.mipmap.ic_palmos);
        aboutWindow.setTitle("About");
        aboutWindow.setMessage(msg);
        aboutWindow.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        aboutWindow.show();
    }

    private Pumpkin getPumpkin() {
        return (Pumpkin)getApplication();
    }
}
