package tech.ntam.babiesandbeyond.api.request;

import android.content.Context;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import tech.ntam.babiesandbeyond.api.api_model.request.MidwifeRequestRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.AboutResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.AddWorkshopResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ArticleResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.CreateGroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.GroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.HistoryResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MessageAdminResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MessageListAdminResponse;
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
import tech.ntam.babiesandbeyond.model.Article;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.History;
import tech.ntam.babiesandbeyond.model.MessageAdmin;
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
 * Created by Developer on 31/12/17.
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

    /**
     * Login.
     *
     * @param context     the context
     * @param email       the email
     * @param password    the password
     * @param anInterface the an interface
     */
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

    /**
     * Login with social.
     *
     * @param context     the context
     * @param email       the email
     * @param name        the name
     * @param anInterface the an interface
     */
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

    /**
     * Register.
     *
     * @param context     the context
     * @param email       the email
     * @param password    the password
     * @param name        the name
     * @param phone       the phone
     * @param anInterface the an interface
     */
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

    /**
     * Gets events.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Request service.
     *
     * @param context       the context
     * @param serviceTypeId the service type id
     * @param startDate     the start date
     * @param endDate       the end date
     * @param location      the location
     * @param lat           the lat
     * @param lng           the lng
     * @param anInterface   the an interface
     */
    public static void requestService(Context context, int serviceTypeId, String startDate,
                                      String endDate, String location,
                                      double lat,double lng,String noOfChildren,
                                      String birthDate, String additionalInfo,
                                      int isComplex, String nurseType,
                                      final BaseResponseInterface<Service> anInterface) {
        Call<AddServiceResponse> response = baseRequestInterface.requestService(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context),
                serviceTypeId, startDate, endDate, location,lat,lng,
                noOfChildren,birthDate,additionalInfo,isComplex,nurseType);
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

    /**
     * Gets my service.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Send status event.
     *
     * @param context     the context
     * @param eventId     the event id
     * @param isComing    the is coming
     * @param anInterface the an interface
     */
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

    /**
     * Gets groups.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Gets profile.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Update password.
     *
     * @param context     the context
     * @param password    the password
     * @param oldPassword the old password
     * @param anInterface the an interface
     */
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

    /**
     * Gets history.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Update profile.
     *
     * @param context     the context
     * @param name        the name
     * @param phone       the phone
     * @param photo       the photo
     * @param anInterface the an interface
     */
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

    /**
     * Create group.
     *
     * @param context     the context
     * @param name        the name
     * @param description the description
     * @param photo       the photo
     * @param anInterface the an interface
     */
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

    /**
     * Gets workshops.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Join group.
     *
     * @param context     the context
     * @param groupId     the group id
     * @param anInterface the an interface
     */
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

    /**
     * Leave group.
     *
     * @param context     the context
     * @param groupId     the group id
     * @param anInterface the an interface
     */
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

    /**
     * Gets about.
     *
     * @param anInterface the an interface
     */
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

    /**
     * Gets terms.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
    public static void getTerms(Context context,final BaseResponseInterface<String> anInterface) {
        Call<AboutResponse> response = baseRequestInterface.getTerms(UserSharedPref.getTokenWithHeader(context));
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

    /**
     * Send workshop request.
     *
     * @param context     the context
     * @param workshopId  the workshop id
     * @param anInterface the an interface
     */
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


    /**
     * Gets notification.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Logout.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Gets tasks.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * User comment service.
     *
     * @param context     the context
     * @param taskId      the task id
     * @param comment     the comment
     * @param anInterface the an interface
     */
    public static void userCommentService(Context context, String taskId, String comment, final BaseResponseInterface<String> anInterface) {
        // is midwife to detect url what will call
        Call<ParentResponse> response;
        if(UserSharedPref.getIamMidwife(context)){
            response = baseRequestInterface.userCommentMidwife(
                    UserSharedPref.getTokenWithHeader(context),
                    taskId, comment);
        }else {
            response = baseRequestInterface.userCommentService(
                    UserSharedPref.getTokenWithHeader(context),
                    UserSharedPref.getId(context), taskId, comment);
        }
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

    /**
     * User rate service.
     *
     * @param context     the context
     * @param isMidwife   the is midwife
     * @param serviceId   the service id
     * @param rate        the rate
     * @param anInterface the an interface
     */
    public static void userRateService(Context context,boolean isMidwife, String serviceId, int rate, final BaseResponseInterface<String> anInterface) {
        // is midwife to detect url what will call
        Call<ParentResponse> response;
        if(isMidwife){
            response = baseRequestInterface.userRateMidwife(
                    UserSharedPref.getTokenWithHeader(context),
                    serviceId, rate);
        }else {
            response = baseRequestInterface.userRateService(
                    UserSharedPref.getTokenWithHeader(context),
                    serviceId, rate);
        }

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

    /**
     * Forget password.
     *
     * @param email       the email
     * @param anInterface the an interface
     */
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

    /**
     * Cancel service.
     *
     * @param context     the context
     * @param serviceId   the service id
     * @param anInterface the an interface
     */
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

    /**
     * Cancel workshop.
     *
     * @param context     the context
     * @param workshopId  the workshop id
     * @param anInterface the an interface
     */
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

    /**
     * Workshop payment.
     *
     * @param context     the context
     * @param workshopId  the workshop id
     * @param anInterface the an interface
     */
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

    /**
     * Service payment.
     *
     * @param context     the context
     * @param serviceId   the service id
     * @param anInterface the an interface
     * payment method 1-> cash 2-> online payment
     */
    public static void servicePayment(Context context, int serviceId,int paymentMethod, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.servicePayment(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), serviceId,paymentMethod);
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

    /**
     * Gets all midwife.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Check midwife.
     *
     * @param context     the context
     * @param midwifeId   the midwife id
     * @param timeFrom    the time from
     * @param timeTo      the time to
     * @param date        the date
     * @param anInterface the an interface
     */
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

    /**
     * Reserve midwife.
     *
     * @param context              the context
     * @param midwifeId            the midwife id
     * @param location             the location
     * @param lng                  the lng
     * @param lat                  the lat
     * @param midwifeRequestModels the midwife request models
     * @param anInterface          the an interface
     */
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

    /**
     * Active account.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Resend code.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Cancel resercation midwife.
     *
     * @param context     the context
     * @param uniqueKey   the unique key
     * @param anInterface the an interface
     */
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

    /**
     * Midwife payment.
     *
     * @param context     the context
     * @param uniqueKey   the unique key
     * @param anInterface the an interface
     */
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

    /**
     * Gets user history.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
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

    /**
     * Send contact us.
     *
     * @param context     the context
     * @param subject     the subject
     * @param reason      the reason
     * @param message     the message
     * @param anInterface the an interface
     */
    public static void sendContactUs(Context context,
            String subject,String reason,String message,final BaseResponseInterface<String> anInterface){
        Call<ParentResponse> response = baseRequestInterface.contactUs(
                UserSharedPref.getTokenWithHeader(context),UserSharedPref.getName(context),
                UserSharedPref.getEmail(context),UserSharedPref.getPhone(context),subject,reason,message);
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

    /**
     * Gets message admin.
     *
     * @param context     the context
     * @param anInterface the an interface
     */
    public static void getMessageAdmin(Context context, final BaseResponseInterface<List<MessageAdmin>> anInterface) {
        Call<MessageListAdminResponse> response = baseRequestInterface.getMessageAdmin(
                UserSharedPref.getTokenWithHeader(context));
        response.enqueue(new Callback<MessageListAdminResponse>() {
            @Override
            public void onResponse(Call<MessageListAdminResponse> call, Response<MessageListAdminResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessageAdmins(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MessageListAdminResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    /**
     * Send message to admin.
     *
     * @param context     the context
     * @param message     the message
     * @param anInterface the an interface
     */
    public static void sendMessageToAdmin(Context context,String message, final BaseResponseInterface<MessageAdmin> anInterface) {
        Call<MessageAdminResponse> response = baseRequestInterface.sendMessageToAdmin(
                UserSharedPref.getTokenWithHeader(context),message);
        response.enqueue(new Callback<MessageAdminResponse>() {
            @Override
            public void onResponse(Call<MessageAdminResponse> call, Response<MessageAdminResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessageAdmin(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MessageAdminResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getArticles(final BaseResponseInterface<List<Article>> anInterface) {
        Call<ArticleResponse> response = baseRequestInterface.getArticles();
        response.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getArticles(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getServiceQuotation(Context context,
                                   String serviceName,
                                   String noOfDaysRequiredPerMonth,
                                   String noOfHoursAday,
                                   String specificRequest,
                                   String location,
                                   final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.getServiceQuotation(UserSharedPref.getTokenWithHeader(context)
        ,UserSharedPref.getId(context),serviceName,noOfDaysRequiredPerMonth,noOfHoursAday,specificRequest,location);
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

}
