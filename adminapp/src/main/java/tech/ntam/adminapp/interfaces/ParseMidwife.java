package tech.ntam.adminapp.interfaces;

import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.model.MidwifeService;

/**
 * Created by Developer on 28/02/18.
 */

public interface ParseMidwife {
    void assignmentMidwife(MidwifeService request, int position);
    void viewMidwife(Midwife midwife);
}
