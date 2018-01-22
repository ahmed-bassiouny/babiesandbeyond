package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Activity;
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


    public void showMyDialog(Activity activity) {
        if(activity.isDestroyed())
            return;
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
