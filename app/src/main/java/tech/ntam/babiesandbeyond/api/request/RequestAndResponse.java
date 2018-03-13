package tech.ntam.babiesandbeyond.api.request;

import android.content.Context;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.babiesandbeyond.api.api_model.request.MidwifeRequestRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.AboutResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddWorkshopResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.CreateGroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.GroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.HistoryResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MidwifeResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MyServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.NotificationResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ProfileResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ResendCodeResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ReserveMidwifeResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.StaffTasksResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.UserHistoryResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.WorkshopResponse;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.History;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.Notification;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.ApiConfig;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {

    private static String api_error = "Server is not responding";
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void loginWithSocial(Context context, String email, String name, final BaseResponseInterface<User> anInterface) {
        Call<LoginResponse> response = baseRequestInterface.loginWithSocial(email, name, UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void register(final Context context, String email, String password, String name, String phone, final BaseResponseInterface<UserData> anInterface) {
        Call<RegisterResponse> response = baseRequestInterface.register(name, email, password, phone, UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getEvents(Context context, final BaseResponseInterface<List<Event>> anInterface) {
        Call<EventsResponse> response = baseRequestInterface.getEvents(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getEmail(context));
        response.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getEvent(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void requestService(Context context, int serviceTypeId, String startDate,
                                      String endDate, String location,
                                      double lat,double lng,
                                      final BaseResponseInterface<Service> anInterface) {
        Call<AddServiceResponse> response = baseRequestInterface.requestService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                serviceTypeId, startDate, endDate, location,lat,lng);
        response.enqueue(new Callback<AddServiceResponse>() {
            @Override
            public void onResponse(Call<AddServiceResponse> call, Response<AddServiceResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getService(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AddServiceResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserService(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MyServiceResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getGroups(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void updatePassword(Context context, String password, String oldPassword, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.updatePassword(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                password, oldPassword);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getHistoryList(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getGroup(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<CreateGroupResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getWorkshops(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<WorkshopResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getAbout(final BaseResponseInterface<String> anInterface) {
        Call<AboutResponse> response = baseRequestInterface.getAbout();
        response.enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getAboutText(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getWorkshop(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AddWorkshopResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
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
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getNotifications(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void logout(Context context, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.logout(
                UserSharedPref.getId(context));
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getTasks(Context context, final BaseResponseInterface<List<Task>> anInterface) {
        Call<StaffTasksResponse> response = baseRequestInterface.getTasks(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context));
        response.enqueue(new Callback<StaffTasksResponse>() {
            @Override
            public void onResponse(Call<StaffTasksResponse> call, Response<StaffTasksResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getTaskList(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<StaffTasksResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void rateTask(Context context, int taskId, String comment, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.rateTask(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), taskId, comment);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void userRateService(Context context, int serviceId, float rate, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.userRateService(
                UserSharedPref.getTokenWithHeader(context),
                serviceId, rate);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void forgetPassword(String email, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.forgetPassword(email);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void cancelService(Context context, int serviceId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.cancelService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), serviceId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void cancelWorkshop(Context context, int workshopId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.cancelWorkshop(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), workshopId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void workshopPayment(Context context, int workshopId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.workshopPayment(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), workshopId,1);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void servicePayment(Context context, int serviceId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.servicePayment(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), serviceId,1);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getAllMidwife(Context context, final BaseResponseInterface<List<Midwife>> anInterface) {
        Call<MidwifeResponse> response = baseRequestInterface.getAllMidwife(
                UserSharedPref.getTokenWithHeader(context));
        response.enqueue(new Callback<MidwifeResponse>() {
            @Override
            public void onResponse(Call<MidwifeResponse> call, Response<MidwifeResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMidwifeList(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MidwifeResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void checkMidwife(Context context,int midwifeId
            ,String timeFrom,String timeTo,String date
            , final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.checkMidwife(
                UserSharedPref.getTokenWithHeader(context),midwifeId,timeFrom,timeTo,date);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void reserveMidwife(Context context, int midwifeId,String location, double lng, double lat
            , List<MidwifeRequestModel> midwifeRequestModels, final BaseResponseInterface<MidwifeService> anInterface) {
        MidwifeRequestRequest request = new MidwifeRequestRequest(midwifeId,location,lng,lat, midwifeRequestModels);
        Call<ReserveMidwifeResponse> response = baseRequestInterface.reserveMidwife(
                UserSharedPref.getTokenWithHeader(context),request);
        response.enqueue(new Callback<ReserveMidwifeResponse>() {
            @Override
            public void onResponse(Call<ReserveMidwifeResponse> call, Response<ReserveMidwifeResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMidwifeService(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ReserveMidwifeResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void activeAccount(Context context,final BaseResponseInterface anInterface){
        Call<ParentResponse> response = baseRequestInterface.activeAccount(UserSharedPref.getEmail(context));
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void resendCode(Context context,final BaseResponseInterface<String> anInterface){
        Call<ResendCodeResponse> response = baseRequestInterface.resendActiveCode(UserSharedPref.getEmail(context));
        response.enqueue(new Callback<ResendCodeResponse>() {
            @Override
            public void onResponse(Call<ResendCodeResponse> call, Response<ResendCodeResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getCode(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ResendCodeResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void cancelResercationMidwife(Context context,String uniqueKey,final BaseResponseInterface anInterface){
        Call<ParentResponse> response = baseRequestInterface.cancelReservationMidwife(
                UserSharedPref.getTokenWithHeader(context),uniqueKey);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void midwifePayment(Context context, String uniqueKey, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.midwifePayment(
                UserSharedPref.getTokenWithHeader(context),
                uniqueKey,1);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getUserHistory(Context context, final BaseResponseInterface<List<UserHistory>> anInterface) {
        Call<UserHistoryResponse> response = baseRequestInterface.getUserHistory(
                UserSharedPref.getTokenWithHeader(context));
        response.enqueue(new Callback<UserHistoryResponse>() {
            @Override
            public void onResponse(Call<UserHistoryResponse> call, Response<UserHistoryResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserHistory(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<UserHistoryResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
}
