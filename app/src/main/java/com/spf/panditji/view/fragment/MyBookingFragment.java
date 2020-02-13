package com.spf.panditji.view.fragment;


import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.BookingListModel;
import com.spf.panditji.model.BookingModel;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.view.BookingAdapter;
import com.spf.panditji.view.CategoryListActivity;
import com.spf.panditji.view.DetailScreen;
import com.spf.panditji.view.RoundImageAdapter;
import com.spf.panditji.view.SignInActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingFragment extends Fragment {

    private ProgressDialog progressDialog;
    private BookingAdapter bookingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
        if(!ApplicationDataController.getInstance().isUserLoggedIn()){
            startActivity(new Intent(getContext(), SignInActivity.class));
        }else{
            getUsersBooking();
        }

    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.booking);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        bookingAdapter = new BookingAdapter(new OnItemClick<BookingListModel>() {
            @Override
            public void onClick(BookingListModel bookingListModel) {
//                startActivity(new Intent(getContext(), DetailScreen.class).putExtra("id",bookingListModel.getId()));
            }
        });
        recyclerView.setAdapter(bookingAdapter);
    }

    private void getUsersBooking() {

        showProgressDialog();

        ApiUtil.getInstance().getBookings(ApplicationDataController.getInstance().getUserId(),new Callback<List<BookingListModel>>() {

            @Override
            public void onResponse(Call<List<BookingListModel>> call, Response<List<BookingListModel>> response) {

                hideLoader();

                bookingAdapter.setData(response.body());

            }

            @Override
            public void onFailure(Call<List<BookingListModel>> call, Throwable t) {

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
            progressDialog = ProgressDialog.show(getContext(), "", "Please Wait...", true);
        } else {
            progressDialog.show();
        }
    }

}
