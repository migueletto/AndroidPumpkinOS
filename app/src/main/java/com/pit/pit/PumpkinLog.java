package com.pit.pit;

import android.util.Log;

public class PumpkinLog {
    public static final int INFO = 1;
    public static final int ERROR = 2;

    public static void log(int level, String category, String msg) {
        msg = "(" + Thread.currentThread().getName() + ") " + msg;
        switch (level) {
            case INFO: Log.i(category, msg); break;
            case ERROR: Log.e(category, msg); break;
        }
    }
}
