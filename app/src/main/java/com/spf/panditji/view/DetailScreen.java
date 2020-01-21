package com.spf.panditji.view;

import android.Manifest;
import android.app.ProgressDialog;
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
    ProgressDialog progressDialog;

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
        nextButton.setEnabled(false);

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(DetailScreen.this,CheckAvailabilityScreen.class).putExtra("pooja_model",pujaDetailModel));

            }
        });
    }

    private void getPujaDetailsFromServer(String id) {

        showProgressDialog();

        ApiUtil.getInstance().getPujaDetails(id, new Callback<List<PujaDetailModel>>() {
            @Override
            public void onResponse(Call<List<PujaDetailModel>> call, Response<List<PujaDetailModel>> response) {
                hideLoader();
                nextButton.setEnabled(true);
                pujaDetailModel = response.body().get(0);
                getSupportActionBar().setTitle(pujaDetailModel.getTitle());
//                name.setText(pujaDetailModel.getTitle());
                name.setVisibility(View.GONE);
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
                hideLoader();
            }
        });
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "Please Wait...", true);
        } else {
            progressDialog.show();
        }
    }
}
