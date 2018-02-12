package tech.ntam.adminapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by bassiouny on 31/12/17.
 */

public interface BaseRequestInterface {

    String HEADER_KEY = "Accept:application/json";
    String AUTHORIZATION = "Authorization";
    String LOGIN = "login";
    String STAFF_REQUESTS = "staff_requests";
    String WORKSHOP_LIST = "admin_workshops";
    String FORGET_PASSWORD = "forget_password";


    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password,
                              @Field(LoginRequest.NOTIFICATION_TOKEN) String notificationToken);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(STAFF_REQUESTS)
    Call<StaffResponse> getStaffRequests(@Header(AUTHORIZATION) String token,
                                         @Field("admin_id") int adminId,
                                         @Field("service_type_id") int serviceTypeId);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOP_LIST)
    Call<WorkshopListResponse> getWorkshopLList(@Header(AUTHORIZATION) String token,
                                         @Field("admin_id") int adminId);

    @FormUrlEncoded
    @POST(FORGET_PASSWORD)
    Call<ParentResponse> forgetPassword(@Field(LoginRequest.EMAIL) String email);
}
