package com.spf.panditji;

import android.location.Location;

import org.jetbrains.annotations.NotNull;

public class ApplicationDataController {
    private static final ApplicationDataController ourInstance = new ApplicationDataController();

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
}
