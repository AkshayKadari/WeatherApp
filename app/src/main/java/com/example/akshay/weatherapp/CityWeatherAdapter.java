package com.example.akshay.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by akshay on 4/4/2017.
 */


public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CustomViewHolder>{
    CityWeather activity;
    List<DailyForecast> mData;
    Context mContext;
    int mResource;

    public CityWeatherAdapter(Context context, int resource, ArrayList<DailyForecast> objects,CityWeather cityWeather) {
        this.mContext=context;
        this.mData= objects;
        this.mResource= resource;
        this.activity=cityWeather;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rv1;
        public TextView Title;
        public ImageView Image1;

        public CustomViewHolder(View v) {
            super(v);
            this.rv1= (LinearLayout) v.findViewById(R.id.gridlayout);
            this.Title=(TextView)v.findViewById(R.id.date);
            this.Image1=(ImageView) v.findViewById(R.id.imageView4);
        }
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResource, null);
        CustomViewHolder CV=new CustomViewHolder(view);
        return CV;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final DailyForecast EP = mData.get(position);
        holder.rv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView2= (TextView)activity.findViewById(R.id.textView7);
                textView2.setText("Forecast on : "+EP.getDate());

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date frmDate = null;
                try {
                    frmDate = sdf.parse(EP.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat sdff = new SimpleDateFormat("MMM dd, yyyy");
                String frmDateStr = sdff.format(frmDate);
                textView2.setText("Forecast on : " +frmDateStr);

                TextView textView3= (TextView) activity.findViewById(R.id.textView8);
                textView3.setText("Temperature :"+EP.getMaxtemp()+"/"+EP.getMintemp());
                ImageView imageView= (ImageView) activity.findViewById(R.id.imageView);
                ImageView imageView1= (ImageView) activity.findViewById(R.id.imageView2);
                if(EP.getDayiconid().length()==1)
                    Picasso.with(activity)
                            .load("http://developer.accuweather.com/sites/default/files/0"+EP.getDayiconid()+"-s.png")
                            .into(imageView);
                else
                    Picasso.with(activity)
                            .load("http://developer.accuweather.com/sites/default/files/"+EP.getDayiconid()+"-s.png")
                            .into(imageView);
                if(EP.getNighticonid().length()==1)
                    Picasso.with(activity)
                            .load("http://developer.accuweather.com/sites/default/files/0"+EP.getNighticonid()+"-s.png")
                            .into(imageView1);
                else
                    Picasso.with(activity)
                            .load("http://developer.accuweather.com/sites/default/files/"+EP.getNighticonid()+"-s.png")
                            .into(imageView1);

                TextView textView4= (TextView) activity.findViewById(R.id.textView9);
                textView4.setText(EP.getDayiconphrase());
                TextView textView5= (TextView) activity.findViewById(R.id.textView10);
                textView5.setText(EP.getNighticonphrase());
            }
        });
        Picasso.with(mContext).load(EP.getDayiconid()).into(holder.Image1);
        if(EP.getDayiconid().length()==1)
            Picasso.with(mContext)
                    .load("http://developer.accuweather.com/sites/default/files/0"+EP.getDayiconid()+"-s.png")
                    .into(holder.Image1);
        else
            Picasso.with(mContext)
                    .load("http://developer.accuweather.com/sites/default/files/"+EP.getDayiconid()+"-s.png")
                    .into(holder.Image1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date frmDate = null;
        try {
            frmDate = sdf.parse(EP.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdff = new SimpleDateFormat("dd MMM yy");
        String frmDateStr = sdff.format(frmDate);
        holder.Title.setText(frmDateStr);

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

