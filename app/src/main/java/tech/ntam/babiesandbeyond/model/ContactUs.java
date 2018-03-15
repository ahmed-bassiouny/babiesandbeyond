package tech.ntam.babiesandbeyond.model;

/**
 * Created by bassiouny on 28/01/18.
 */

public class ContactUs {

    private static String contactUs;

    public static String getContactUs() {
        if(contactUs == null)
            contactUs = "";
        return contactUs;
    }

    public static void setContactUs(String about) {
        ContactUs.contactUs = about;
    }
}
