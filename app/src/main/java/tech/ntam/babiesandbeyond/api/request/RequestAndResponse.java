package tech.ntam.babiesandbeyond.api.request;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.api.config.ApiConfig;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {


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
    private static <T> void checkValidListResult(int responseCode, boolean responseStatus, List<T> object, String errorMsg, BaseResponseInterface baseResponseInterface) {
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

    public static void login(String email, String password, final BaseResponseInterface<UserData> anInterface) {
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

    public static void register(String email, String password, String name, String phone, final BaseResponseInterface<User> anInterface) {
        Call<RegisterResponse> response = baseRequestInterface.register(name, email, password, phone);
        response.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }
    public static void getEvents(final BaseResponseInterface<List<Event>> anInterface){
        Call<EventsResponse> response = baseRequestInterface.getEvents("test@gmail.com");
        response.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getEvent(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }
}
