package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 08/01/18.
 */

public class UserWorkshopController {

    private Activity activity;

    public UserWorkshopController(Activity activity) {
        this.activity = activity;
    }

    public void getWorkshop(final CompactCalendarView compactCalendarView) {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.getWorkshops(activity, new BaseResponseInterface<List<Workshop>>() {
            @Override
            public void onSuccess(List<Workshop> workshops) {
                setWorkshopInCalendar(compactCalendarView, workshops);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setWorkshopInCalendar(CompactCalendarView compactCalendarView, List<Workshop> workshops) {
        List<Event> events = new ArrayList<>();
        for (Workshop item : workshops) {
            events.add(new Event(activity.getResources().getColor(R.color.gray_bold), MyDateTimeFactor.convertStringToDate(item.getStartDate()).getTime(), item));
        }
        if (compactCalendarView != null)
            compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(events);
    }
}
