package tech.ntam.adminapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    String WORKSHOP_REQUESTS = "workshop_requests";

    String FORGET_PASSWORD = "forget_password";
    String GET_AVALIABLE_STAFF = "get_avaliable_staff";
    String ASSIGN_TASK = "assign_service_to_staff";
    String WORKSHOP_INVOICE = "create_workshop_invoice";
    String ALL_MIDWIFE = "midwife/all";
    String MIDWIFE_REQUESTS = "midwife/get-reservations";
    String APPROVE_MIDWIFE_REQUEST = "midwife/confirm-without-payment";
    String CLIENTS = "user/all-clients";
    String ADD_USER = "add_user";


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
    @Headers(HEADER_KEY)
    @POST(WORKSHOP_REQUESTS)
    Call<WorkshopListResponse> getWorkshopLRequest(@Header(AUTHORIZATION) String token,
                                                   @Field("admin_id") int adminId);

    @FormUrlEncoded
    @POST(FORGET_PASSWORD)
    Call<ParentResponse> forgetPassword(@Field(LoginRequest.EMAIL) String email);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(GET_AVALIABLE_STAFF)
    Call<AvailableStaffResponse> getAvaliableStaff(@Header(AUTHORIZATION) String token,
                                                   @Field("admin_id") int adminId,
                                                   @Field("service_type_id") int serviceTypeId,
                                                   @Field("service_start_date") String serviceStartDate,
                                                   @Field("service_end_date") String serviceEndDate);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(ASSIGN_TASK)
    Call<ParentResponse> assignStaff(@Header(AUTHORIZATION) String token,
                                     @Field("admin_id") int adminId,
                                     @Field("staff_id") int staffId,
                                     @Field("service_id") int requestId,
                                     @Field("service_type_name") String serviceTypeName,
                                     @Field("user_id") int userId);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(WORKSHOP_INVOICE)
    Call<ParentResponse> createWorkshopInvoice(@Header(AUTHORIZATION) String token,
                                               @Field("admin_id") int adminId,
                                               @Field("user_id") String userId,
                                               @Field("user_workshop_id") int userWorkshopId);


    @Headers(HEADER_KEY)
    @GET(ALL_MIDWIFE)
    Call<MidwifeResponse> getAllMidwife(@Header(AUTHORIZATION) String token);

    @Headers(HEADER_KEY)
    @POST(MIDWIFE_REQUESTS)
    Call<MidwifeRequestsResponse> getMidwifeRequests(@Header(AUTHORIZATION) String token);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(APPROVE_MIDWIFE_REQUEST)
    Call<ParentResponse> approveMidwifeRequest(@Header(AUTHORIZATION) String token,
                                               @Field("admin_id") int adminId,
                                               @Field("unique_key") String uniqueKey);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(CLIENTS)
    Call<ClientsResponse> getClients(@Header(AUTHORIZATION) String token,
                                     @Field("page") int page);

    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(ADD_USER)
    Call<AddClientResponse> addClient(@Header(AUTHORIZATION) String token,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("photo") String photo,
                                    @Field("birthday") String birthday,
                                    @Field("password") String password);
}
