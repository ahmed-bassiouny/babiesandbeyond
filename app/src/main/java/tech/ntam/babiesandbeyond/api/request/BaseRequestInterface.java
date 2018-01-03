package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.AskServiceRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RegisterRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.MyServiceResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;

/**
 * Created by bassiouny on 31/12/17.
 */

public interface BaseRequestInterface {

    /*
    * example
    *  @FormUrlEncoded
    @POST(ALL_POST)
    Call<PostListResponse> getAllPost(@Field(ParentRequest.USER_ID_KEY) int userId,
                                      @Field(ParentRequest.PAGE_NUMBER_KEY) int pageNumber,
                                      @Field(ParentRequest.EVENT_KEY) int event_id);
    * */

    String HEADER_KEY = "Accept:application/json";
    String AUTHORIZATION = "Authorization";
    String LOGIN = "login";
    String REGISTER = "register";
    String EVENTS = "all_events";
    String REQUEST_SERVICE = "send_service_request";
    String SERVICE ="services";

    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password);

    @FormUrlEncoded
    @POST(REGISTER)
    Call<RegisterResponse> register(@Field(RegisterRequest.NAME) String name,
                                    @Field(RegisterRequest.EMAIL) String email,
                                    @Field(RegisterRequest.PASSWORD) String password,
                                    @Field(RegisterRequest.PHONE) String phone);


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
                                          @Field(AskServiceRequest.USER_TYPE_ID) String userTypeId,
                                          @Field(AskServiceRequest.START_DATE) String startDate,
                                          @Field(AskServiceRequest.END_DATE) String endDate,
                                          @Field(AskServiceRequest.LOCATION) String location);


    @FormUrlEncoded
    @Headers(HEADER_KEY)
    @POST(SERVICE)
    Call<MyServiceResponse> getMyService(@Header(AUTHORIZATION) String token,
                                       @Field(RegisterRequest.EMAIL) String email);
}
