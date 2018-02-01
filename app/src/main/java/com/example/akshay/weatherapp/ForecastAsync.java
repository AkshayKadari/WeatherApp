package com.example.akshay.weatherapp;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by akshay on 4/6/2017.
 */

public class ForecastAsync extends AsyncTask<String, Void, Forecast> {
    CityWeather activity;
    public ForecastAsync(CityWeather activity)
    {
        this.activity = activity;
    }
    @Override
    protected Forecast doInBackground(String... params) {
        try {
            URL url= new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode= con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb= new StringBuilder();
                String line = reader.readLine();
                while(line != null)
                {
                    sb.append(line);
                    line=reader.readLine();
                }
                Forecast forecast = new Forecast();
                forecast=ForecastParser.ForecastParse.city(sb.toString());
                return forecast;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;


    }

    @Override
    protected void onPostExecute(Forecast key) {
        activity.onGetForecast(key);
        super.onPostExecute(key);
    }
    static public  interface ForecastData{
        public void onGetForecast(Forecast city );
    }

}