package tech.ntam.babiesandbeyond.api.request;

import retrofit2.Response;
import tech.ntam.babiesandbeyond.api.api_model.ParentResponse;
import tech.ntam.babiesandbeyond.api.config.ApiConfig;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;

/**
 * Created by bassiouny on 31/12/17.
 */

public class RequestAndResponse {


    // base request
    private static BaseRequestInterface baseRequestInterface = ApiConfig.getRetrofit().create(BaseRequestInterface.class);
    private final String errorMsg = "Sorry we can\'t load data";

    private void checkValidResult(Response<ParentResponse> tResponse, Class<?> aClass, BaseResponseInterface baseResponseInterface) {
        // get response to check if request valid or not
        // get result object to pass it to base response interface
        if (tResponse.code() == 200) {
            if (tResponse.body().getStatus()) {
                baseResponseInterface.onSuccess(aClass);
            } else {
                baseResponseInterface.onFailed(tResponse.body().getMessage());
            }
        } else {
            // this case mean response code not equal 200
            baseResponseInterface.onFailed(errorMsg);
        }
    }
}
