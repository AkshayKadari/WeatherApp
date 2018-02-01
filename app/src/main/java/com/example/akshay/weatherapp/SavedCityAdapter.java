package com.example.akshay.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 4/8/2017.
 */


public class SavedCityAdapter extends RecyclerView.Adapter<SavedCityAdapter.CustomViewHolder>{
    MainActivity activity;
    List<SavedCity> mData;
    Context mContext;
    int mResource;
    private boolean mTempType;

    public SavedCityAdapter(Context context, int resource, ArrayList<SavedCity> objects, MainActivity cityWeather,boolean tType) {
        this.mContext=context;
        this.mData= objects;
        this.mResource= resource;
        this.activity=cityWeather;
        this.mTempType = tType;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rv2;
        public TextView Title;
        public TextView Temperature;
        public TextView Time;
        public ImageView Image1;


        public CustomViewHolder(View v) {
            super(v);
            this.rv2= (LinearLayout) v.findViewById(R.id.cweather);
            this.Title=(TextView)v.findViewById(R.id.textViewCityCountryName);
            this.Temperature= (TextView) v.findViewById(R.id.textViewCityTemperature);
            this.Time= (TextView) v.findViewById(R.id.textViewLastUpdated);
            this.Image1=(ImageView) v.findViewById(R.id.imageView5);

        }
    }
    @Override
    public SavedCityAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResource, null);
        SavedCityAdapter.CustomViewHolder CV=new SavedCityAdapter.CustomViewHolder(view);
        return CV;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final SavedCity EP = mData.get(position);
        holder.Title.setText(EP.getCityname()+","+EP.getCountry());
        holder.Temperature.setText( EP.getTemperature());
        holder.Time.setText(EP.getTime());

        if(EP.getFav())
            holder.Image1.setImageResource(R.drawable.star_gold);
        else
            holder.Image1.setImageResource(R.drawable.star_gray);

        holder.Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EP.getFav())
                {
                    EP.setFav(false);
                    holder.Image1.setImageResource(R.drawable.star_gray);
                    activity.update(EP);
                }
                else
                {
                    EP.setFav(true);
                    holder.Image1.setImageResource(R.drawable.star_gold);
                    activity.update(EP);
                }
            }
        });
        holder.rv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                DatabaseReference cities = ref.child("cities");
                cities.child(EP.getId()).removeValue();
                mData.remove(EP);
                return true;
            }
        });

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

