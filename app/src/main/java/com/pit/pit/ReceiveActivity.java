package com.pit.pit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;

public class ReceiveActivity extends AppCompatActivity implements ConfirmationListener {

    private static final String RAW_PREFIX = "/document/raw:";

    private File file;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_SEND)) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            Log.i("ReceiveActivity", "onCreate received type [" + intent.getType() + "], file [" + uri + "]");
            if (uri != null && uri.getPath() != null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    String path = uri.getPath();
                    if (path.startsWith(RAW_PREFIX)) {
                        path = path.substring(RAW_PREFIX.length());
                    }
                    file = new File(path);
                    fileName = getFileName(uri);
                    ConfirmationDialog dialog = new ConfirmationDialog(this, this, "Accept file " + fileName + " ?");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("ReceiveActivity", "permission granted");
            } else {
                Log.i("ReceiveActivity", "permission denied");
            }
        }
    }

    @Override
    public void confirmationResult(boolean accept) {
        if (accept) {
            getPumpkin().installFile(file, fileName);
        }
        finish();
    }

    private String getFileName(Uri uri) {
        String result = null;
        String scheme = uri.getScheme();
        if (scheme != null && scheme.equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int i = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(i);
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private Pumpkin getPumpkin() {
        return (Pumpkin)getApplication();
    }
}
