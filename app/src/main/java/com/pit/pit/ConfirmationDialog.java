package com.pit.pit;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class ConfirmationDialog {

    public ConfirmationDialog(Context context, ConfirmationListener listener, String msg) {
        AlertDialog.Builder window = new AlertDialog.Builder(context);
        window.setTitle("Confirmation");
        window.setMessage(msg);
        window.setPositiveButton("Yes", (dialog, which) -> listener.confirmationResult(true));
        window.setNegativeButton("No", (dialog, which) -> listener.confirmationResult(false));
        window.show();
    }
}
