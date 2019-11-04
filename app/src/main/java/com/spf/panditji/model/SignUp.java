package com.spf.panditji.model;

public class SignUp {


    /**
     * alert : otp send on email and mobile
     * success : 1
     */

    private String alert;
    private int success;

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
