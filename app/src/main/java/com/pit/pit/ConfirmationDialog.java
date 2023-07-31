package com.pit.pit;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ConfirmationDialog {

    private final ConfirmationListener listener;
    private boolean accept;

    public ConfirmationDialog(Context context, ConfirmationListener listener, String msg) {
        this.listener = listener;
        AlertDialog.Builder window = new AlertDialog.Builder(context);
        window.setTitle("Confirmation");
        window.setMessage(msg);

        window.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.confirmationResult(true);
            }
        });

        window.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.confirmationResult(false);
            }
        });

        window.show();
    }
}
