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


public class CurrentCityAsync extends AsyncTask<String, Void, CurrentCity> {
    MainActivity activity;
    ProgressBar pb;

    public CurrentCityAsync(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

        pb= new ProgressBar(activity);
        pb.setVisibility(View.VISIBLE);

        super.onPreExecute();
    }

    @Override
    protected CurrentCity doInBackground(String... params) {
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
                CurrentCity currentCity = new CurrentCity();
                currentCity=CurrentCityParser.CityParser.city(sb.toString());
                return currentCity;
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
    protected void onPostExecute(CurrentCity key) {
        pb.setVisibility(View.INVISIBLE);
        activity.onGetCurrentCity(key);
        super.onPostExecute(key);
    }
    static public  interface CurrentCityData{
        public void onGetCurrentCity(CurrentCity city );
    }

}