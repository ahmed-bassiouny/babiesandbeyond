package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;
import com.payfort.sdk.android.dependancies.models.FortRequest;

import java.util.HashMap;
import java.util.Map;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.payment.IPaymentRequestCallBack;
import tech.ntam.babiesandbeyond.payment.PayFortData;
import tech.ntam.babiesandbeyond.payment.PayFortPayment;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class PaymentGetwayActivity extends MyToolbar implements IPaymentRequestCallBack {

    private FortCallBackManager fortCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_getway);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.payment);
        findViewById();
        fortCallback = FortCallback.Factory.create();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForPayfortPayment();
            }
        });


    }


    public void findViewById() {


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
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment successful");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void requestForPayfortPayment(){
        PayFortData payFortData = new PayFortData();
        if (/*!TextUtils.isEmpty(etAmount.getText().toString())*/true) {
            payFortData.amount = String.valueOf((int) (Float.parseFloat("0") * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = "readyandroid@gmail.com";
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            payFortPayment.requestForPayment(payFortData);
        }
    }
}

    /*


    private void payfor(){
        fortCallback = null;
        String deviceId = "", sdkToken = "";
        // create Fort callback instance
        fortCallback = FortCallback.Factory.create();
        // Generating deviceId
        deviceId = FortSdk.getDeviceId(this);
        Log.e("DeviceId ", deviceId);
        // prepare payment request
        FortRequest fortrequest = new FortRequest();
        fortrequest.setRequestMap(collectRequestMap("PASS_THE_GENERATED_SDK_TOKEN_HERE"));
        fortrequest.setShowResponsePage(true); // to [display/use] the SDK response page
        // execute payment request
        callSdk(fortrequest);
    }

    private Map<String, Object> collectRequestMap(String sdkToken) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("command", "PURCHASE");
        requestMap.put("customer_email", "Sam@gmail.com");
        requestMap.put("currency", "SAR");
        requestMap.put("amount", "100");
        requestMap.put("language", "en");
        requestMap.put("merchant_reference", "ORD-0000007682");
        requestMap.put("customer_name", "Sam");
        requestMap.put("customer_ip", "172.150.16.10");
        requestMap.put("payment_option", "VISA");
        requestMap.put("eci", "ECOMMERCE");
        requestMap.put("order_description", "DESCRIPTION");
        requestMap.put("sdk_token", sdkToken);
        return requestMap;
    }
    private void callSdk(FortRequest fortrequest) {
        try {
            FortSdk.getInstance().registerCallback(this, fortrequest,
                    FortSdk.ENVIRONMENT.TEST, 5, fortCallback,true, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, Object> map, Map<String, Object> map1) {

                        }

                        @Override
                        public void onSuccess(Map<String, Object> map, Map<String, Object> map1) {

                        }

                        @Override
                        public void onFailure(Map<String, Object> map, Map<String, Object> map1) {

                        }
                    });
        } catch (Exception e) {
            Log.e("execute Payment", "call FortSdk", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode, resultCode, data);
    }*/

