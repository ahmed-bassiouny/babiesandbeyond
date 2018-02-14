package tech.ntam.adminapp.api;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.ApiConfig;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {

    private static String api_error = "API Error";

    // base request
    private static BaseRequestInterface baseRequestInterface = ApiConfig.getRetrofit().create(BaseRequestInterface.class);

    private static <T> void checkValidResult(int responseCode, boolean responseStatus, T object, String errorMsg, BaseResponseInterface baseResponseInterface) {
        // get response to check if request valid or not
        // get result object to pass it to base response interface
        if (responseCode == 200) {
            if (responseStatus) {
                baseResponseInterface.onSuccess(object);
            } else {
                baseResponseInterface.onFailed(errorMsg);
            }
        } else {
            // this case mean response code not equal 200
            baseResponseInterface.onFailed(errorMsg);
        }
    }

    public static void login(Context context, String email, String password, final BaseResponseInterface<User> anInterface) {
        Call<LoginResponse> response = baseRequestInterface.login(email, password, UserSharedPref.getNotificationToken(context));
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getUserData().getUser(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getStaff(Context context, int serviceTypeId, final BaseResponseInterface<Staff> anInterface) {
        Call<StaffResponse> response = baseRequestInterface.getStaffRequests(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context), serviceTypeId);
        response.enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getStaff(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<StaffResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getWorkshopLList(Context context, final BaseResponseInterface<List<Workshop>> anInterface) {
        Call<WorkshopListResponse> response = baseRequestInterface.getWorkshopLList(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context));
        response.enqueue(new Callback<WorkshopListResponse>() {
            @Override
            public void onResponse(Call<WorkshopListResponse> call, Response<WorkshopListResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getWorkshops(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<WorkshopListResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void forgetPassword(String email, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.forgetPassword(email);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void getAvaliableStaff(Context context,int serviceTypeId,String serviceStartDate,String serviceEndDate, final BaseResponseInterface<List<Service>> anInterface) {
        Call<AvailableStaffResponse> response = baseRequestInterface.getAvaliableStaff(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context),serviceTypeId,serviceStartDate,serviceEndDate);
        response.enqueue(new Callback<AvailableStaffResponse>() {
            @Override
            public void onResponse(Call<AvailableStaffResponse> call, Response<AvailableStaffResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getServices(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AvailableStaffResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void assignStaff(Context context,String serviceTypeName,int staffId,int requestId,int userId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.assignStaff(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context),staffId,requestId,serviceTypeName,userId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMessage(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

}
