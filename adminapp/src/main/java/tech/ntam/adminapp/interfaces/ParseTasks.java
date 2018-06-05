package tech.ntam.adminapp.interfaces;

import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;

/**
 * Created by Developer on 11/01/18.
 */

public interface ParseTasks {
    void assignmentTask(Request request,int position);
    void viewService(Service service);
}
