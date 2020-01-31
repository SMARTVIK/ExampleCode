package com.spf.panditji.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularPoojaModel implements Parcelable {


    /**
     * id : 1
     * name : Engagement Puja Sagai
     * price : 5000
     * img : pooja.png
     */

    private String id;
    private String name;
    private String price;
    private String img;

    protected PopularPoojaModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        img = in.readString();
    }

    public static final Creator<PopularPoojaModel> CREATOR = new Creator<PopularPoojaModel>() {
        @Override
        public PopularPoojaModel createFromParcel(Parcel in) {
            return new PopularPoojaModel(in);
        }

        @Override
        public PopularPoojaModel[] newArray(int size) {
            return new PopularPoojaModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(img);
    }
}
