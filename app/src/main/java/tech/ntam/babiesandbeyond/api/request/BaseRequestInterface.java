package tech.ntam.babiesandbeyond.api.request;

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

/**
 * Created by Developer on 31/12/17.
 */
public interface BaseRequestInterface {

    /**
     * The constant HEADER_KEY.
     */
    String HEADER_KEY = "Accept:application/json";
    /**
     * The constant AUTHORIZATION.
     */
    String AUTHORIZATION = "Authorization";
    /**
     * The constant LOGIN.
     */
    String LOGIN = "login";
    /**
     * The constant REGISTER.
     */
    String REGISTER = "register";
    /**
     * The constant EVENTS.
     */
    String EVENTS = "all_events";
    /**
     * The constant REQUEST_SERVICE.
     */
    String REQUEST_SERVICE = "send_service_request";
    /**
     * The constant SERVICE.
     */
    String SERVICE = "services";
    /**
     * The constant SEND_STATUS_EVENT.
     */
    String SEND_STATUS_EVENT = "comming_or_not_comming_to_event";
    /**
     * The constant GROUP.
     */
    String GROUP = "all_groups";
    /**
     * The constant PROFILE.
     */
    String PROFILE = "user_profile";
    /**
     * The constant UPDATE_PASSWORD.
     */
    String UPDATE_PASSWORD = "change_password";
    /**
     * The constant HISTORY.
     */
    String HISTORY = "history";
    /**
     * The constant UPDATE_PROFILE.
     */
    String UPDATE_PROFILE = "update_profile";
    /**
     * The constant CREATE_GROUP.
     */
    String CREATE_GROUP = "create_group";
    /**
     * The constant WORKSHOPS.
     */
    String WORKSHOPS = "all_workshops";
    /**
     * The constant JOIN_GROUP.
     */
    String JOIN_GROUP = "join_group";
    /**
     * The constant LEAVE_GROUP.
     */
    String LEAVE_GROUP = "leave_group";
    /**
     * The constant ABOUT.
     */
    String ABOUT = "about";
    /**
     * The constant TERMS.
     */
    String TERMS = "terms-and-conditions/get";
    /**
     * The constant SEND_WORKSHOP_REQUEST.
     */
    String SEND_WORKSHOP_REQUEST = "send_workshop_request";
    /**
     * The constant NOTIFICATION.
     */
    String NOTIFICATION = "user_notifications";
    /**
     * The constant LOGOUT.
     */
    String LOGOUT = "logout";
    /**
     * The constant STAFF_TASK.
     */
    String STAFF_TASK = "staff/paid-services";
    /**
     * The constant RATE.
     */
    String RATE = "staff_rate_and_comment";
    /**
     * The constant FORGET_PASSWORD.
     */
    String FORGET_PASSWORD = "forget_password";
    /**
     * The constant CANCEL_SERVICE.
     */
    String CANCEL_SERVICE = "cancel_service";
    /**
     * The constant CANCEL_WORKSHOP.
     */
    String CANCEL_WORKSHOP = "cancel_workshop";
    /**
     * The constant SERVICE_PAYMENT.
     */
    String SERVICE_PAYMENT = "service_payment";
    /**
     * The constant WORKSHOP_PAYMENT.
     */
    String WORKSHOP_PAYMENT = "workshop_payment";
    /**
     * The constant ALL_MIDWIFE.
     */
    String ALL_MIDWIFE = "midwife/all";
    /**
     * The constant CHECK_MIDWIFE.
     */
    String CHECK_MIDWIFE = "midwife/check-midwife";
    /**
     * The constant RESERVE_MIDWIFE.
     */
    String RESERVE_MIDWIFE = "midwife/reserve";
    /**
     * The constant ACTIVE_ACCOUNT.
     */
    String ACTIVE_ACCOUNT = "active_account";
    /**
     * The constant RESEND_CODE.
     */
    String RESEND_CODE = "resend_verification_code";
    /**
     * The constant CANCEL_RESERVATION_MIDWIFE.
     */
    String CANCEL_RESERVATION_MIDWIFE = "midwife/cancel-reservation";
    /**
     * The constant MIDWIFE_PAYMENT.
     */
    String MIDWIFE_PAYMENT = "midwife/confirm-with-payment";
    /**
     * The constant LOGIN_WITH_SOCIAL.
     */
    String LOGIN_WITH_SOCIAL = "social_login";
    /**
     * The constant USER_SERVICE_RATE.
     */
    String USER_SERVICE_RATE = "user_service_rate";
    /**
     * The constant USER_HISTORY.
     */
    String USER_HISTORY = "user/history";
    /**
     * The constant USER_MIDWIFE_RATE.
     */
    String USER_MIDWIFE_RATE = "rate-service";
    /**
     * The constant USER_MIDWIFE_COMMENT.
     */
    String USER_MIDWIFE_COMMENT = "midwife/comment-service";
    /**
     * The constant CONTACT_US.
     */
    String CONTACT_US = "contact-us/post-message";
    /**
     * The constant MESSAGES.
     */
    String MESSAGES = "staff/messages";
    /**
     * The constant SEND_MESSAGE.
     */
    String SEND_MESSAGE = "staff/send-inbox-message";
    String GET_ARTICLES ="all_articles";

