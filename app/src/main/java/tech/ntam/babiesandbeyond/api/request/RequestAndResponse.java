package tech.ntam.babiesandbeyond.api.request;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MyServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.api.config.ApiConfig;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;

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

    public static void login(Context context,String email, String password, final BaseResponseInterface<User> anInterface) {
        Call<LoginResponse> response = baseRequestInterface.login(email, password,UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.message(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void register(final Context context, String email, String password, String name, String phone, final BaseResponseInterface<User> anInterface) {
        Call<RegisterResponse> response = baseRequestInterface.register(name, email, password, phone,UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                anInterface.onFailed(context.getString(R.string.email_already_taken));
            }
        });
    }

    public static void getEvents(Context context, final BaseResponseInterface<List<Event>> anInterface) {
        Call<EventsResponse> response = baseRequestInterface.getEvents(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getEmail(context));
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

    public static void requestService(Context context, int serviceTypeId, String startDate, String endDate, String location, final BaseResponseInterface<ParentResponse> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.requestService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                serviceTypeId, startDate, endDate, location);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , null, response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void getMyService(Context context, final BaseResponseInterface<UserService> anInterface) {
        Call<MyServiceResponse> response = baseRequestInterface.getMyService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getEmail(context));
        response.enqueue(new Callback<MyServiceResponse>() {
            @Override
            public void onResponse(Call<MyServiceResponse> call, Response<MyServiceResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserService(), response.message(), anInterface);
            }

            @Override
            public void onFailure(Call<MyServiceResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void sendStatusEvent(Context context, int eventId, boolean isComing, final BaseResponseInterface<String> anInterface) {
        int coming = 0;
        if (isComing) {
            coming = 1;
        }
        Call<ParentResponse> response = baseRequestInterface.sendStatusEvent(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                coming, eventId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }
}
