package tech.ntam.mylibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bassiouny on 17/12/17.
 */

public class Utils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // value => i will send string from activity to another
    public static void goToFragment(int container, FragmentActivity fragmentActivity,
                                    Fragment fragment, boolean supportBack,
                                    @Nullable Bundle bundle) {

        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (supportBack)
            fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit();
    }

    public static String getMonthString(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static void setDate(TextView textView, Date date) {
        // this method take date and put it in text view
        // ex 20-11-2017 => novmcer 2017
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        textView.setText(Utils.getMonthString(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR));
    }

    public static void setCurrentDate(TextView textView) {
        // this method take date and put it in text view
        // ex 20-11-2017 => novmcer 2017
        Date date = new Date();
        setDate(textView, date);
    }

    public static String getValueFromString(String item) {
        if (item == null)
            item = "";
        return item;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
