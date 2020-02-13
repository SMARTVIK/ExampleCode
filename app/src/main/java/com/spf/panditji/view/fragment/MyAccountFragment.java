package com.spf.panditji.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.view.SelectAddressScreen;
import com.spf.panditji.view.SignInActivity;
import com.spf.panditji.view.SignUpActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pandit_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      /*  if (ApplicationDataController.getInstance().getCurrentUserProfile() != null) {
            view.findViewById(R.id.user_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.go_to_sign_in).setVisibility(View.GONE);
            setUserProfile(view, ApplicationDataController.getInstance().getCurrentUserProfile());
            return;
        }*/

        if (ApplicationDataController.getInstance().getUserId() != null) {
            getUserProfile(view);
        }else{
            view.findViewById(R.id.user_layout).setVisibility(View.GONE);
            view.findViewById(R.id.go_to_sign_in).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.go_to_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ApplicationDataController.getInstance().isUserLoggedIn()) {
                    startActivity(new Intent(getContext(), SignInActivity.class).putExtra(Constants.OPEN_BOOKING,true));
                    return;
                }
            }
        });

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SelectAddressScreen.class).putExtra(Constants.HIDE_DONE,true));
            }
        });
    }

    private void setUserProfile(View view, UserProfileModel userProfileModel) {

        final TextView name = view.findViewById(R.id.name);
        final TextView userEmail = view.findViewById(R.id.text_email);
        final TextView userName = view.findViewById(R.id.user_name);
        final TextView userDetailEmail = view.findViewById(R.id.user_email);
        final TextView userMobile = view.findViewById(R.id.user_mobile);
        final TextView userAddress = view.findViewById(R.id.user_address);


        userName.setText(userProfileModel.getName());
        name.setText(userProfileModel.getName());
        userEmail.setText(userProfileModel.getEmail());
        userDetailEmail.setText(userProfileModel.getEmail());
        userMobile.setText(userProfileModel.getMobile());
        userAddress.setText(userProfileModel.getAddress());
    }

    void getUserProfile(final View view) {

        String userId = ApplicationDataController.getInstance().getUserId();

        ApiUtil.getInstance().getUserProfile(userId, new Callback<List<UserProfileModel>>() {

            @Override
            public void onResponse(Call<List<UserProfileModel>> call, Response<List<UserProfileModel>> response) {
                List<UserProfileModel> userProfileModels = response.body();
                UserProfileModel userProfileModel = userProfileModels.get(0);
                ApplicationDataController.getInstance().setCurrentUserProfile(userProfileModel);
                setUserProfile(view,userProfileModel);
            }

            @Override
            public void onFailure(Call<List<UserProfileModel>> call, Throwable t) {

                Log.d("onFailure",t.getMessage());

            }
        });
    }

}
