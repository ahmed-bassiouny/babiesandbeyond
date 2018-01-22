package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Task;

/**
 * Created by bassiouny on 22/01/18.
 */

public class StaffTasksResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Task> taskList;

    public List<Task> getTaskList() {
        if (taskList == null)
            taskList = new ArrayList<>();
        return taskList;
    }
}
