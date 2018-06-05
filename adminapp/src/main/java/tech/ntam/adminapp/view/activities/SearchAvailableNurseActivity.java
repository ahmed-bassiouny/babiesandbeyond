package tech.ntam.adminapp.view.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.view.adapter.NurseItemAdapter;
import tech.ntam.adminapp.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class SearchAvailableNurseActivity extends MyToolbar {

    private EditText etChooseDateFrom;
    private EditText etChooseDateTo;
    private Button btnSend;
    private ProgressBar progress;
    private RecyclerView recycler;
    private Calendar now;
    private NurseItemAdapter nurseItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_available_nurse);
        setupToolbar("Available Nurse");
        findView();
        onClick();
        now = Calendar.getInstance();
    }

    private void findView() {
        etChooseDateFrom = findViewById(R.id.et_choose_date_from);
        etChooseDateTo = findViewById(R.id.et_choose_date_to);
        btnSend = findViewById(R.id.btn_send);
        progress = findViewById(R.id.progress);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(this));

    }

    private void onClick() {
        etChooseDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTime(etChooseDateFrom);
            }
        });
        etChooseDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTime(etChooseDateTo);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etChooseDateFrom.getText().toString().trim().isEmpty()) {
                    etChooseDateFrom.setError(getString(R.string.invalid_Date));
                } else if (etChooseDateTo.getText().toString().trim().isEmpty()) {
                    etChooseDateTo.setError(getString(R.string.invalid_Date));
                    etChooseDateFrom.setError(null);
                } else {
                    etChooseDateFrom.setError(null);
                    etChooseDateTo.setError(null);
                    loadData();
                }
            }
        });
    }

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        RequestAndResponse.getAvailableNurse(this, MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(etChooseDateFrom.getText().toString()),
                MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(etChooseDateTo.getText().toString())
                , new BaseResponseInterface<List<Service>>() {
                    @Override
                    public void onSuccess(List<Service> services) {
                        nurseItemAdapter = new NurseItemAdapter(SearchAvailableNurseActivity.this, services);
                        recycler.setAdapter(nurseItemAdapter);
                        progress.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(SearchAvailableNurseActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void showDateTime(final EditText editText) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showDateTime(editText, year, monthOfYear, dayOfMonth);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setYearRange(Calendar.getInstance().get(Calendar.YEAR),(Calendar.getInstance().get(Calendar.YEAR)+1));
        dpd.setMinDate(MyDateTimeFactor.getDateTimeAfter24Hour());
        dpd.setAccentColor(getResources()
                .getColor(R.color.colorPrimary));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void showDateTime(final EditText editText, final int year, final int monthOfYear, final int dayOfMonth) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                String date = MyDateTimeFactor.changeFullDateTimeTODateTimeWithoutSecond(year + "-" + getValueDateDigit(monthOfYear + 1) + "-" + getValueDateDigit(dayOfMonth) + " " + getValueDateDigit(hourOfDay) + ":" + getValueDateDigit(minute) + ":" + getValueDateDigit(second));
                editText.setText(date);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);

        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setThemeDark(true);
        timePickerDialog.setAccentColor(getResources()
                .getColor(R.color.colorPrimary));
        timePickerDialog.show(getFragmentManager(), "Timepickerdialog");
    }

    private String getValueDateDigit(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
    }
}
