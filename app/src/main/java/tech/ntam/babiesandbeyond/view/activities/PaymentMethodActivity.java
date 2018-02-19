package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class PaymentMethodActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvCash;
    private RadioButton rdCash;
    private TextView tvCart;
    private RadioButton rdCart;
    private Button tvPay;
    private Service service;
    private Workshop workshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        findViewById();
        service = getIntent().getParcelableExtra(IntentDataKey.MY_SERVICE);
        workshop = getIntent().getParcelableExtra(IntentDataKey.MY_WORKSHOP);

        if (service == null && workshop == null)
            finish();
    }

    private void findViewById() {
        tvTitle = findViewById(R.id.tv_title);
        tvCash = findViewById(R.id.tv_cash);
        rdCash = findViewById(R.id.rd_cash);
        tvCart = findViewById(R.id.tv_cart);
        rdCart = findViewById(R.id.rd_cart);
        tvPay = findViewById(R.id.tv_pay);
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(service !=null){
                    // send pay service
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(PaymentMethodActivity.this);
                    RequestAndResponse.servicePayment(PaymentMethodActivity.this, service.getId(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(PaymentMethodActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            service.setServiceStatusName(Constant.CASH);
                            ServiceSharedPref.setMyService(PaymentMethodActivity.this,service);
                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(PaymentMethodActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                        }
                    });
                }else if(workshop != null){
                    // send pay workshop
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(PaymentMethodActivity.this);
                    RequestAndResponse.workshopPayment(PaymentMethodActivity.this, workshop.getId(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(PaymentMethodActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            workshop.setWorkshopId(workshop.getId());
                            workshop.setWorkshopStatusName(Constant.CASH);
                            ServiceSharedPref.setMyWorkshop(PaymentMethodActivity.this,workshop);
                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(PaymentMethodActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                        }
                    });
                }
            }
        });
    }
}
