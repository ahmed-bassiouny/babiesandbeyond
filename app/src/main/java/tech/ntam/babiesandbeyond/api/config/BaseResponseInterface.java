package tech.ntam.babiesandbeyond.api.config;

/**
 * Created by bassiouny on 31/12/17.
 */

public interface BaseResponseInterface<T> {

    void onSuccess(T t);
    void onFailed(String errorMessage);
}
