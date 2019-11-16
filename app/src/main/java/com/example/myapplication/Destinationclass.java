package com.example.myapplication;

public class Destinationclass {
    public float Lat;
    public float Log;
    public String Name;
    public String AddressLocation;
    public String Discount;
    public String Type;
    public String ActiveTime;
    public String AccountPost;

    public Destinationclass() {
    }

    public Destinationclass(float lat, float log, String name, String addressLocation, String discount, String type, String activeTime, String accountPost) {
        Lat = lat;
        Log = log;
        Name = name;
        AddressLocation = addressLocation;
        Discount = discount;
        Type = type;
        ActiveTime = activeTime;
        this.AccountPost = accountPost;
    }

    public Destinationclass(String name, String addressLocation, String discount, String activeTime) {
        Name = name;
        AddressLocation = addressLocation;
        Discount = discount;
        ActiveTime = activeTime;
    }

}
