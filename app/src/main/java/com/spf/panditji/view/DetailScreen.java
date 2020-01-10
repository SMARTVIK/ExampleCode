package com.spf.panditji.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.util.ApiUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailScreen extends AppCompatActivity {

    private static final int LOCATION_PERMISSION = 100;
    TextView name,amount,description,nextButton;
    private ImageView imageView;
    private PujaDetailModel pujaDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        String id = getIntent().getStringExtra("id");

        getPujaDetailsFromServer(id);

    }

    private FusedLocationProviderClient fusedLocationClient;

    private void initViews() {
        nextButton = findViewById(R.id.next);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.main_image);

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(DetailScreen.this,CheckAvailabilityScreen.class).putExtra("pooja_model",pujaDetailModel));

            }
        });
    }

    private void checkAvailability() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            } else {
                checkingPanditAvailability();
            }
        } else {
            checkingPanditAvailability();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkingPanditAvailability();
        }
    }

    private void checkingPanditAvailability() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            ApplicationDataController.getInstance().setLastKnownLocation(location);
                        }
                    }
                });

        String locationCity = "";
        String date = "";
        String poojaName = pujaDetailModel.getTitle();

        if (ApplicationDataController.getInstance().getLocation() != null) {

            Location location = ApplicationDataController.getInstance().getLocation();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses.size()>0){

            locationCity = addresses.get(0).getAddressLine(0);

            locationCity = locationCity.substring(0,1).toUpperCase() + locationCity.substring(1);

            ApiUtil.getInstance().getPandit(locationCity,date,poojaName, new Callback<AvailabilityModel>() {

                  @Override
                  public void onResponse(Call<AvailabilityModel> call, Response<AvailabilityModel> response) {




                  }

                  @Override
                  public void onFailure(Call<AvailabilityModel> call, Throwable t) {


                  }
              });

            }

        } else {

        }
    }

    private void getPujaDetailsFromServer(String id) {

        ApiUtil.getInstance().getPujaDetails(id, new Callback<List<PujaDetailModel>>() {
            @Override
            public void onResponse(Call<List<PujaDetailModel>> call, Response<List<PujaDetailModel>> response) {

                pujaDetailModel = response.body().get(0);

                name.setText(pujaDetailModel.getTitle());
                amount.setText("₹"+pujaDetailModel.getPrice());
                description.setText(pujaDetailModel.getDescription());
                String baseUrl = "https://vaidiksewa.in/img_big/";

                Glide.with(DetailScreen.this)
                        .load(baseUrl+pujaDetailModel.getImg())
                        .into(imageView);
            }

            @Override
            public void onFailure(Call<List<PujaDetailModel>> call, Throwable t) {
                Log.d("onFailaure" , t.getMessage());
            }
        });
    }
}
