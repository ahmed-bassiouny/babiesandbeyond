package tech.ntam.babiesandbeyond.api.request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.AskServiceRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.AskWorkshopRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.CreateGroupRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.GroupOptionRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.MidwifeRequestRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.ParentRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RateRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RegisterRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.StaffTasksRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.StatusEvent;
import tech.ntam.babiesandbeyond.api.api_model.request.UpdatePasswordRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.UpdateProfileRequest;
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
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;

/**
 * Created by bassiouny on 31/12/17.
 */

public interface BaseRequestInterface {

    String HEADER_KEY = "Accept:application/json";
    String AUTHORIZATION = "Authorization";
    String LOGIN = "login";
    String REGISTER = "register";
    String EVENTS = "all_events";
    String REQUEST_SERVICE = "send_service_request";
    String SERVICE = "services";
    String SEND_STATUS_EVENT = "comming_or_not_comming_to_event";
    String GROUP = "all_groups";
    String PROFILE = "user_profile";
    String UPDATE_PASSWORD = "change_password";
    String HISTORY = "history";
    String UPDATE_PROFILE = "update_profile";
    String CREATE_GROUP = "create_group";
    String WORKSHOPS = "all_workshops";
    String JOIN_GROUP = "join_group";
    String LEAVE_GROUP = "leave_group";
    String ABOUT = "about";
    String CONTACT_US = "";
    String SEND_WORKSHOP_REQUEST = "send_workshop_request";
    String NOTIFICATION = "user_notifications";
    String LOGOUT = "logout";
    String STAFF_TASK = "staff_schedule_and_tasks";
    String RATE = "staff_rate_and_comment";
    String FORGET_PASSWORD = "forget_password";
    String CANCEL_SERVICE = "cancel_service";
    String CANCEL_WORKSHOP = "cancel_workshop";
    String SERVICE_PAYMENT = "service_payment";
    String WORKSHOP_PAYMENT = "workshop_payment";
    String ALL_MIDWIFE = "midwife/all";
    String CHECK_MIDWIFE = "midwife/check-midwife";
    String RESERVE_MIDWIFE = "midwife/reserve";
    String ACTIVE_ACCOUNT = "active_account";
    String RESEND_CODE = "resend_verification_code";
    String CANCEL_RESERVATION_MIDWIFE = "midwife/cancel-reservation";
    String MIDWIFE_PAYMENT = "midwife/confirm-with-payment";
    String LOGIN_WITH_SOCIAL = "social_login";
    String USER_SERVICE_RATE = "user_service_rate";
    String USER_HISTORY = "user/history";
    String USER_MIDWIFE_RATE= "rate-service";
    String USER_MIDWIFE_COMMENT= "comment-service";

    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password,
                              @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);


    @FormUrlEncoded
    @POST(LOGIN_WITH_SOCIAL)
    Call<LoginResponse> loginWithSocial(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.NAME) String name,
                              @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);

    @FormUrlEncoded
    @POST(REGISTER)
    Call<RegisterResponse> register(@Field(RegisterRequest.NAME) String name,
                                    @Field(RegisterRequest.EMAIL) String email,
                                    @Field(RegisterRequest.PASSWORD) String password,
                                    @Field(RegisterRequest.PHONE) String phone,
                                    @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(EVENTS)
    Call<EventsResponse> getEvents(@Header(AUTHORIZATION) String token,
                                   @Field(RegisterRequest.EMAIL) String email);


    @Headers(HEADER_KEY)
    @POST(USER_HISTORY)
    Call<UserHistoryResponse> getUserHistory(@Header(AUTHORIZATION) String token);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(REQUEST_SERVICE)
    Call<AddServiceResponse> requestService(@Header(AUTHORIZATION) String token,
                                            @Field(AskServiceRequest.USER_ID) int userId,
                                            @Field(AskServiceRequest.USER_TYPE_ID) int userTypeId,
                                            @Field(AskServiceRequest.START_DATE) String startDate,
                                            @Field(AskServiceRequest.END_DATE) String endDate,
                                            @Field(AskServiceRequest.LOCATION) String location,
                                            @Field(AskServiceRequest.LATITUDE) double latitude,
                                            @Field(AskServiceRequest.LONGITUDE) double longitude);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SERVICE)
    Call<MyServiceResponse> getMyService(@Header(AUTHORIZATION) String token,
                                         @Field(RegisterRequest.EMAIL) String email);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SEND_STATUS_EVENT)
    Call<ParentResponse> sendStatusEvent(@Header(AUTHORIZATION) String token,
                                         @Field(StatusEvent.USER_ID) int userId,
                                         @Field(StatusEvent.IS_COMING) int isComing,
                                         @Field(StatusEvent.EVENT_ID) int eventId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(GROUP)
    Call<GroupResponse> getGroups(@Header(AUTHORIZATION) String token,
                                  @Field(RegisterRequest.EMAIL) String email);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(PROFILE)
    Call<ProfileResponse> getProfile(@Header(AUTHORIZATION) String token,
                                     @Field(RegisterRequest.EMAIL) String email);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(UPDATE_PASSWORD)
    Call<ParentResponse> updatePassword(@Header(AUTHORIZATION) String token,
                                        @Field(UpdatePasswordRequest.USER_ID) int userId,
                                        @Field(UpdatePasswordRequest.PASSWORD) String password,
                                        @Field(UpdatePasswordRequest.OLD_PASSWORD) String oldPassword);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(HISTORY)
    Call<HistoryResponse> getHistory(@Header(AUTHORIZATION) String token,
                                     @Field(UpdatePasswordRequest.USER_ID) int userId);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(UPDATE_PROFILE)
    Call<ParentResponse> updateProfile(@Header(AUTHORIZATION) String token,
                                       @Field(UpdateProfileRequest.USER_ID) int userId,
                                       @Field(UpdateProfileRequest.NAME) String userName,
                                       @Field(UpdateProfileRequest.PHOTO) String userPhoto,
                                       @Field(UpdateProfileRequest.PHONE) String userPhone);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CREATE_GROUP)
    Call<CreateGroupResponse> createGroup(@Header(AUTHORIZATION) String token,
                                          @Field(CreateGroupRequest.USER_ID) int userId,
                                          @Field(CreateGroupRequest.GROUP_NAME) String name,
                                          @Field(CreateGroupRequest.GROUP_DESCRIPTION) String description,
                                          @Field(CreateGroupRequest.GROUP_PHOTO) String photo);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOPS)
    Call<WorkshopResponse> getWorkshops(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(JOIN_GROUP)
    Call<ParentResponse> joinGroup(@Header(AUTHORIZATION) String token,
                                   @Field(ParentRequest.USER_ID) int userId,
                                   @Field(GroupOptionRequest.GROUP_ID) int groupId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(LEAVE_GROUP)
    Call<ParentResponse> leaveGroup(@Header(AUTHORIZATION) String token,
                                    @Field(ParentRequest.USER_ID) int userId,
                                    @Field(GroupOptionRequest.GROUP_ID) int groupId);

    @POST(ABOUT)
    Call<AboutResponse> getAbout();


    @POST(CONTACT_US)
    Call<AboutResponse> getContactUs();


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SEND_WORKSHOP_REQUEST)
    Call<AddWorkshopResponse> sendWorkshopRequest(@Header(AUTHORIZATION) String token,
                                                  @Field(AskWorkshopRequest.USER_ID) int userId,
                                                  @Field(AskWorkshopRequest.WORKSHOP_ID) int workshopId);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(NOTIFICATION)
    Call<NotificationResponse> getNotification(@Header(AUTHORIZATION) String token,
                                               @Field(AskWorkshopRequest.USER_ID) int userId);

    @FormUrlEncoded
    @POST(LOGOUT)
    Call<ParentResponse> logout(@Field(AskWorkshopRequest.USER_ID) int userId);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(STAFF_TASK)
    Call<StaffTasksResponse> getTasks(@Header(AUTHORIZATION) String token,
                                      @Field(StaffTasksRequest.STAFF_ID) int userId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(RATE)
    Call<ParentResponse> userCommentService(@Header(AUTHORIZATION) String token,
                                  @Field(RateRequest.STAFF_ID) int userId,
                                  @Field(RateRequest.SERVICE_ID) String taskId,
                                  @Field(RateRequest.COMMENT) String comment);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_SERVICE_RATE)
    Call<ParentResponse> userRateService(@Header(AUTHORIZATION) String token,
                                  @Field(RateRequest.SERVICE_ID) String serviceId,
                                  @Field(RateRequest.RATE) float rate);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_MIDWIFE_RATE)
    Call<ParentResponse> userRateMidwife(@Header(AUTHORIZATION) String token,
                                         @Field(RateRequest.UNIQUE_KEY) String uniqueKey,
                                         @Field(RateRequest.RATE) float rate);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_MIDWIFE_COMMENT)
    Call<ParentResponse> userCommentMidwife(@Header(AUTHORIZATION) String token,
                                         @Field(RateRequest.UNIQUE_KEY) String uniqueKey,
                                         @Field(RateRequest.COMMENT) String comment);

    @FormUrlEncoded
    @POST(FORGET_PASSWORD)
    Call<ParentResponse> forgetPassword(@Field(LoginRequest.EMAIL) String email);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_SERVICE)
    Call<ParentResponse> cancelService(@Header(AUTHORIZATION) String token,
                                       @Field(ParentRequest.USER_ID) int userId,
                                       @Field("service_id") int serviceId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_WORKSHOP)
    Call<ParentResponse> cancelWorkshop(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId,
                                        @Field("workshop_id") int workshopId);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOP_PAYMENT)
    Call<ParentResponse> workshopPayment(@Header(AUTHORIZATION) String token,
                                         @Field(ParentRequest.USER_ID) int userId,
                                         @Field("workshop_id") int workshopId,
                                         @Field("payment_method") int paymentMethod);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SERVICE_PAYMENT)
    Call<ParentResponse> servicePayment(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId,
                                        @Field("service_id") int serviceId,
                                        @Field("payment_method") int paymentMethod);


    @Headers(HEADER_KEY)
    @GET(ALL_MIDWIFE)
    Call<MidwifeResponse> getAllMidwife(@Header(AUTHORIZATION) String token);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CHECK_MIDWIFE)
    Call<ParentResponse> checkMidwife(@Header(AUTHORIZATION) String token,
                                      @Field("midwife_id") int midwifeId,
                                      @Field("from") String from,
                                      @Field("to") String to,
                                      @Field("date") String date);


    @Headers(HEADER_KEY)
    @POST(RESERVE_MIDWIFE)
    Call<ReserveMidwifeResponse> reserveMidwife(@Header(AUTHORIZATION) String token,
                                                @Body() MidwifeRequestRequest request);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(ACTIVE_ACCOUNT)
    Call<ParentResponse> activeAccount(@Field("email") String email);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(RESEND_CODE)
    Call<ResendCodeResponse> resendActiveCode(@Field("email") String email);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_RESERVATION_MIDWIFE)
    Call<ParentResponse> cancelReservationMidwife(@Header(AUTHORIZATION) String token,
                                                  @Field("unique_key") String uniquekey);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(MIDWIFE_PAYMENT)
    Call<ParentResponse> midwifePayment(@Header(AUTHORIZATION) String token,
                                                  @Field("unique_key") String uniquekey,
                                                  @Field("payment_method") int paymentMethod);


}
