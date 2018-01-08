package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.AskServiceRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.CreateGroupRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RegisterRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.StatusEvent;
import tech.ntam.babiesandbeyond.api.api_model.request.UpdatePasswordRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.UpdateProfileRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.GroupResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.HistoryResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MyServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ProfileResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.model.Group;

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

    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password,
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

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(REQUEST_SERVICE)
    Call<ParentResponse> requestService(@Header(AUTHORIZATION) String token,
                                        @Field(AskServiceRequest.USER_ID) int userId,
                                        @Field(AskServiceRequest.USER_TYPE_ID) int userTypeId,
                                        @Field(AskServiceRequest.START_DATE) String startDate,
                                        @Field(AskServiceRequest.END_DATE) String endDate,
                                        @Field(AskServiceRequest.LOCATION) String location);


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
                                        @Field(UpdatePasswordRequest.PASSWORD) String password);

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
    Call<ParentResponse> createGroup(@Header(AUTHORIZATION) String token,
                                       @Field(CreateGroupRequest.USER_ID) int userId,
                                       @Field(CreateGroupRequest.GROUP_NAME) String name,
                                       @Field(CreateGroupRequest.GROUP_DESCRIPTION) String description,
                                       @Field(CreateGroupRequest.GROUP_PHOTO) String photo);
}
