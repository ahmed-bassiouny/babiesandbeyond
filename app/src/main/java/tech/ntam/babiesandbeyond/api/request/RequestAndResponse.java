package tech.ntam.babiesandbeyond.api.request;

import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.api_model.response.AboutResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddWorkshopResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.CreateGroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.GroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.HistoryResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MyServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.NotificationResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ProfileResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.WorkshopResponse;
import tech.ntam.babiesandbeyond.api.config.ApiConfig;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.History;
import tech.ntam.babiesandbeyond.model.Notification;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;

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

    public static void register(final Context context, String email, String password, String name, String phone, final BaseResponseInterface<User> anInterface) {
        Call<RegisterResponse> response = baseRequestInterface.register(name, email, password, phone, UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.body().getMessage(), anInterface);
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
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void requestService(Context context, int serviceTypeId, String startDate, String endDate, String location, final BaseResponseInterface<Service> anInterface) {
        Call<AddServiceResponse> response = baseRequestInterface.requestService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                serviceTypeId, startDate, endDate, location);
        response.enqueue(new Callback<AddServiceResponse>() {
            @Override
            public void onResponse(Call<AddServiceResponse> call, Response<AddServiceResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getService(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AddServiceResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
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
                        , response.body().getUserService(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MyServiceResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void sendStatusEvent(Context context, int eventId, boolean isComing, final BaseResponseInterface<String> anInterface) {
        int coming = 0;
        if (isComing) coming = 1;
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
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void getGroups(Context context, final BaseResponseInterface<List<Group>> anInterface) {
        Call<GroupResponse> response = baseRequestInterface.getGroups(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getEmail(context));
        response.enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getGroups(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void getProfile(Context context, final BaseResponseInterface<User> anInterface) {
        Call<ProfileResponse> response = baseRequestInterface.getProfile(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getEmail(context));
        response.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void updatePassword(Context context, String password, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.updatePassword(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                password);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void getHistory(Context context, final BaseResponseInterface<List<History>> anInterface) {
        Call<HistoryResponse> response = baseRequestInterface.getHistory(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context));
        response.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getHistoryList(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void updateProfile(Context context, String name, String phone, String photo, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.updateProfile(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                name, photo, phone);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void createGroup(Context context, String name, String description, String photo, final BaseResponseInterface<Group> anInterface) {
        Call<CreateGroupResponse> response = baseRequestInterface.createGroup(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                name, description, photo);
        response.enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(Call<CreateGroupResponse> call, Response<CreateGroupResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getGroup(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<CreateGroupResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void getWorkshops(Context context, final BaseResponseInterface<List<Workshop>> anInterface) {
        Call<WorkshopResponse> response = baseRequestInterface.getWorkshops(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context));
        response.enqueue(new Callback<WorkshopResponse>() {
            @Override
            public void onResponse(Call<WorkshopResponse> call, Response<WorkshopResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getWorkshops(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<WorkshopResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void joinGroup(Context context, int groupId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.joinGroup(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), groupId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void leaveGroup(Context context, int groupId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.leaveGroup(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), groupId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void getAbout(final BaseResponseInterface<String> anInterface) {
        Call<AboutResponse> response = baseRequestInterface.getAbout();
        response.enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getAboutText(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }

    public static void sendWorkshopRequest(Context context, int workshopId, final BaseResponseInterface<Workshop> anInterface) {
        Call<AddWorkshopResponse> response = baseRequestInterface.sendWorkshopRequest(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), workshopId);
        response.enqueue(new Callback<AddWorkshopResponse>() {
            @Override
            public void onResponse(Call<AddWorkshopResponse> call, Response<AddWorkshopResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getWorkshop(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AddWorkshopResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }


    public static void getNotification(Context context, final BaseResponseInterface<List<Notification>> anInterface) {
        Call<NotificationResponse> response = baseRequestInterface.getNotification(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context));
        response.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getNotifications(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }
    public static void logout(Context context, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.logout(
                UserSharedPref.getId(context));
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(errorConnection);
            }
        });
    }
}
