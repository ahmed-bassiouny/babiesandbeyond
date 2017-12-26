package tech.ntam.mylibrary;

/**
 * Created by bassiouny on 26/12/17.
 */

public class DummyGroupModel {

    public String status;
    public String name;
    public String createdBy;
    public String description;
    public String date;
    public boolean leave;

    public DummyGroupModel(String status, String name, String createdBy, String description, String date, boolean leave) {
        this.status = status;
        this.name = name;
        this.createdBy = createdBy;
        this.description = description;
        this.date = date;
        this.leave = leave;
    }
}
