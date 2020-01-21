package com.spf.panditji.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.AddressModel;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressScreen extends AppCompatActivity {

    private List<AddressModel> addresses = new ArrayList<>();
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Select Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpListView();
        getAllAddresses(ApplicationDataController.getInstance().getUserId());
    }

    private void setUpListView() {
        RecyclerView recyclerView = findViewById(R.id.addresses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        addressAdapter = new AddressAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.setData(addresses);
    }

    public void openDialogToAdd() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_address_layout, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        final EditText name = dialogView.findViewById(R.id.name_edit_text);
        final EditText address = dialogView.findViewById(R.id.address_edit_text);
        final EditText city = dialogView.findViewById(R.id.city_edit_text);
        final EditText state = dialogView.findViewById(R.id.state_edit_text);
        final EditText landmark = dialogView.findViewById(R.id.landmark_edit_text);
        final EditText pincode = dialogView.findViewById(R.id.pincode_edit_text);

        dialogView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().isEmpty()) {
                    name.setError("Can not be empty");
                    name.requestFocus();
                    return;
                } else if (address.getText().toString().isEmpty()) {
                    address.setError("Can not be empty");
                    address.requestFocus();
                    return;
                } else if (city.getText().toString().isEmpty()) {
                    city.setError("Can not be empty");
                    city.requestFocus();
                    return;
                } else if (state.getText().toString().isEmpty()) {
                    state.setError("Can not be empty");
                    state.requestFocus();
                    return;
                } else if (landmark.getText().toString().isEmpty()) {
                    landmark.setError("Can not be empty");
                    landmark.requestFocus();
                    return;
                } else if (pincode.getText().toString().isEmpty()) {
                    pincode.setError("Can not be empty");
                    pincode.requestFocus();
                    return;
                }else if(pincode.getText().toString().length()<6){
                    pincode.setError("invalid pin code");
                    pincode.requestFocus();
                    return;
                }

                ApiUtil.getInstance().addAddress(name.getText().toString().trim(),ApplicationDataController.getInstance().getUserId(),

                        address.getText().toString(),
                        city.getText().toString().trim(),
                        state.getText().toString().trim(),
                        landmark.getText().toString().trim(),
                        pincode.getText().toString(), new Callback<AddressResponse>() {
                            @Override
                            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                                getAllAddresses(ApplicationDataController.getInstance().getUserId());
                                alertDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<AddressResponse> call, Throwable t) {
                                alertDialog.dismiss();
                            }
                        });
            }
        });


    }

    private void getAllAddresses(String userId) {

        if(userId == null){
            L.d("getAllAddresses : userid was null");
            return;
        }

        ApiUtil.getInstance().getAllAddresses(userId, new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {

                addresses = response.body();

                addressAdapter.setData(addresses);

            }

            @Override
            public void onFailure(Call<List<AddressModel>> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
            }
        });

    }

    public void setSelectedAddress(AddressModel addressModel) {
        setResult(Activity.RESULT_OK,new Intent().putExtra(Constants.SELECTED_ADDRESS,addressModel));
        finish();
    }
}
