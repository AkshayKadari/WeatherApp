package com.example.akshay.weatherapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

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

public class Location2Async extends AsyncTask<String, Void, String> {

    CityWeather cityWeather;
    ProgressBar pb;

    public Location2Async(CityWeather activity) {
        this.cityWeather = activity;
    }

    @Override
    protected void onPreExecute() {

        pb= new ProgressBar(cityWeather);
        pb.setVisibility(View.VISIBLE);

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
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
                String key = Locationparser.LocationJSONParser.location(sb.toString());
                return key;
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
    protected void onPostExecute(String key) {

        pb.setVisibility(View.INVISIBLE);
        cityWeather.onGetLocation(key);
        super.onPostExecute(key);
    }
    static public  interface LocationData{
        public void onGetLocation(String ql);
    }

}