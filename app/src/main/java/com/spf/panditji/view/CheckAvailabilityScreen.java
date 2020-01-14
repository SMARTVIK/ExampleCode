package com.spf.panditji.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.BookingModel;
import com.spf.panditji.model.OrderModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.util.ApiUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAvailabilityScreen extends AppCompatActivity {

    private static final int SIGN_IN = 100;
    private static final int SIGN_UP = 101;
    private static final int SELECT_ADDRESS = 102;
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
    private AvailabilityModel availablePandit;
    private UserProfileModel userProfileModel;
    private int categoryId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availability_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Details");

        final Calendar c = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(CheckAvailabilityScreen.this, datePickerListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(c.getTime().getTime());

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

                    if(!ApplicationDataController.getInstance().isUserSignedUp()){
                        SignUpUser();
                        return;
                    }

                    if (ApplicationDataController.getInstance().isUserLoggedIn()) {
                        createOrder();
                        return;
                    }

                    startActivityForResult(new Intent(CheckAvailabilityScreen.this,SignInActivity.class),SIGN_IN);
                }
                else if(selectedDate!=null && selectedTime!=null){

                    ApiUtil.getInstance().getPandit("Noida", selectedDate, pujaDetailModel.getTitle(), new Callback<AvailabilityModel>() {
                        @Override
                        public void onResponse(Call<AvailabilityModel> call, Response<AvailabilityModel> response) {
                            availablePandit = response.body();
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


        findViewById(R.id.select_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CheckAvailabilityScreen.this,SelectAddressScreen.class),SELECT_ADDRESS);
            }
        });

    }

    private void SignUpUser() {
        startActivityForResult(new Intent(CheckAvailabilityScreen.this,SignUpActivity.class).putExtra("show_signin",false),SIGN_UP);
    }

    private void createOrder() {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setBooking_id("");
        bookingModel.setCat(categoryId);
        bookingModel.setEmail("abc@gmail.com");
        bookingModel.setCity("New Delhi");
        bookingModel.setLandmark("Swaraj Colony");
        bookingModel.setMobile("9999887766");
        bookingModel.setState("Delhi");
        bookingModel.setAddress("b10");
        bookingModel.setPandit(availablePandit.getName());
        bookingModel.setPandit_email(availablePandit.getEmail());
        bookingModel.setPandit_mobile(availablePandit.getMobile());
        bookingModel.setPooja(pujaDetailModel.getTitle());
        bookingModel.setPrice(pujaDetailModel.getPrice());
        bookingModel.setPin("110030");
        bookingModel.setPooja_date(selectedDate);
        bookingModel.setPooja_time(selectedTime);
        bookingModel.setUser_name("WWxjNWFreHRlSEJaVnpGdVVVZDBhMk4zUFQwPQ");

        Gson gson = new Gson();
        String json = gson.toJson(bookingModel);

        ApiUtil.getInstance().booking(json, new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {

                Toast.makeText(CheckAvailabilityScreen.this, "booking done with booking id "+response.body().get(0).getBooking_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckAvailabilityScreen.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {

                Log.d("onFailure",t.getMessage());

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_UP && resultCode == Activity.RESULT_OK){

            if(ApplicationDataController.getInstance().getCurrentUserProfile()==null){

                getUserProfile(data.getStringExtra("user_id"));

            }

        }


    }

    private void getUserProfile(String user_id) {

        ApiUtil.getInstance().getUserProfile(user_id, new Callback<List<UserProfileModel>>() {

            @Override
            public void onResponse(Call<List<UserProfileModel>> call, Response<List<UserProfileModel>> response) {

                if (response.code() == 200) {
                    ApplicationDataController.getInstance().setCurrentUserProfile(response.body().get(0));
                }

                findViewById(R.id.select_address).setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<List<UserProfileModel>> call, Throwable t) {

            }
        });

    }
}
