package com.example.akshay.weatherapp;

/**
 * Created by akshay on 4/6/2017.
 */

public class DailyForecast {
    String date, mintemp, maxtemp, dayiconid, nighticonid, dayiconphrase, nighticonphrase, dailymobilelink;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DailyForecast{" +
                "date='" + date + '\'' +
                ", mintemp='" + mintemp + '\'' +
                ", maxtemp='" + maxtemp + '\'' +
                ", dayiconid='" + dayiconid + '\'' +
                ", nighticonid='" + nighticonid + '\'' +
                ", dayiconphrase='" + dayiconphrase + '\'' +
                ", nighticonphrase='" + nighticonphrase + '\'' +
                ", dailymobilelink='" + dailymobilelink + '\'' +
                '}';
    }

    public String getMintemp() {
        return mintemp;
    }

    public void setMintemp(String mintemp) {
        this.mintemp = mintemp;
    }

    public String getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getDayiconid() {
        return dayiconid;
    }

    public void setDayiconid(String dayiconid) {
        this.dayiconid = dayiconid;
    }

    public String getNighticonid() {
        return nighticonid;
    }

    public void setNighticonid(String nighticonid) {
        this.nighticonid = nighticonid;
    }

    public String getDayiconphrase() {
        return dayiconphrase;
    }

    public void setDayiconphrase(String dayiconphrase) {
        this.dayiconphrase = dayiconphrase;
    }

    public String getNighticonphrase() {
        return nighticonphrase;
    }

    public void setNighticonphrase(String nighticonphrase) {
        this.nighticonphrase = nighticonphrase;
    }

    public String getDailymobilelink() {
        return dailymobilelink;
    }

    public void setDailymobilelink(String dailymobilelink) {
        this.dailymobilelink = dailymobilelink;
    }
}
