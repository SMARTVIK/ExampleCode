package com.spf.panditji.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryModel implements Parcelable {


    /**
     * cat : Bhajans
     * img : bhajan.jpg
     */

    private String cat;
    private String img;

    protected CategoryModel(Parcel in) {
        cat = in.readString();
        img = in.readString();
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
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
        parcel.writeString(cat);
        parcel.writeString(img);
    }
}
