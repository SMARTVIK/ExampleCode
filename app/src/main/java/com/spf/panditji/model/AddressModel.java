package com.spf.panditji.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressModel implements Parcelable {


    /**
     * address_id : 1
     * name : Dheeraj Singh
     * address : A-45
     * state : Delhi
     * city : Delhi
     * pin : 110030
     * landmark : Chhatarpur Metro station
     */

    private String address_id;
    private String name;
    private String address;
    private String state;
    private String city;
    private String pin;
    private String landmark;

    protected AddressModel(Parcel in) {
        address_id = in.readString();
        name = in.readString();
        address = in.readString();
        state = in.readString();
        city = in.readString();
        pin = in.readString();
        landmark = in.readString();
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address_id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(state);
        parcel.writeString(city);
        parcel.writeString(pin);
        parcel.writeString(landmark);
    }
}
