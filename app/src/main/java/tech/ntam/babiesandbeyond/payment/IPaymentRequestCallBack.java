package tech.ntam.babiesandbeyond.payment;

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);
}
