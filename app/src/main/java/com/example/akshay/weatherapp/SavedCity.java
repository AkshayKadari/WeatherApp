package com.example.akshay.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akshay on 4/7/2017.
 */


public class SavedCity  {
    String citykey,cityname, country, temperature,id, time;
    Boolean fav;

    public String getTime() {

        CurrentCity cc = new CurrentCity();
        time = cc.getLocalobvtime();


        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    @Override

    public String toString() {
        return "FirebaseDb{" +
                "citykey='" + citykey + '\'' +
                ", cityname='" + cityname + '\'' +
                ", country='" + country + '\'' +
                ", temperature='" + temperature + '\'' +
                ", fav=" + fav +
                '}';
    }


    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

}
