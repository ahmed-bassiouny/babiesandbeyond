package tech.ntam.babiesandbeyond.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import dmax.dialog.SpotsDialog;

/**
 * Created by bassiouny on 02/01/18.
 */

public class MyDialog {

    public AlertDialog dialog;


    public void showMyDialog(Context context) {
        dialog = new SpotsDialog(context);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissMyDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
