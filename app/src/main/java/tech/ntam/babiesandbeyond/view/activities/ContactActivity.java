package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.ContactUs;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class ContactActivity extends MyToolbar {


    private WebView wvAbout;
    private TextView noInternet;
    private Button btnNoInternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        wvAbout = findViewById(R.id.wv_contact);
        noInternet = findViewById(R.id.no_internet);
        btnNoInternet = findViewById(R.id.btn_no_internet);
        setupToolbar(this,false,true,false);
        tvTitle.setText(getString(R.string.contact_us));
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContactUs();
            }
        });
        if(ContactUs.getContactUs().isEmpty()){
            loadContactUs();
        }else {
            wvAbout.loadDataWithBaseURL("", ContactUs.getContactUs(), "text/html", "UTF-8", "");
            wvAbout.setVisibility(View.VISIBLE);
        }
    }

    private void loadContactUs() {
        noInternet.setVisibility(View.GONE);
        btnNoInternet.setVisibility(View.GONE);
        wvAbout.setVisibility(View.GONE);
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getContactUs(new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                ContactUs.setContactUs(s);
                wvAbout.loadDataWithBaseURL("", s, "text/html", "UTF-8", "");
                wvAbout.setVisibility(View.VISIBLE);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                noInternet.setVisibility(View.VISIBLE);
                btnNoInternet.setVisibility(View.VISIBLE);
                noInternet.setText(errorMessage);
                myDialog.dismissMyDialog();
            }
        });
    }

}
