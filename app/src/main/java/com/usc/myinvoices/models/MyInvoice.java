package com.usc.myinvoices.models;

import java.util.UUID;

public class MyInvoice {

    private UUID mId;

    private String date;
    private String type;
    private String title;
    private String comment;
    private String location;
    private String shop_name;

    public MyInvoice() {
        mId = UUID.randomUUID();
    }

    public MyInvoice(UUID id) {
        mId = id;
    }

    public MyInvoice(String title, String date, String destination, String comment, String type) {
        this(UUID.randomUUID());
        this.type = type;
        this.date = date;
        this.title = title;
        this.comment = comment;
        this.shop_name = destination;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShopName() {
        return shop_name;
    }

    public void setShopName(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
