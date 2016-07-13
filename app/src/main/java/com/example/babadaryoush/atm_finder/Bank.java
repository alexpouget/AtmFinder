package com.example.babadaryoush.atm_finder;

/**
 * Created by Baba Daryoush on 27/06/2016.
 */

public class Bank {
    String name;
    float longitude;
    float latitude;

    public Bank (String name, float longitude, float latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
