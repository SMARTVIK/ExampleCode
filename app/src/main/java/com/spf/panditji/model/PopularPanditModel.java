package com.spf.panditji.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularPanditModel implements Parcelable {


    /**
     * id : 1
     * name : Dheeraj Singh
     * star : 4.5
     * img : sdk.jpg
     */

    private String id;
    private String name;
    private String star;
    private String img;

    protected PopularPanditModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        star = in.readString();
        img = in.readString();
    }

    public static final Creator<PopularPanditModel> CREATOR = new Creator<PopularPanditModel>() {
        @Override
        public PopularPanditModel createFromParcel(Parcel in) {
            return new PopularPanditModel(in);
        }

        @Override
        public PopularPanditModel[] newArray(int size) {
            return new PopularPanditModel[size];
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

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
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
        parcel.writeString(star);
        parcel.writeString(img);
    }
}
