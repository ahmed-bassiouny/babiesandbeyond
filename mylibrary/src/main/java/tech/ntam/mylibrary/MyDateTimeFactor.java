package tech.ntam.mylibrary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bassiouny on 11/01/18.
 */

public class MyDateTimeFactor {

    public static final String DATE_TIME_FORMAT_NUMBER = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_TEXT = "dd MMM yyyy HH:mm a";
    public static final String DATE_FORMAT_TEXT = "dd MMM yyyy";
    public static final String DAY_MONTH_FORMAT_TEXT = "dd MMM";
    public static final String TIME_FORMAT = "HH:mm a";

    public static String changeDateFormatFromNumberToText(String dateStr) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_TIME_FORMAT_TEXT,Locale.ENGLISH);
        Date date = originalFormat.parse(dateStr);
        return targetFormat.format(date);
    }

    public static Date convertStringToDate(String date) {

        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getDateFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT_TEXT,Locale.ENGLISH);
        Date date ;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String getDayMonthFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DAY_MONTH_FORMAT_TEXT,Locale.ENGLISH);
        Date date ;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String getTimeFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
        Date date ;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }
}
