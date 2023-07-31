package com.pit.pit;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

// final float scale = getResources().getDisplayMetrics().density;
// px = dp * (dpi / 160)

public class CustomView extends View {
    private static final int DIA_HEIGHT = 160;
    private static final int BUTTON_HEIGHT = 64;
    private final Bitmap bitmap;
    private Rect rect;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Bitmap.Config conf = Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(getScreenWidth(), getScreenHeight() + DIA_HEIGHT + BUTTON_HEIGHT, conf);
        eraseScreen();

        addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                rect = new Rect(0, 0, right - left, bottom - top));
    }

    public void eraseScreen() {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getScreenWidth(), getScreenHeight(), paint);
    }

    public int getScreenWidth() {
        return 320;
    }

    public int getScreenHeight() {
        return 320;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int x = (int)(event.getX() * getScreenWidth()  / (rect.right  - rect.left));
        int y = (int)(event.getY() * (getScreenHeight() + DIA_HEIGHT + BUTTON_HEIGHT) / (rect.bottom - rect.top));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getPumpkin().pitTouch(0, x, y);
                break;
            case MotionEvent.ACTION_UP:
                getPumpkin().pitTouch(1, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                getPumpkin().pitTouch(2, x, y);
                break;
        }

        return true;
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private Pumpkin getPumpkin() {
        Activity activity = getActivity();
        return activity != null ? (Pumpkin)activity.getApplication() : new Pumpkin();
    }
}
