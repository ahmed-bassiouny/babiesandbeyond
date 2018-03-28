package tech.ntam.mylibrary.apiCongif;

/**
 * Created by Developer on 31/12/17.
 */

public interface BaseResponseInterface<T> {

    void onSuccess(T t);
    void onFailed(String errorMessage);
}
