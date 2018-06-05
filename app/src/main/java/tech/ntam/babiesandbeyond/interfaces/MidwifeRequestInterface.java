package tech.ntam.babiesandbeyond.interfaces;

import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;

/**
 * Created by Developer on 25/02/18.
 */

public interface MidwifeRequestInterface {
    void editRequest(MidwifeRequestModel midwifeRequestModel , int position);
    void removeRequest(MidwifeRequestModel midwifeRequestModel);
}
