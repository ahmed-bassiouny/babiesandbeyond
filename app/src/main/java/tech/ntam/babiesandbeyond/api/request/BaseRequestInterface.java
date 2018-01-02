package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RegisterRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;

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

    String LOGIN = "login";
    String REGISTER = "register";

    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                              @Field(LoginRequest.PASSWORD) String password);

    @FormUrlEncoded
    @POST(REGISTER)
    Call<ParentResponse> register(@Field(RegisterRequest.NAME) String name,
                                  @Field(RegisterRequest.EMAIL) String email,
                                  @Field(RegisterRequest.PASSWORD) String password,
                                  @Field(RegisterRequest.PHONE) String phone);
}
