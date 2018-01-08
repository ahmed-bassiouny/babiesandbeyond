package tech.ntam.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bassiouny on 17/12/17.
 */

public class Utils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

    public static Date convertStringToDate(String date) {

        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static void MyGlide(Activity activity, ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void convertImageFromBitmapToStringBase64(final Bitmap bitmap, final ProcessInterface processInterface) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                final String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                processInterface.completed(encoded);
            }
        }).start();

    }

    public interface ProcessInterface {
        void completed(String item);
    }
}
