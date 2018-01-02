package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by bassiouny on 02/01/18.
 */

public class MyDialog extends Dialog {

    public static MyDialog myDialog;

    public MyDialog(@NonNull Context context) {
        super(context);
        ProgressBar progressBar = new ProgressBar(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(progressBar, layoutParams);
    }

    @Override
    public void onBackPressed() {
    }

    public static void showMyDialog(Context context) {
        myDialog = new MyDialog(context);
        myDialog.show();
    }

    public static void dismissMyDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }
}
