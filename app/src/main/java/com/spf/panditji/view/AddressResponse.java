package com.spf.panditji.view;

public class AddressResponse {


    /**
     * alert : added successfully
     * error : 0
     */

    private String alert;
    private int error;

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
