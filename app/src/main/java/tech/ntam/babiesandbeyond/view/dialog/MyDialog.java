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

    public static AlertDialog dialog;


    public static void showMyDialog(Context context) {
        dialog = new SpotsDialog(context);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dismissMyDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
