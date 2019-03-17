package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.payment.IPaymentRequestCallBack;
import tech.ntam.babiesandbeyond.payment.PayFortData;
import tech.ntam.babiesandbeyond.payment.PayFortPayment;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class PaymentMethodActivity extends AppCompatActivity implements IPaymentRequestCallBack {

    private Button tvPay;
    private RadioButton rdCash, rdCart;
    private Service service;
    private Workshop workshop;
    private MidwifeService midwifeService;
    private FortCallBackManager fortCallback;
    private String amount;
    private int paymentMethod = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        findViewById();
        service = getIntent().getParcelableExtra(IntentDataKey.MY_SERVICE);
        workshop = getIntent().getParcelableExtra(IntentDataKey.MY_WORKSHOP);
        midwifeService = getIntent().getParcelableExtra(IntentDataKey.MIDWIFE);
        amount = getIntent().getStringExtra(IntentDataKey.AMOUNT);

        if (service == null && workshop == null && midwifeService == null)
            finish();

        fortCallback = FortCallback.Factory.create();
        if(amount.equals("0")){
            rdCart.setEnabled(false);
        }
    }

    private void findViewById() {
        tvPay = findViewById(R.id.tv_pay);
        rdCash = findViewById(R.id.rd_cash);
        rdCart = findViewById(R.id.rd_cart);
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdCash.isChecked()) {
                    paymentMethod = 1;
                } else {
                    paymentMethod = 2;
                    requestForPayfortPayment();
                }

            }
        });

    }

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();
        payFortData.amount = String.valueOf((int) (Float.parseFloat(amount) * 100));// Multiplying with 100, bcz amount should not be in decimal format
        payFortData.command = PayFortPayment.PURCHASE;
        payFortData.currency = PayFortPayment.CURRENCY_TYPE;
        payFortData.customerEmail = UserSharedPref.getEmail(this);
        payFortData.language = PayFortPayment.LANGUAGE_TYPE;
        payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

        PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
        payFortPayment.requestForPayment(payFortData);
    }

    @Override
    public void onPaymentRequestResponse(int responseType, PayFortData responseData) {

        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
        } else {
            Log.e("onPaymentResponse", "Payment successful");
            pay();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode, resultCode, data);
    }

    private void pay() {
        if (service != null) {
            // send pay service
            final MyDialog myDialog = new MyDialog();
            myDialog.showMyDialog(PaymentMethodActivity.this);
            RequestAndResponse.servicePayment(PaymentMethodActivity.this, service.getId(), paymentMethod, new BaseResponseInterface<String>() {
                @Override
                public void onSuccess(String s) {
                    Toast.makeText(PaymentMethodActivity.this, s, Toast.LENGTH_SHORT).show();
                    myDialog.dismissMyDialog();
                    if (paymentMethod == 1)
                        service.setServiceStatusName(Constant.CASH);
                    else
                        service.setServiceStatusName(Constant.PaymentOnline);
                    ServiceSharedPref.setMyService(PaymentMethodActivity.this, service);
                    finish();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Toast.makeText(PaymentMethodActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    myDialog.dismissMyDialog();
                }
            });
        } else if (workshop != null) {
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
                    ServiceSharedPref.setMyWorkshop(PaymentMethodActivity.this, workshop);
                    finish();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Toast.makeText(PaymentMethodActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    myDialog.dismissMyDialog();
                }
            });
        } else if (midwifeService != null) {
            // send pay midwife
            final MyDialog myDialog = new MyDialog();
            myDialog.showMyDialog(PaymentMethodActivity.this);
            RequestAndResponse.midwifePayment(PaymentMethodActivity.this, midwifeService.getUniqueKey(), new BaseResponseInterface<String>() {
                @Override
                public void onSuccess(String s) {
                    Toast.makeText(PaymentMethodActivity.this, s, Toast.LENGTH_SHORT).show();
                    myDialog.dismissMyDialog();
                    if (paymentMethod == 1)
                        service.setServiceStatusName(Constant.CASH);
                    else
                        service.setServiceStatusName(Constant.PaymentOnline);
                    ServiceSharedPref.setMyMidwife(PaymentMethodActivity.this, midwifeService);
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
}
