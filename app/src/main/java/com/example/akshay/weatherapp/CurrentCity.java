package com.example.akshay.weatherapp;

/**
 * Created by akshay on 4/6/2017.
 */


public class CurrentCity {
    String localobvtime, weathertext,weathericon,metric;

    public String getLocalobvtime() {
        return localobvtime;
    }

    public void setLocalobvtime(String localobvtime) {
        this.localobvtime = localobvtime;
    }

    public String getWeathertext() {
        return weathertext;
    }

    public void setWeathertext(String weathertext) {
        this.weathertext = weathertext;
    }

    public String getWeathericon() {
        return weathericon;
    }

    public void setWeathericon(String weathericon) {
        this.weathericon = weathericon;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "CurrentCity{" +
                "localobvtime='" + localobvtime + '\'' +
                ", weathertext='" + weathertext + '\'' +
                ", weathericon='" + weathericon + '\'' +
                ", metric='" + metric + '\'' +
                '}';
    }
}
