package tech.ntam.babiesandbeyond.interfaces;

import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.Service;

/**
 * Created by Developer on 27/02/18.
 */

public interface ParseService {
    void getService(Service service);
    void getMidwife(MidwifeService midwifeService);
}
