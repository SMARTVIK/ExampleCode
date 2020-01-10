package com.spf.panditji.view;

import android.os.Bundle;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spf.panditji.R;
import com.spf.panditji.model.PanditDetailsModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.util.ApiUtil;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanditProfile extends AppCompatActivity {

    private TextView reviewsPandit;
    private TextView pujaPandit;
    private PopularPanditModel panditModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandit_profile2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pandit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        panditModel = getIntent().getParcelableExtra("pandit_model");

        initViews();

        pujaPandit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pujaPandit.setTextColor(ContextCompat.getColor(PanditProfile.this,R.color.white));
                pujaPandit.setBackground(ContextCompat.getDrawable(PanditProfile.this,R.drawable.toolbar_gradient));
                reviewsPandit.setTextColor(ContextCompat.getColor(PanditProfile.this,R.color.darkBlack));
                reviewsPandit.setBackground(ContextCompat.getDrawable(PanditProfile.this,R.drawable.white));
                setUpPujaList();
            }
        });

        reviewsPandit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pujaPandit.setTextColor(ContextCompat.getColor(PanditProfile.this,R.color.darkBlack));
                pujaPandit.setBackground(ContextCompat.getDrawable(PanditProfile.this,R.drawable.white));
                reviewsPandit.setTextColor(ContextCompat.getColor(PanditProfile.this,R.color.white));
                reviewsPandit.setBackground(ContextCompat.getDrawable(PanditProfile.this,R.drawable.toolbar_gradient));
                setUpReviewsList();
            }
        });

    }

    private void initViews() {
        reviewsPandit = findViewById(R.id.reviews_pandit);
        pujaPandit = findViewById(R.id.puja_pandit);
        getPanditDetails();
        TextView name = findViewById(R.id.name);
        CircleImageView imageView = findViewById(R.id.image);
        name.setText(panditModel.getName());
        String baseUrl = "https://vaidiksewa.in/pandit_img/";
        Glide.with(imageView.getContext())
                .load(baseUrl+panditModel.getImg())
                .into(imageView);
    }

    private void getPanditDetails() {
        ApiUtil.getInstance().getPanditDetails(panditModel.getId(), new Callback<PanditDetailsModel>() {

            @Override
            public void onResponse(Call<PanditDetailsModel> call, Response<PanditDetailsModel> response) {

                PanditDetailsModel panditDetailsModel = response.body();
                final TextView userEducation = findViewById(R.id.user_education);
                final TextView userCity = findViewById(R.id.user_city);
                final TextView userVerification = findViewById(R.id.user_verification);
                final TextView userJoined = findViewById(R.id.user_joined);


                userEducation.setText(panditDetailsModel.getEducation());
                userCity.setText(panditDetailsModel.getAvalble_in());
                userVerification.setText(panditDetailsModel.getVerify().equals("0")? "Not Verified" : "Verified");
                userJoined.setText(panditDetailsModel.getJoin());

            }

            @Override
            public void onFailure(Call<PanditDetailsModel> call, Throwable t) {

            }
        });
    }

    private void setUpReviewsList() {
        ApiUtil.getInstance().getReviewsList();
    }

    private void setUpPujaList() {
        ApiUtil.getInstance().getPujaList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
