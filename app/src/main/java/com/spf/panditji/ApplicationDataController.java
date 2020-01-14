package com.spf.panditji;

import android.location.Location;

import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.UserProfileModel;

import org.jetbrains.annotations.NotNull;

public class ApplicationDataController {
    private static final ApplicationDataController ourInstance = new ApplicationDataController();
    private SignInResponse currentUserResponse;
    private UserProfileModel currentUserProfile;
    private String userId;

    public void setUserSignUp(boolean userSignUp) {
        isUserSignUp = userSignUp;
    }

    private boolean isUserSignUp;

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
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
    }

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public boolean isUserSignedUp() {
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
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
