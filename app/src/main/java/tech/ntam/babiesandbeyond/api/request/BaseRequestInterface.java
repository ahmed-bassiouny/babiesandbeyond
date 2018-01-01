package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;

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

    @FormUrlEncoded
    @POST(LOGIN)
    Call<LoginResponse> login(@Field(LoginRequest.EMAIL) String email,
                                  @Field(LoginRequest.PASSWORD) String password);
}
