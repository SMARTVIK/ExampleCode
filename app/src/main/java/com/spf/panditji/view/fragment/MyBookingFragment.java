package com.spf.panditji.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.BookingListModel;
import com.spf.panditji.model.BookingModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.view.SignInActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!ApplicationDataController.getInstance().isUserLoggedIn()){
            startActivity(new Intent(getContext(), SignInActivity.class));
        }else{
            getUsersBooking();
        }

    }

    private void getUsersBooking() {

        showLoader();

        ApiUtil.getInstance().getBookings(ApplicationDataController.getInstance().getUserId(),new Callback<List<BookingListModel>>() {

            @Override
            public void onResponse(Call<List<BookingListModel>> call, Response<List<BookingListModel>> response) {

                hideLoader();

            }

            @Override
            public void onFailure(Call<List<BookingListModel>> call, Throwable t) {

                hideLoader();

            }
        });


    }

    private void hideLoader() {


    }

    private void showLoader() {


    }


}
