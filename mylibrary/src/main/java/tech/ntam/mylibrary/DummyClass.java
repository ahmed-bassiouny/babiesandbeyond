package tech.ntam.mylibrary;

import android.graphics.Color;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

/**
 * Created by bassiouny on 26/12/17.
 */

public class DummyClass {

  /*  public static void setDaySelected(CompactCalendarView compactCalendarView){

        long l = System.currentTimeMillis();
        Event ev1 = new Event(Color.GREEN,l , "fds");
        compactCalendarView.addEvent(ev1,false);
    }*/
    public static void setShortText(TextView shortText){
        shortText.setText("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..." +
                "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain..." );
    }
    public static void setLongText(TextView longText){
        longText.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
    }
    public static void setTitleText(TextView titleText){
        titleText.setText("Lorem Ipsum");
    }
    public static void setDateTimeText(TextView dateTimeText){
        dateTimeText.setText("27-12-2017 6:30");
    }
}
