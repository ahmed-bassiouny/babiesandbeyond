package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tech.ntam.babiesandbeyond.api.api_model.request.LoginRequest;
import tech.ntam.babiesandbeyond.api.api_model.request.RegisterRequest;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.LoginResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;

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
    String EVENTS = "all_events";

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
    @Headers({"Accept:application/json", "Authorization:Bearer  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjcxYmNiMjIwOWRkYzc3Y2ZhZmI1MTlhY2U3OTcwZGZmM2ZmOThkNzVmM2MyZmJjMTg0Zjc3YmE1OWE0MTM3ZjZjMWJlMDQxNDJmNzVjYzcwIn0.eyJhdWQiOiIxIiwianRpIjoiNzFiY2IyMjA5ZGRjNzdjZmFmYjUxOWFjZTc5NzBkZmYzZmY5OGQ3NWYzYzJmYmMxODRmNzdiYTU5YTQxMzdmNmMxYmUwNDE0MmY3NWNjNzAiLCJpYXQiOjE1MTQ5MDIwMzUsIm5iZiI6MTUxNDkwMjAzNSwiZXhwIjoxNTQ2NDM4MDM1LCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.Dj4wSyLC5o7PFDKL2tlPKCuK2XlJQQmrAvoGLG7FuLyj8XtrhPAiWoaHTe6pyxjdH4MqQFJnhDhRVhPlk4dKjBULNZjGkb-BsJFT8gQMrPgX8o2FlcvizY5j7WFFNuas6KJPgYs2CsO-PRjIcQqe80vLIEQYAKnGOGjHI283iMzHyDnx1RplhvHmha-VO9ZpcTLlLvUGCYC7H4opsAIrff4gdhOSzddisu2rp6wawc3pp49bzadaKG7-_XZ5_rdypPaDVZFicdGTtEtchKmo-QdVDRergyi2XJd2uVx325RqL783w0UE5b5OKJQ7sfAe_d24-3r-gqoZvEvN3UYxwySykcJePs444TwfvQiCRQ9JW7Nb4dm204POnJCV8IX5kxX_a_WTX02ImKUlwdBuTtPdj3M1MBYIAZVIhanDfYJztZTNjskuDO-JrSBB6YnZ4V8XdXCnFWQwd3AnNzyOLb84qDc-GbuvokAya6eY9z4NBxX_Er3PHl-fFlW9A8V0MZbRiehHHPN1HWYm72GiEBu1TH6hRuViLj4VMwwK8yprvT_VhT35SoBuwg803tmMcN49mY52QY9aXrmLcSUH-nDZUqHyVuVItvC_Gt_G36PnOlrojK64AQRpcupTcD1XrCIVOrfWFCS_Hb2uehzKpTi1jKBg2TshD0dEvDHUDzM"})
    @POST(EVENTS)
    Call<EventsResponse> getEvents(@Field(RegisterRequest.EMAIL) String email);
}
