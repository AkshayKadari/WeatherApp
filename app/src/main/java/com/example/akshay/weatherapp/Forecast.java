package com.example.akshay.weatherapp;

import java.util.ArrayList;

/**
 * Created by akshay on 4/6/2017.
 */

public class Forecast {
    String headlinetext;
    String mobilelinkurl;
    ArrayList<DailyForecast> dailyForecastArrayList;

    public String getMobilelinkurl() {
        return mobilelinkurl;
    }

    public void setMobilelinkurl(String mobilelinkurl) {
        this.mobilelinkurl = mobilelinkurl;
    }

    public String getHeadlinetext() {
        return headlinetext;
    }

    public void setHeadlinetext(String headlinetext) {
        this.headlinetext = headlinetext;
    }

    public ArrayList<DailyForecast> getDailyForecastArrayList() {
        return dailyForecastArrayList;
    }

    public void setDailyForecastArrayList(ArrayList<DailyForecast> dailyForecastArrayList) {
        this.dailyForecastArrayList = dailyForecastArrayList;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "headlinetext='" + headlinetext + '\'' +
                ", mobilelinkurl='" + mobilelinkurl + '\'' +
                ", dailyForecastArrayList=" + dailyForecastArrayList +
                '}';
    }
}