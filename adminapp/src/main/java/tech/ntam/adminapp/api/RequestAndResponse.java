package tech.ntam.adminapp.api;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ntam.adminapp.model.Client;
import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.model.MidwifeService;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.ApiConfig;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by Developer on 31/12/17.
 */

public class RequestAndResponse {

    private static String api_error = "Server is not responding";

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
    public static void getWorkshopLRequest(Context context, final BaseResponseInterface<List<Workshop>> anInterface) {
        Call<WorkshopListResponse> response = baseRequestInterface.getWorkshopLRequest(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context));
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

    public static void createWorkshopInvoice(Context context,int userWorkshopId,String userId, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.createWorkshopInvoice(UserSharedPref.getTokenWithHeader(context), UserSharedPref.getId(context),userId,userWorkshopId);
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


    public static void getAllMidwife(Context context, final BaseResponseInterface<List<Midwife>> anInterface) {
        Call<MidwifeResponse> response = baseRequestInterface.getAllMidwife(
                UserSharedPref.getTokenWithHeader(context));
        response.enqueue(new Callback<MidwifeResponse>() {
            @Override
            public void onResponse(Call<MidwifeResponse> call, Response<MidwifeResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMidwifeList(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MidwifeResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }


    public static void getMidwifeRequests(Context context, final BaseResponseInterface<List<MidwifeService>> anInterface) {
        Call<MidwifeRequestsResponse> response = baseRequestInterface.getMidwifeRequests(
                UserSharedPref.getTokenWithHeader(context));
        response.enqueue(new Callback<MidwifeRequestsResponse>() {
            @Override
            public void onResponse(Call<MidwifeRequestsResponse> call, Response<MidwifeRequestsResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getMidwifeServices(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<MidwifeRequestsResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void approveMidwifeRequest(Context context,String uniqueKey, final BaseResponseInterface<String> anInterface) {
        Call<ParentResponse> response = baseRequestInterface.approveMidwifeRequest(
                UserSharedPref.getTokenWithHeader(context),
                UserSharedPref.getId(context), uniqueKey);
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

    public static void getClients(Context context,int page, final BaseResponseInterface<List<Client>> anInterface) {
        Call<ClientsResponse> response = baseRequestInterface.getClients(
                UserSharedPref.getTokenWithHeader(context),
                page);
        response.enqueue(new Callback<ClientsResponse>() {
            @Override
            public void onResponse(Call<ClientsResponse> call, Response<ClientsResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getClients(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<ClientsResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void addClient(Context context,String name,
                                  String email,String phone, String photo,
                                  String birthday,String password, final BaseResponseInterface<Client> anInterface) {
        Call<AddClientResponse> response = baseRequestInterface.addClient(
                UserSharedPref.getTokenWithHeader(context),
                name,email,phone,photo,birthday,password);
        response.enqueue(new Callback<AddClientResponse>() {
            @Override
            public void onResponse(Call<AddClientResponse> call, Response<AddClientResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getClient(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<AddClientResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }

    public static void getAvailableNurse(Context context,String startDate,
                                 String endDate, final BaseResponseInterface<List<Service>> anInterface) {
        Call<NursesResponse> response = baseRequestInterface.getAvailableNurse(
                UserSharedPref.getTokenWithHeader(context),
                startDate,endDate);
        response.enqueue(new Callback<NursesResponse>() {
            @Override
            public void onResponse(Call<NursesResponse> call, Response<NursesResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getNurses(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<NursesResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }
    public static void getOccupiedNurses(Context context,String date, final BaseResponseInterface<List<Service>> anInterface) {
        Call<NursesResponse> response = baseRequestInterface.getOccupiedNurses(
                UserSharedPref.getTokenWithHeader(context), date);
        response.enqueue(new Callback<NursesResponse>() {
            @Override
            public void onResponse(Call<NursesResponse> call, Response<NursesResponse> response) {
                if (response.body() == null) {
                    anInterface.onFailed(api_error);
                    return;
                }
                checkValidResult(response.code(), response.body().getStatus()
                        , response.body().getNurses(), response.body().getMessage(), anInterface);
            }

            @Override
            public void onFailure(Call<NursesResponse> call, Throwable t) {
                anInterface.onFailed(Utils.getExceptionText(t));
            }
        });
    }


}
