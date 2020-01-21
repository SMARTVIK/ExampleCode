package com.spf.panditji.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PujaDetailModel implements Parcelable {


    /**
     * id : 3
     * title : Ganesh Puja
     * price : 10000
     * about : Ganesh Puja is performed for Lord Ganapathi who removes all the obstacles and negative energies. This puja bestows one with victory brings harmony in family and helps one succeed in life.
     * description : Lord Ganesha, the son of Lord Shiva and Goddess Parvati is one of the most revered Gods. Ganesh Puja is usually conducted before any other havans and pujas in order to avoid and overcome any obstacles during the prayers.

     When a person is starting any auspicious events like new business investments house-warming, getting married to reduce the malefic effects of Ketu or on their respective birthdays Ganapathi puja is recommended.

     Ganesh Puja is performed by invoking Lord Ganapathi followed by the chanting of Ganapathi Mantra and then performing the Ganapathi puja to receive his blessings.

     When to Perform Ganesh Puja?
     Ganapathi Puja is performed during the Brahma Muhurth and during all other auspicious times. The date can be fixed according to ones nakshatra and the yoga, nakshatra and thithi of that particular date.

     Benefits of Ganesh Puja:
     Bestows one with intelligence and wisdom.
     Removes and helps to overcome obstacles and hurdles one may face in life.
     Ensures good fortune harmony, and prosperity in ones family.
     Book Pandit for Ganesh Puja & Havan. All the Puja Samagri will be brought by Panditji. All the Pandits are well experienced and studied from Vedic Pathshala.

     Note: To View Ganesh Puja Cost, kindly click on the View Pricing and Packages button.
     * key_point : To retain health, wealth and prosperity.##
     Ganesh is worshiped before doing any auspicious activity.##
     Deity Worshiped: Lord Ganesh.##
     Ashta Dravya, Durva/Doob grass are the key ingredients used.##
     * our_pact : Vedic Patashala certified and experienced priests.##
     All rituals follow Vedic Standards and Procedures.##
     High quality Samagri to ensure a pleasant Puja experience.##
     Guaranteed Punctuality and Authenticity.##
     Professional Guidance & Support.##
     * step : Gowri-Ganesh Puja.## Maha Sankalp.## Patrika Puja.## Ashirwad.## Prasad distribution.##
     * star : 3.8
     * img : Ganesh-Puja.jpg
     */

    private String id;
    private String title;
    private String price;
    private String about;
    private String description;
    private String key_point;
    private String our_pact;
    private String step;
    private String star;
    private String img;

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    private String cat;


    protected PujaDetailModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        about = in.readString();
        description = in.readString();
        key_point = in.readString();
        our_pact = in.readString();
        step = in.readString();
        star = in.readString();
        img = in.readString();
        cat = in.readString();
    }

    public static final Creator<PujaDetailModel> CREATOR = new Creator<PujaDetailModel>() {
        @Override
        public PujaDetailModel createFromParcel(Parcel in) {
            return new PujaDetailModel(in);
        }

        @Override
        public PujaDetailModel[] newArray(int size) {
            return new PujaDetailModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey_point() {
        return key_point;
    }

    public void setKey_point(String key_point) {
        this.key_point = key_point;
    }

    public String getOur_pact() {
        return our_pact;
    }

    public void setOur_pact(String our_pact) {
        this.our_pact = our_pact;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
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
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(about);
        parcel.writeString(description);
        parcel.writeString(key_point);
        parcel.writeString(our_pact);
        parcel.writeString(step);
        parcel.writeString(star);
        parcel.writeString(img);
        parcel.writeString(cat);
    }
}
