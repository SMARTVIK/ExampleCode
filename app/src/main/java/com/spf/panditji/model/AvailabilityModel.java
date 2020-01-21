package com.spf.panditji.model;

public class AvailabilityModel {


    /**
     * pandit_id : 4
     * name : Anuj Mishra
     * email : sdk3@gmail.com
     * mobile : 65656565656
     */

    private String pandit_id;
    private String name;
    private String email;
    private String mobile;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public String getPandit_id() {
        return pandit_id;
    }

    public void setPandit_id(String pandit_id) {
        this.pandit_id = pandit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
