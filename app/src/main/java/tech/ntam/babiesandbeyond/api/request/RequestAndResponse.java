package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.config.ApiConfig;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.model.User;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {


    // base request
    private static BaseRequestInterface baseRequestInterface = ApiConfig.getRetrofit().create(BaseRequestInterface.class);

    private static  <T> void checkValidResult(int responseCode, boolean responseStatus, T object, String errorMsg, BaseResponseInterface baseResponseInterface) {
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

    public static void login(String email, String password, final BaseResponseInterface<User> anInterface) {
        Call<LoginResponse> response = baseRequestInterface.login(email, password);
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData(), response.message(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }
}