    /**
     * Login call.
     *
     * @param email             the email
     * @param password          the password
     * @param notificationToken the notification token
     * @return the call
     */
    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password,
                              @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);


    /**
     * Login with social call.
     *
     * @param email             the email
     * @param name              the name
     * @param notificationToken the notification token
     * @return the call
     */
    @FormUrlEncoded
    @POST(LOGIN_WITH_SOCIAL)
    Call<LoginResponse> loginWithSocial(@Field(LoginRequest.EMAIL) String email,
                                        @Field(LoginRequest.NAME) String name,
                                        @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);

    /**
     * Register call.
     *
     * @param name              the name
     * @param email             the email
     * @param password          the password
     * @param phone             the phone
     * @param notificationToken the notification token
     * @return the call
     */
    @FormUrlEncoded
    @POST(REGISTER)
    Call<RegisterResponse> register(@Field(RegisterRequest.NAME) String name,
                                    @Field(RegisterRequest.EMAIL) String email,
                                    @Field(RegisterRequest.PASSWORD) String password,
                                    @Field(RegisterRequest.PHONE) String phone,
                                    @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);


    /**
     * Gets events.
     *
     * @param token the token
     * @param email the email
     * @return the events
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(EVENTS)
    Call<EventsResponse> getEvents(@Header(AUTHORIZATION) String token,
                                   @Field(RegisterRequest.EMAIL) String email);


    /**
     * Gets user history.
     *
     * @param token the token
     * @return the user history
     */
    @Headers(HEADER_KEY)
    @POST(USER_HISTORY)
    Call<UserHistoryResponse> getUserHistory(@Header(AUTHORIZATION) String token);

    /**
     * Request service call.
     *
     * @param token      the token
     * @param userId     the user id
     * @param userTypeId the user type id
     * @param startDate  the start date
     * @param endDate    the end date
     * @param location   the location
     * @param latitude   the latitude
     * @param longitude  the longitude
     * @return the call
     */
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
                                            @Field(AskServiceRequest.LONGITUDE) double longitude,
                                            @Field("no_of_children") String noOfChildren,
                                            @Field("birth_date") String birthDate,
                                            @Field("additional_info") String additionalInfo,
                                            @Field("is_complex") int isComplex,
                                            @Field("nurse_type") String nurseType);


    /**
     * Gets my service.
     *
     * @param token the token
     * @param email the email
     * @return the my service
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SERVICE)
    Call<MyServiceResponse> getMyService(@Header(AUTHORIZATION) String token,
                                         @Field(RegisterRequest.EMAIL) String email);


    /**
     * Send status event call.
     *
     * @param token    the token
     * @param userId   the user id
     * @param isComing the is coming
     * @param eventId  the event id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SEND_STATUS_EVENT)
    Call<ParentResponse> sendStatusEvent(@Header(AUTHORIZATION) String token,
                                         @Field(StatusEvent.USER_ID) int userId,
                                         @Field(StatusEvent.IS_COMING) int isComing,
                                         @Field(StatusEvent.EVENT_ID) int eventId);

    /**
     * Gets groups.
     *
     * @param token the token
     * @param email the email
     * @return the groups
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(GROUP)
    Call<GroupResponse> getGroups(@Header(AUTHORIZATION) String token,
                                  @Field(RegisterRequest.EMAIL) String email);

    /**
     * Gets profile.
     *
     * @param token the token
     * @param email the email
     * @return the profile
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(PROFILE)
    Call<ProfileResponse> getProfile(@Header(AUTHORIZATION) String token,
                                     @Field(RegisterRequest.EMAIL) String email);

    /**
     * Update password call.
     *
     * @param token       the token
     * @param userId      the user id
     * @param password    the password
     * @param oldPassword the old password
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(UPDATE_PASSWORD)
    Call<ParentResponse> updatePassword(@Header(AUTHORIZATION) String token,
                                        @Field(UpdatePasswordRequest.USER_ID) int userId,
                                        @Field(UpdatePasswordRequest.PASSWORD) String password,
                                        @Field(UpdatePasswordRequest.OLD_PASSWORD) String oldPassword);

    /**
     * Gets history.
     *
     * @param token  the token
     * @param userId the user id
     * @return the history
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(HISTORY)
    Call<HistoryResponse> getHistory(@Header(AUTHORIZATION) String token,
                                     @Field(UpdatePasswordRequest.USER_ID) int userId);


    /**
     * Update profile call.
     *
     * @param token     the token
     * @param userId    the user id
     * @param userName  the user name
     * @param userPhoto the user photo
     * @param userPhone the user phone
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(UPDATE_PROFILE)
    Call<ParentResponse> updateProfile(@Header(AUTHORIZATION) String token,
                                       @Field(UpdateProfileRequest.USER_ID) int userId,
                                       @Field(UpdateProfileRequest.NAME) String userName,
                                       @Field(UpdateProfileRequest.PHOTO) String userPhoto,
                                       @Field(UpdateProfileRequest.PHONE) String userPhone);


    /**
     * Create group call.
     *
     * @param token       the token
     * @param userId      the user id
     * @param name        the name
     * @param description the description
     * @param photo       the photo
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CREATE_GROUP)
    Call<CreateGroupResponse> createGroup(@Header(AUTHORIZATION) String token,
                                          @Field(CreateGroupRequest.USER_ID) int userId,
                                          @Field(CreateGroupRequest.GROUP_NAME) String name,
                                          @Field(CreateGroupRequest.GROUP_DESCRIPTION) String description,
                                          @Field(CreateGroupRequest.GROUP_PHOTO) String photo);

    /**
     * Gets workshops.
     *
     * @param token  the token
     * @param userId the user id
     * @return the workshops
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOPS)
    Call<WorkshopResponse> getWorkshops(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId);

    /**
     * Join group call.
     *
     * @param token   the token
     * @param userId  the user id
     * @param groupId the group id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(JOIN_GROUP)
    Call<ParentResponse> joinGroup(@Header(AUTHORIZATION) String token,
                                   @Field(ParentRequest.USER_ID) int userId,
                                   @Field(GroupOptionRequest.GROUP_ID) int groupId);

    /**
     * Leave group call.
     *
     * @param token   the token
     * @param userId  the user id
     * @param groupId the group id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(LEAVE_GROUP)
    Call<ParentResponse> leaveGroup(@Header(AUTHORIZATION) String token,
                                    @Field(ParentRequest.USER_ID) int userId,
                                    @Field(GroupOptionRequest.GROUP_ID) int groupId);

    /**
     * Gets about.
     *
     * @return the about
     */
    @POST(ABOUT)
    Call<AboutResponse> getAbout();


    /**
     * Gets terms.
     *
     * @param token the token
     * @return the terms
     */
    @Headers(HEADER_KEY)
    @GET(TERMS)
    Call<AboutResponse> getTerms(@Header(AUTHORIZATION) String token);


    /**
     * Send workshop request call.
     *
     * @param token      the token
     * @param userId     the user id
     * @param workshopId the workshop id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SEND_WORKSHOP_REQUEST)
    Call<AddWorkshopResponse> sendWorkshopRequest(@Header(AUTHORIZATION) String token,
                                                  @Field(AskWorkshopRequest.USER_ID) int userId,
                                                  @Field(AskWorkshopRequest.WORKSHOP_ID) int workshopId);


    /**
     * Gets notification.
     *
     * @param token  the token
     * @param userId the user id
     * @return the notification
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(NOTIFICATION)
    Call<NotificationResponse> getNotification(@Header(AUTHORIZATION) String token,
                                               @Field(AskWorkshopRequest.USER_ID) int userId);

    /**
     * Logout call.
     *
     * @param userId the user id
     * @return the call
     */
    @FormUrlEncoded
    @POST(LOGOUT)
    Call<ParentResponse> logout(@Field(AskWorkshopRequest.USER_ID) int userId);


    /**
     * Gets tasks.
     *
     * @param token  the token
     * @param userId the user id
     * @return the tasks
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(STAFF_TASK)
    Call<StaffTasksResponse> getTasks(@Header(AUTHORIZATION) String token,
                                      @Field(StaffTasksRequest.STAFF_ID) int userId);

    /**
     * User comment service call.
     *
     * @param token   the token
     * @param userId  the user id
     * @param taskId  the task id
     * @param comment the comment
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(RATE)
    Call<ParentResponse> userCommentService(@Header(AUTHORIZATION) String token,
                                            @Field(RateRequest.STAFF_ID) int userId,
                                            @Field(RateRequest.SERVICE_ID) String taskId,
                                            @Field(RateRequest.COMMENT) String comment);


    /**
     * User rate service call.
     *
     * @param token     the token
     * @param serviceId the service id
     * @param rate      the rate
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_SERVICE_RATE)
    Call<ParentResponse> userRateService(@Header(AUTHORIZATION) String token,
                                         @Field(RateRequest.SERVICE_ID) String serviceId,
                                         @Field(RateRequest.RATE) int rate);


    /**
     * User rate midwife call.
     *
     * @param token     the token
     * @param uniqueKey the unique key
     * @param rate      the rate
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_MIDWIFE_RATE)
    Call<ParentResponse> userRateMidwife(@Header(AUTHORIZATION) String token,
                                         @Field(RateRequest.UNIQUE_KEY) String uniqueKey,
                                         @Field(RateRequest.RATE) int rate);

    /**
     * User comment midwife call.
     *
     * @param token     the token
     * @param uniqueKey the unique key
     * @param comment   the comment
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(USER_MIDWIFE_COMMENT)
    Call<ParentResponse> userCommentMidwife(@Header(AUTHORIZATION) String token,
                                            @Field(RateRequest.UNIQUE_KEY) String uniqueKey,
                                            @Field(RateRequest.COMMENT) String comment);

    /**
     * Forget password call.
     *
     * @param email the email
     * @return the call
     */
    @FormUrlEncoded
    @POST(FORGET_PASSWORD)
    Call<ParentResponse> forgetPassword(@Field(LoginRequest.EMAIL) String email);


    /**
     * Cancel service call.
     *
     * @param token     the token
     * @param userId    the user id
     * @param serviceId the service id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_SERVICE)
    Call<ParentResponse> cancelService(@Header(AUTHORIZATION) String token,
                                       @Field(ParentRequest.USER_ID) int userId,
                                       @Field("service_id") int serviceId);

    /**
     * Cancel workshop call.
     *
     * @param token      the token
     * @param userId     the user id
     * @param workshopId the workshop id
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_WORKSHOP)
    Call<ParentResponse> cancelWorkshop(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId,
                                        @Field("workshop_id") int workshopId);


    /**
     * Workshop payment call.
     *
     * @param token         the token
     * @param userId        the user id
     * @param workshopId    the workshop id
     * @param paymentMethod the payment method
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOP_PAYMENT)
    Call<ParentResponse> workshopPayment(@Header(AUTHORIZATION) String token,
                                         @Field(ParentRequest.USER_ID) int userId,
                                         @Field("workshop_id") int workshopId,
                                         @Field("payment_method") int paymentMethod);


    /**
     * Service payment call.
     *
     * @param token         the token
     * @param userId        the user id
     * @param serviceId     the service id
     * @param paymentMethod the payment method
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SERVICE_PAYMENT)
    Call<ParentResponse> servicePayment(@Header(AUTHORIZATION) String token,
                                        @Field(ParentRequest.USER_ID) int userId,
                                        @Field("service_id") int serviceId,
                                        @Field("payment_method") int paymentMethod);


    /**
     * Gets all midwife.
     *
     * @param token the token
     * @return the all midwife
     */
    @Headers(HEADER_KEY)
    @GET(ALL_MIDWIFE)
    Call<MidwifeResponse> getAllMidwife(@Header(AUTHORIZATION) String token);


    /**
     * Check midwife call.
     *
     * @param token     the token
     * @param midwifeId the midwife id
     * @param from      the from
     * @param to        the to
     * @param date      the date
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CHECK_MIDWIFE)
    Call<ParentResponse> checkMidwife(@Header(AUTHORIZATION) String token,
                                      @Field("midwife_id") int midwifeId,
                                      @Field("from") String from,
                                      @Field("to") String to,
                                      @Field("date") String date);


    /**
     * Reserve midwife call.
     *
     * @param token   the token
     * @param request the request
     * @return the call
     */
    @Headers(HEADER_KEY)
    @POST(RESERVE_MIDWIFE)
    Call<ReserveMidwifeResponse> reserveMidwife(@Header(AUTHORIZATION) String token,
                                                @Body() MidwifeRequestRequest request);


    /**
     * Active account call.
     *
     * @param email the email
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(ACTIVE_ACCOUNT)
    Call<ParentResponse> activeAccount(@Field("email") String email);


    /**
     * Resend active code call.
     *
     * @param email the email
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(RESEND_CODE)
    Call<ResendCodeResponse> resendActiveCode(@Field("email") String email);


    /**
     * Cancel reservation midwife call.
     *
     * @param token     the token
     * @param uniquekey the uniquekey
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CANCEL_RESERVATION_MIDWIFE)
    Call<ParentResponse> cancelReservationMidwife(@Header(AUTHORIZATION) String token,
                                                  @Field("unique_key") String uniquekey);


    /**
     * Midwife payment call.
     *
     * @param token         the token
     * @param uniquekey     the uniquekey
     * @param paymentMethod the payment method
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(MIDWIFE_PAYMENT)
    Call<ParentResponse> midwifePayment(@Header(AUTHORIZATION) String token,
                                        @Field("unique_key") String uniquekey,
                                        @Field("payment_method") int paymentMethod);


    /**
     * Contact us call.
     *
     * @param token   the token
     * @param name    the name
     * @param email   the email
     * @param phone   the phone
     * @param subject the subject
     * @param reason  the reason
     * @param message the message
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CONTACT_US)
    Call<ParentResponse> contactUs(@Header(AUTHORIZATION) String token,
                                   @Field("name") String name,
                                   @Field("email") String email,
                                   @Field("phone") String phone,
                                   @Field("subject") String subject,
                                   @Field("reason") String reason,
                                   @Field("message") String message);


    /**
     * Gets message admin.
     *
     * @param token the token
     * @return the message admin
     */
    @Headers(HEADER_KEY)
    @GET(MESSAGES)
    Call<MessageListAdminResponse> getMessageAdmin(@Header(AUTHORIZATION) String token);

    /**
     * Send message to admin call.
     *
     * @param token   the token
     * @param message the message
     * @return the call
     */
    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SEND_MESSAGE)
    Call<MessageAdminResponse> sendMessageToAdmin(@Header(AUTHORIZATION) String token,
                                                  @Field("message") String message);

    @POST(GET_ARTICLES)
    Call<ArticleResponse> getArticles();


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST("get_service_quotation")
    Call<ParentResponse> getServiceQuotation(@Header(AUTHORIZATION) String token,
                                   @Field("user_id") int userId,
                                   @Field("service_name") String service_name,
                                   @Field("no_of_days_required_per_month") String noOfDaysRequiredPerMonth,
                                   @Field("no_of_hours_aday") String noOfHoursAday,
                                   @Field("specific_request") String specificRequest,
                                   @Field("location") String location);


}
