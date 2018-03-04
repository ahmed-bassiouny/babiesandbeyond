package tech.ntam.mylibrary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bassiouny on 11/01/18.
 */

public class MyDateTimeFactor {

    public static final String DATE_TIME_FORMAT_NUMBER = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_TEXT = "dd MMM yyyy HH:mm a";
    public static final String DATE_FORMAT_TEXT = "dd MMM yyyy";
    public static final String DATE_DASH_FORMAT_TEXT = "yyyy-MM-dd";
    public static final String DAY_MONTH_FORMAT_TEXT = "dd MMM";
    public static final String TIME_FORMAT_AM_PM = "hh:mm a";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_WITHOUT_SECOND = "hh:mm";
    public static final String TIME_FORMAT_WITHOUT_SECOND_LARGE = "HH:mm";
    public static final String DATE_TIME_FORMAT_NUMBER_WITHOUT_SECOND = "yyyy-MM-dd HH:mm a";

    public static String changeDateFormatFromNumberToText(String dateStr) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_TIME_FORMAT_TEXT, Locale.ENGLISH);
        Date date = originalFormat.parse(dateStr);
        return targetFormat.format(date);
    }

    public static String changeFullDateTimeTODateTimeWithoutSecond(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER_WITHOUT_SECOND, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }

    }
    public static String changeDateTimeWithoutSecondToFullDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER_WITHOUT_SECOND, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        Date date ;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }

    }


    public static Date convertStringToDate(String date) {

        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
    public static Date convertStringToDateWithoutSecond(String date) {

        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER_WITHOUT_SECOND, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getDateFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT_TEXT, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getDayMonthFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DAY_MONTH_FORMAT_TEXT, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getTimeFromDateTime(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_NUMBER, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT_AM_PM, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static Calendar getDateTimeAfter24Hour() {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 24); // adds one hour
        return cal; // returns new date object, one hour in the future
    }

    public static String convertTimeFrom24To12(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT_AM_PM, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String convertTimeFrom24To12WithoutSecond(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(TIME_FORMAT_WITHOUT_SECOND, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT_AM_PM, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String convertTimeFrom12To24(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(TIME_FORMAT_AM_PM, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String convertTimeFrom12To24WithoutSecond(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat(TIME_FORMAT_AM_PM, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(TIME_FORMAT_WITHOUT_SECOND_LARGE, Locale.ENGLISH);
        Date date;
        try {
            date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String convertTimestampToString(long timestamp) {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_DASH_FORMAT_TEXT);
        Date date = new Date(timestamp);
        return sf.format(date);
    }

    public static long convertStringToTimestamp(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_DASH_FORMAT_TEXT);
        Date parsedDate = dateFormat.parse(date);
        return parsedDate.getTime();
    }

    public static String convertTimestampToDayOfWeek(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return getDayStringFromNumber(cal.get(Calendar.DAY_OF_WEEK));
    }
    public static long getHourBetweenTwoDate(Date firstDate,Date secondDate){
        long secs = (firstDate.getTime() - secondDate.getTime()) / 1000;
        long h =  secs / 3600;
        secs = secs % 3600;
        long mins = secs / 60;
        return mins > 1 ? (h+1):h;
    }

    private static String getDayStringFromNumber(int day) {
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }
    public static String convertDateStringToDayOfWeek(String item){
        try {
            return convertTimestampToDayOfWeek(convertStringToTimestamp(item));
        } catch (ParseException e) {
            return "";
        }
    }

}
