package com.example.akshay.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akshay on 4/6/2017.
 */


public class ForecastParser {
    static  public  class  ForecastParse{
        static Forecast forecast=new Forecast();
        static Forecast city(String in) throws JSONException {
            JSONObject jsonObject = new JSONObject(in);
            ArrayList<DailyForecast> dailyForecastArrayList= new ArrayList<>();
            if(jsonObject!=null)
            {
                Forecast forecast= new Forecast();
                JSONObject headline= jsonObject.getJSONObject("Headline");
                if(headline.getString("Text")!=null) {
                    Log.d("demo","------"+headline.getString("Text"));
                    forecast.setHeadlinetext(headline.getString("Text"));
                }
                if(headline.getString("MobileLink")!=null)
                    forecast.setMobilelinkurl(headline.getString("MobileLink"));
                if(jsonObject.getJSONArray("DailyForecasts")!=null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("DailyForecasts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DailyForecast dailyForecast = new DailyForecast();
                        if(jsonArray.getJSONObject(i).getString("Date")!=null)
                            dailyForecast.setDate(jsonArray.getJSONObject(i).getString("Date"));
                        if(jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Minimum")!=null)
                        {
                            String minvalue=jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                            String minunit=jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            dailyForecast.setMintemp(minvalue+"°"+minunit);
                        }
                        if(jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Maximum")!=null)
                        {
                            String maxvalue=jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                            String maxunit=jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Maximum").getString("Unit");
                            dailyForecast.setMaxtemp(maxvalue+"°"+maxunit);
                        }
                        if(jsonArray.getJSONObject(i).getJSONObject("Day")!=null)
                        {
                            dailyForecast.setDayiconid(jsonArray.getJSONObject(i).getJSONObject("Day").getString("Icon"));
                            dailyForecast.setDayiconphrase(jsonArray.getJSONObject(i).getJSONObject("Day").getString("IconPhrase"));
                        }
                        if(jsonArray.getJSONObject(i).getJSONObject("Night")!=null)
                        {
                            dailyForecast.setNighticonid(jsonArray.getJSONObject(i).getJSONObject("Night").getString("Icon"));
                            dailyForecast.setNighticonphrase(jsonArray.getJSONObject(i).getJSONObject("Night").getString("IconPhrase"));
                        }
                        if(jsonArray.getJSONObject(i).getString("MobileLink")!=null)
                            dailyForecast.setDailymobilelink(jsonArray.getJSONObject(i).getString("MobileLink"));


                        dailyForecastArrayList.add(dailyForecast);
                    }
                    forecast.setDailyForecastArrayList(dailyForecastArrayList);
                }
                return forecast;
            }
            return null;
        }
    }
}