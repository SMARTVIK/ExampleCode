package com.spf.panditji.view.fragment;


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

        ImageView image = view.findViewById(R.id.image);
        final TextView name = view.findViewById(R.id.name);
        final TextView userEmail = view.findViewById(R.id.text_email);

        final TextView userName = view.findViewById(R.id.user_name);
        final TextView userDetailEmail = view.findViewById(R.id.user_email);
        final TextView userMobile = view.findViewById(R.id.user_mobile);
        final TextView userAddress = view.findViewById(R.id.user_address);

        if (ApplicationDataController.getInstance().getCurrentUserProfile() != null) {
            setUserProfile(name, userEmail, userName, userDetailEmail, userMobile, userAddress, ApplicationDataController.getInstance().getCurrentUserProfile());
            return;
        }
        getUserProfile(name, userEmail, userName, userDetailEmail, userMobile, userAddress);
    }

    private void setUserProfile(TextView name, TextView userEmail, TextView userName, TextView userDetailEmail, TextView userMobile, TextView userAddress, UserProfileModel userProfileModel) {
        userName.setText(userProfileModel.getName());
        name.setText(userProfileModel.getName());
        userEmail.setText(userProfileModel.getEmail());
        userDetailEmail.setText(userProfileModel.getEmail());
        userMobile.setText(userProfileModel.getMobile());
        userAddress.setText(userProfileModel.getAddress());
    }

    void getUserProfile(final TextView name, final TextView userEmail, final TextView userName, final TextView userDetailEmail, final TextView userMobile, final TextView userAddress) {

        String userId = "WWxjNWFreHRlSEJaVnpGdVVVZDBhMk4zUFQwPQ==";

        ApiUtil.getInstance().getUserProfile(userId, new Callback<List<UserProfileModel>>() {

            @Override
            public void onResponse(Call<List<UserProfileModel>> call, Response<List<UserProfileModel>> response) {
                List<UserProfileModel> userProfileModels = response.body();
                UserProfileModel userProfileModel = userProfileModels.get(0);
                ApplicationDataController.getInstance().setCurrentUserProfile(userProfileModel);
                userName.setText(userProfileModel.getName());
                name.setText(userProfileModel.getName());
                userEmail.setText(userProfileModel.getEmail());
                userDetailEmail.setText(userProfileModel.getEmail());
                userMobile.setText(userProfileModel.getMobile());
                userAddress.setText(userProfileModel.getAddress());
            }

            @Override
            public void onFailure(Call<List<UserProfileModel>> call, Throwable t) {

                Log.d("onFailure",t.getMessage());

            }
        });
    }

}
