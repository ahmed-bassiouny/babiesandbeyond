package tech.ntam.mylibrary;

import android.app.Activity;
import android.app.AlertDialog;

import dmax.dialog.SpotsDialog;

/**
 * Created by Developer on 02/01/18.
 */

public class MyDialog {

    public AlertDialog dialog;

    public void showMyDialog(Activity activity) {
        dialog = new SpotsDialog(activity);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissMyDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
