package com.example.akshay.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akshay on 4/6/2017.
 */
public class CurrentCityParser {
    static public class CityParser {
        static CurrentCity currentCity = new CurrentCity();

        static CurrentCity city(String in) throws JSONException {
            JSONArray jsonObject = new JSONArray(in);
            if (jsonObject.getJSONObject(0).getString("LocalObservationDateTime") != null)
                currentCity.setLocalobvtime(jsonObject.getJSONObject(0).getString("LocalObservationDateTime"));
            if (jsonObject.getJSONObject(0).getString("WeatherText") != null)
                currentCity.setWeathertext(jsonObject.getJSONObject(0).getString("WeatherText"));
            if (jsonObject.getJSONObject(0).getString("WeatherIcon") != null)
                currentCity.setWeathericon(jsonObject.getJSONObject(0).getString("WeatherIcon"));
            JSONObject metric = jsonObject.getJSONObject(0).getJSONObject("Temperature").getJSONObject("Metric");
            if (metric != null) {
                String value = metric.getString("Value");
                String unit = metric.getString("Unit");
                currentCity.setMetric(value + "°" + unit);
            }
            return currentCity;
        }
    }
}