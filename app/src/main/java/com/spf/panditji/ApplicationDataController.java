package com.spf.panditji;

import android.content.SharedPreferences;
import android.location.Location;

import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;
import com.spf.panditji.view.VadikSewaApplication;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplicationDataController {
    private static final ApplicationDataController ourInstance = new ApplicationDataController();
    private SignInResponse currentUserResponse;
    private UserProfileModel currentUserProfile;
    private String userId;
    private List<CategoryModel> categoriesList;

    public void setUserSignUp(boolean userSignUp) {
        isUserSignUp = userSignUp;
    }

    private boolean isUserSignUp;

    public void setUserLoggedIn(boolean userLoggedIn) {
        SharedPreferences sharedPreferences = VadikSewaApplication.getInstance().getSharedPrefs();
        isUserLoggedIn = userLoggedIn;
        sharedPreferences.edit().putBoolean(Constants.USER_LOGGED_IN, userLoggedIn).commit();
    }

    private boolean isUserLoggedIn;

    public Location getLocation() {
        return location;
    }

    private Location location;

    public static ApplicationDataController getInstance() {
        return ourInstance;
    }

    private ApplicationDataController() {
    }


    public void setLastKnownLocation(@NotNull Location location) {
        this.location = location;
        SharedPreferences sharedPreferences  = VadikSewaApplication.getInstance().getSharedPrefs();
        sharedPreferences.edit().putString("lat",location.getLatitude()+"");
        sharedPreferences.edit().putString("long",location.getLongitude()+"");
    }

    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences  = VadikSewaApplication.getInstance().getSharedPrefs();
        isUserLoggedIn = sharedPreferences.getBoolean(Constants.USER_LOGGED_IN,false);
        return isUserLoggedIn;
    }

    public boolean isUserSignedUp() {
        SharedPreferences sharedPreferences  = VadikSewaApplication.getInstance().getSharedPrefs();
        isUserSignUp = sharedPreferences.getBoolean(Constants.USER_LOGGED_IN,false);
        return isUserSignUp;
    }

    public void setCurrentUserResponse(SignInResponse currentUserResponse) {
        this.currentUserResponse = currentUserResponse;
    }

    public SignInResponse getCurrentUserResponse() {
        return currentUserResponse;
    }

    public UserProfileModel getCurrentUserProfile() {
        return currentUserProfile;
    }

    public void setCurrentUserProfile(UserProfileModel currentUserProfile) {
        this.currentUserProfile = currentUserProfile;
        L.d("Setting user profile");
    }

    public String getUserId() {
        return VadikSewaApplication.getInstance().getSharedPrefs().getString(Constants.USER_ID,null);
    }

    public void setUserId(String userId) {
        this.userId = userId;
        VadikSewaApplication.getInstance().getSharedPrefs().edit().putString(Constants.USER_ID,userId).commit();
    }

    public void setCategoriesList(List<CategoryModel> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<CategoryModel> getCategoriesList() {
        return categoriesList;
    }
}
