package com.spf.panditji.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.spf.panditji.R;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.util.ApiUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAvailabilityScreen extends AppCompatActivity {

    private PujaDetailModel pujaDetailModel;
    private TextView datePickerButton;
    private int day;
    private int month;
    private int year;
    private DatePickerDialog datePickerDialog;
    private String selectedDate;
    private TextView checkAvailability;
    private boolean panditSelected = false;
    private TextView selectTime;
    private int mHour;
    private int mMinute;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availability_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Details");

        datePickerDialog = new DatePickerDialog(CheckAvailabilityScreen.this, datePickerListener, 2020, 1, 1);

        pujaDetailModel = getIntent().getParcelableExtra("pooja_model");

        List<String> keyPoints = Arrays.asList(pujaDetailModel.getKey_point().split("##"));

        List<String> ourPromise = Arrays.asList(pujaDetailModel.getOur_pact().split("##"));

        List<String> steps = Arrays.asList(pujaDetailModel.getStep().split("##"));

        setUpKeyPointsList((RecyclerView) findViewById(R.id.key_points_list),keyPoints);
        setUpKeyPointsList((RecyclerView) findViewById(R.id.our_promise_list),ourPromise);
        setUpKeyPointsList((RecyclerView) findViewById(R.id.steps_list),steps);

        datePickerButton = findViewById(R.id.select_date);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog.show();

            }
        });

        selectTime = findViewById(R.id.select_time);
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiemPicker();
            }
        });

        checkAvailability = findViewById(R.id.next);

        checkAvailability.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(panditSelected){



                }
                else if(selectedDate!=null){

                    ApiUtil.getInstance().getPandit("Noida", selectedDate, pujaDetailModel.getTitle(), new Callback<AvailabilityModel>() {
                        @Override
                        public void onResponse(Call<AvailabilityModel> call, Response<AvailabilityModel> response) {
                            AvailabilityModel availablePandit = response.body();
                            findViewById(R.id.available_pandit_view).setVisibility(View.VISIBLE);
                            final TextView userName = findViewById(R.id.user_name);
                            final TextView userDetailEmail = findViewById(R.id.user_email);
                            final TextView userMobile = findViewById(R.id.user_mobile);
                            userName.setText(availablePandit.getName());
                            userDetailEmail.setText(availablePandit.getEmail());
                            userMobile.setText(availablePandit.getMobile());
                            panditSelected = true;
                            checkAvailability.setText("Book Now");
                        }

                        @Override
                        public void onFailure(Call<AvailabilityModel> call, Throwable t) {

                        }
                    });

                }


            }
        });

    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        selectedTime = getProperDigit(hourOfDay) + ":" + getProperDigit(minute);
                        selectTime.setText(selectedTime);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            selectedDate = getProperDigit(selectedDay) + " / " + getProperDigit(selectedMonth+1) + " / "
                    + selectedYear;
            datePickerButton.setText(selectedDate);
        }
    };

    private String getProperDigit(int selectedMonth) {
        return (((selectedMonth) < 10) ? ("0"+(selectedMonth)) : ""+(selectedMonth));
    }

    private void setUpKeyPointsList(RecyclerView recyclerView , List<String> keyPoints) {
        StringAdapter adapter = new StringAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapter);
        adapter.setData(keyPoints);
    }
}
