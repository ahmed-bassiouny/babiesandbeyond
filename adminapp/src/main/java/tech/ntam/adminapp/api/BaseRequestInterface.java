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


}
