package com.example.akshay.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by akshay on 4/6/2017.
 */

public class Locationparser {
    static  public  class  LocationJSONParser{
        static String key;
        static String location(String in) throws JSONException {
            JSONArray jsonObject = new JSONArray(in);
            if(jsonObject!=null) {
                if (jsonObject.getJSONObject(0).getString("Key") != null)
                    key = jsonObject.getJSONObject(0).getString("Key");
            }
            return  key;
        }
    }
}
