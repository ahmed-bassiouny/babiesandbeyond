package tech.ntam.adminapp.api;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.ApiConfig;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {
    public static final String errorConnection = "Please Check Your Internet Connection!";

    // base request
    private static BaseRequestInterface baseRequestInterface = ApiConfig.getRetrofit().create(BaseRequestInterface.class);

    private static <T> void checkValidResult(int responseCode, boolean responseStatus, T object, String errorMsg, BaseResponseInterface baseResponseInterface) {
        // get response to check if request valid or not
        // get result object to pass it to base response interface
        if (responseCode == 200) {
            if (responseStatus) {
                baseResponseInterface.onSuccess(object);
            } else {
                baseResponseInterface.onFailed(errorMsg);
            }
        } else {
            // this case mean response code not equal 200
            baseResponseInterface.onFailed(errorMsg);
        }
    }

    public static void login(Context context, String email, String password, final BaseResponseInterface<User> anInterface) {
        Call<LoginResponse> response = baseRequestInterface.login(email, password, UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }
    public static void getStaff(Context context, int serviceTypeId, final BaseResponseInterface<Staff> anInterface) {
        Call<StaffResponse> response = baseRequestInterface.getStaffRequests(UserSharedPref.getTokenWithHeader(context),UserSharedPref.getId(context), serviceTypeId);
        response.enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getStaff(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<StaffResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

}
