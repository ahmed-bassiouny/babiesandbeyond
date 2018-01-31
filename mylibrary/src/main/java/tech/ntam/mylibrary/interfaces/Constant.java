package tech.ntam.mylibrary.interfaces;

/**
 * Created by bassiouny on 23/01/18.
 */

public class Constant {
    // this status about service and workshop
    public static final String PENDING = "Pending";
    public static final String CONFIRMATION_WITHOUT_PAYMENT = "Confirm Without Payment";
    public static final String CONFIRMATION_WITH_PAYMENT  = "Confirm With Payment";

    // this status about user not in group , pending , in group
    public static final String USER_PENDING_GROUP = "Pending";
    public static final String USER_IN_GROUP = "In Group";
    public static final String USER_OUT_GROUP = "Not in Group";


    // this status text about user not in group , pending , in group
    public static final String USER_PENDING_GROUP_TEXT = "Pending Join";
    public static final String USER_IN_GROUP_TEXT = "Joined";
    public static final String USER_PENDING_APPROVAL_GROUP_TEXT= "Pending Admin Approval";


}
