package com.spf.panditji.model;

public class BookingListModel {


    /**
     * booking_id : VS-07
     * pooja_name : Ganesh Puja
     * cat : Pujas
     * pooja_date : 25 / 01 / 2020
     * by : Anuj Mishra
     * amount : 10000
     */

    private String booking_id;
    private String pooja_name;
    private String cat;
    private String pooja_date;
    private String by;
    private String amount;
    private String img;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPooja_name() {
        return pooja_name;
    }

    public void setPooja_name(String pooja_name) {
        this.pooja_name = pooja_name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPooja_date() {
        return pooja_date;
    }

    public void setPooja_date(String pooja_date) {
        this.pooja_date = pooja_date;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
