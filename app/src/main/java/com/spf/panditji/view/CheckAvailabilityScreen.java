package com.spf.panditji.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.AddressModel;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.BookingModel;
import com.spf.panditji.model.OrderModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.model.SuccessModel;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAvailabilityScreen extends AppCompatActivity implements PaymentResultListener {

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
    private AddressModel selectedAddress;
    private String bookingId;
    private View addressLayout;
    private TextView address;
    private String selectedCity;
    private ProgressDialog progressDialog;
    private TextView addressButton;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availability_screen);
        Checkout.preload(this);
        userProfileModel = ApplicationDataController.getInstance().getCurrentUserProfile();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Details");

        addressButton = findViewById(R.id.select_address);
        disableAddressButton(false, R.drawable.toolbar_gradient_round_dis);
        addressLayout = findViewById(R.id.address_layout);

        address = findViewById(R.id.text);

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

        final List<String> list = new ArrayList<String>();

        list.add("Noida");
        list.add("Delhi");
        list.add("Kashi");
        list.add("Lucknow");
        list.add("Prayagraj");
        list.add("G. Noida");
        list.add("Gururam");
        list.add("Select");

        final Spinner spinner_1 = (Spinner) findViewById(R.id.spinner);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    selectedCity = list.get(i);

                    enableCheckButton();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.select_city_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner_1.performClick();

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
//                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);
        spinner_1.setSelection(adapter.getCount());

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
                if(selectedDate == null){
                    Toast.makeText(CheckAvailabilityScreen.this, "Select date first!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedTime == null){
                    Toast.makeText(CheckAvailabilityScreen.this, "Select time first!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedCity==null || (selectedCity!=null && selectedCity.equals("Select"))){
                    Toast.makeText(CheckAvailabilityScreen.this, "Select city first!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utility.checkInternetConnection(CheckAvailabilityScreen.this)){
                    Toast.makeText(CheckAvailabilityScreen.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(panditSelected){

                    if(selectedAddress == null){
                        if (!ApplicationDataController.getInstance().isUserLoggedIn()) {
                            SignUpUser();
                            return;
                        }
                        Toast.makeText(CheckAvailabilityScreen.this, "Select address first!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(userProfileModel == null){

                        ApiUtil.getInstance().getUserProfile(ApplicationDataController.getInstance().getUserId(), new Callback<List<UserProfileModel>>() {
                            @Override
                            public void onResponse(Call<List<UserProfileModel>> call, Response<List<UserProfileModel>> response) {

                                if(response.code() == 200){

                                    userProfileModel = response.body().get(0);

                                    createOrder();

                                }
                            }

                            @Override
                            public void onFailure(Call<List<UserProfileModel>> call, Throwable t) {

                            }
                        });

                    } else {
                        createOrder();
                    }

                }
                else if (selectedDate != null && selectedTime != null && selectedCity != null){
                    String addressCity = selectedCity;
                    addressCity = addressCity.substring(0, 1).toUpperCase() + addressCity.substring(1);


                    if(!Utility.checkInternetConnection(CheckAvailabilityScreen.this)){
                        Toast.makeText(CheckAvailabilityScreen.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    showProgressDialog();

                    ApiUtil.getInstance().getPandit(addressCity, selectedDate, pujaDetailModel.getTitle(), new Callback<AvailabilityModel>() {
                        @Override
                        public void onResponse(Call<AvailabilityModel> call, Response<AvailabilityModel> response) {
                            hideLoader();
                            if (response.body().getError().equals("0")){
                                disableAddressButton(true, R.drawable.toolbar_gradient_round);
                                availablePandit = response.body();
                                findViewById(R.id.available_pandit_view).setVisibility(View.GONE);
                                final TextView userName = findViewById(R.id.user_name);
                                final TextView userDetailEmail = findViewById(R.id.user_email);
                                final TextView userMobile = findViewById(R.id.user_mobile);
                                userName.setText(availablePandit.getName());
                                userDetailEmail.setText(availablePandit.getEmail());
                                userMobile.setText(availablePandit.getMobile());
                                panditSelected = true;
                                checkAvailability.setText("Book Now");
                                checkAvailability.setBackgroundResource(R.drawable.check_availability);
                            }else{
                                Toast.makeText(CheckAvailabilityScreen.this, "No Purohit available", Toast.LENGTH_SHORT).show();
                                checkAvailability.setBackgroundResource(R.drawable.no_pandit);
                            }
                        }

                        @Override
                        public void onFailure(Call<AvailabilityModel> call, Throwable t) {

                            hideLoader();

                        }
                    });
                }
            }
        });


        addressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ApplicationDataController.getInstance().getUserId()==null){
                    SignUpUser();
                    return;
                }

                startActivityForResult(new Intent(CheckAvailabilityScreen.this, SelectAddressScreen.class), SELECT_ADDRESS);

            }
        });
    }

    void enableCheckButton() {
        panditSelected = false;

        availablePandit = null;

        checkAvailability.setText("Check Availability");

        checkAvailability.setBackgroundResource(R.drawable.green_round);

        selectedAddress = null;

        disableAddressButton(false,R.drawable.toolbar_gradient_round_dis);
    }

    void disableAddressButton(boolean b, int p) {
        findViewById(R.id.address_layout).setVisibility(View.GONE);
        addressButton.setText("Select Address");
        addressButton.setEnabled(b);
        addressButton.setBackgroundResource(p);
    }

    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = ProgressDialog.show(this,"","Please wait..",false);
        }else{
            progressDialog.show();
        }
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void SignUpUser() {
        startActivityForResult(new Intent(CheckAvailabilityScreen.this,SignInActivity.class).putExtra(Constants.OPEN_BOOKING,true),SIGN_UP);
    }

    private void createOrder() {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setBooking_id("");
        bookingModel.setCat(pujaDetailModel.getCat());
        bookingModel.setEmail(userProfileModel.getEmail());
        bookingModel.setCity(selectedAddress.getCity());
        bookingModel.setLandmark(selectedAddress.getLandmark());
        bookingModel.setMobile(userProfileModel.getMobile());
        bookingModel.setState(selectedAddress.getState());
        bookingModel.setAddress(selectedAddress.getAddress());
        bookingModel.setPandit(availablePandit.getName());
        bookingModel.setPandit_email(availablePandit.getEmail());
        bookingModel.setPandit_mobile(availablePandit.getMobile());
        bookingModel.setPooja(pujaDetailModel.getTitle());
        bookingModel.setPrice(pujaDetailModel.getPrice());
        bookingModel.setPin(selectedAddress.getPin());
        bookingModel.setPooja_date(selectedDate);
        bookingModel.setPooja_time(selectedTime);
        bookingModel.setUser_id(ApplicationDataController.getInstance().getUserId());
        bookingModel.setUser_name(userProfileModel.getName());
        bookingModel.setOther_price("0");
        bookingModel.setImg(pujaDetailModel.getImg());
        bookingModel.setId(pujaDetailModel.getId());

        Gson gson = new Gson();
        String json = gson.toJson(bookingModel);

        showProgressDialog();

        ApiUtil.getInstance().booking(json, new Callback<List<OrderModel>>() {

            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {


                if(response.code() == 200){

                    bookingId = response.body().get(0).getBooking_id();

                    Checkout checkout = new Checkout();

                    /**
                     * Set your logo here
                     */
                    checkout.setImage(R.mipmap.ic_launcher);

                    checkout.setKeyID(getString(R.string.api_key));

                    /**
                     * Reference to current activity
                     */
                    final Activity activity = CheckAvailabilityScreen.this;

                    /**
                     * Pass your payment options to the Razorpay Checkout as a JSONObject
                     */
                    try {
                        JSONObject options = new JSONObject();
                        /**
                         * Merchant Name
                         * eg: Rentomojo || HasGeek etc.
                         */
                        options.put("name", "Jet Hat");
                        /**
                         * Description can be anything
                         * eg: Order #123123
                         *     Invoice Payment
                         *     etc.
                         */
                        options.put("description", bookingId);
//                        options.put("order_id",bookingId);
//                        options.put("merchant_id","E1GIemq6tiF1r2");
                        options.put("currency", "INR");
                        options.put("amount", ""+pujaDetailModel.getPrice());
                        /**
                         * Amount is always passed in PAISE
                         * Eg: "500" = Rs 5.00
                         */
                        checkout.open(activity, options);
                    } catch (Exception e) {
                        L.d("Error in starting Razorpay Checkout "+e.getMessage());
                    }


                }
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

            if(selectedCity != null && selectedTime!=null && !selectedCity.equals("Select")){
                enableCheckButton();
            }

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

        if(requestCode == SELECT_ADDRESS && resultCode == Activity.RESULT_OK){

            findViewById(R.id.available_pandit_view).setVisibility(View.GONE);

            selectedAddress = data.getParcelableExtra(Constants.SELECTED_ADDRESS);

            addDataInAddressView();

        }
    }

    private void addDataInAddressView() {

        addressLayout.setVisibility(View.VISIBLE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                address.setText(selectedAddress.getName() + "\n" +
                        selectedAddress.getAddress() + "\n"
                        + selectedAddress.getCity() + "\n"
                        + selectedAddress.getState() + "\n"
                        + selectedAddress.getLandmark() + "\n"
                        + selectedAddress.getPin());

                addressButton.setText("Change Address");
            }
        });

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

    @Override
    public void onPaymentSuccess(String s) {

        L.d("status "+s);

        ApiUtil.getInstance().sendSuccess(bookingId,(s!=null ? "success":"failed"),new Callback<SuccessModel>() {

            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                if (response.body().getError()==0){
                    startActivity(new Intent(CheckAvailabilityScreen.this,HomeActivity.class).putExtra(Constants.OPEN_BOOKING,true).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

                L.d("onFailure..");

                hideLoader();

            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {

        hideLoader();

        L.d("error message "+s);

    }
}
