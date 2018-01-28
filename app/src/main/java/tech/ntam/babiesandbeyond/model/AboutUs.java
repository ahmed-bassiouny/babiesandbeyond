package tech.ntam.babiesandbeyond.model;

import android.text.Html;

/**
 * Created by bassiouny on 28/01/18.
 */

public class AboutUs {

    private static String about;

    public static String getAbout() {
        if(about == null)
            about = "";
        return about;
    }

    public static void setAbout(String about) {
        AboutUs.about = about;
    }
}
