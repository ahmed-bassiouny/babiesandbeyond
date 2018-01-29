package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.AboutUs;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class AboutUsActivity extends MyToolbar {

    private WebView wvAbout;
    private TextView noInternet;
    private Button btnNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        wvAbout = findViewById(R.id.wv_about);
        noInternet = findViewById(R.id.no_internet);
        btnNoInternet = findViewById(R.id.btn_no_internet);

        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.about_us);
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAbout();
            }
        });
        if(AboutUs.getAbout().isEmpty()){
            loadAbout();
        }else {
            wvAbout.loadDataWithBaseURL("", AboutUs.getAbout(), "text/html", "UTF-8", "");
            wvAbout.setVisibility(View.VISIBLE);
        }

    }

    private void loadAbout() {
        noInternet.setVisibility(View.GONE);
        btnNoInternet.setVisibility(View.GONE);
        wvAbout.setVisibility(View.GONE);
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getAbout(new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                AboutUs.setAbout(s);
                wvAbout.loadDataWithBaseURL("", s, "text/html", "UTF-8", "");
                wvAbout.setVisibility(View.VISIBLE);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                noInternet.setVisibility(View.VISIBLE);
                btnNoInternet.setVisibility(View.VISIBLE);
                myDialog.dismissMyDialog();
            }
        });
    }

}
