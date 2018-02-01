package com.example.akshay.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CityWeather extends AppCompatActivity implements Location2Async.LocationData,ForecastAsync.ForecastData,CurrentCityAsync.CurrentCityData{
    private RecyclerView recyclerView1;
    String key,time;
    int count;
    private CityWeatherAdapter cityWeatherAdapter;
   // private ArrayList<DailyForecast> CityDayWeatherList;
    private TextView topTag;
    final static int CITY_ACTIVITY = 00111;
    public static final String STATUS_PENDING ="Pending";
   ArrayList<SavedCity> citieslist = new ArrayList<SavedCity>();
    String city1,country;
   // FirebaseApp.initializeApp(this);
    final static String API_KEY = "2pLNxiVrAOPPfC7i2EAGDWRoqZSK9f5P";
    static final String CITY_COUNTRY ="city_country";
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cities = ref.child("cities");
    String[] cityCountryName;
    private boolean tType= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);


        country = (String) getIntent().getExtras().get(MainActivity.KEY1);
        city1 = (String) getIntent().getExtras().get(MainActivity.KEY2);
        new Location2Async(CityWeather.this).execute("http://dataservice.accuweather.com/locations/v1/" + country + "/search?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q=" + city1);
        recyclerView1 = (RecyclerView) findViewById(R.id.cityrecview);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //cityWeatherAdapter = new CityWeatherAdapter(CityDayWeatherList, this, R.layout.gridview, tType);


        Firebase.setAndroidContext(getApplicationContext());
        FirebaseApp.initializeApp(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenu = getMenuInflater();
        myMenu.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {

           case R.id.itemSaveCity:

               final SavedCity firebaseDb = new SavedCity();
               firebaseDb.setCitykey(key);
               firebaseDb.setCityname(city1);
               firebaseDb.setCountry(country);
               firebaseDb.setTime(time);
               firebaseDb.setTemperature("13.5 F");
               firebaseDb.setFav(false);
               if (count == 0) {

                   String key = cities.push().getKey();
                   firebaseDb.setId(key);
                   Log.d("demo", key);
                   cities.child(key).setValue(firebaseDb);
               }
               cities.addValueEventListener(new com.google.firebase.database.ValueEventListener() {


                   @Override
                   public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                       if (dataSnapshot.getValue() != null) {

                           citieslist.clear();
                           for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                               Log.d("demo", snapshot.getValue(SavedCity.class).toString());
                               SavedCity savedCity = snapshot.getValue(SavedCity.class);
                               citieslist.add(savedCity);
                           }

                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
               for (SavedCity fb : citieslist) {
                   Log.d("demo1", fb.toString());
               }
               return true;
           case R.id.currentcity:

               final SharedPreferences.Editor editor= MainActivity.mPrefs.edit();
               editor.clear().apply();
               editor.putString("city",city1);
               editor.putString("country",country);
               // editor.putString("key",key);
               editor.apply();
               return true;
           case R.id.itemSetting:
               Intent setting  = new Intent(CityWeather.this,Setting.class);
                startActivityForResult(setting,CITY_ACTIVITY);
                break;
       }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CITY_ACTIVITY:
                    BindLists();
                    break;
            }
        }
    }



    private void BindLists()
    {
        SharedPreferences mPrefs = getSharedPreferences(MainActivity.TEMPERATURE_UNIT,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String type = mPrefs.getString(MainActivity.TEMPERATURE_KEY,null);
        if(type!=null) {
            tType = (type.equals("F")) ? true : false;
        }

    }

    @Override
    public void onGetLocation(String ql) {
        new ForecastAsync(CityWeather.this).execute("http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+ql+"?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q");
              key=ql;
    }

    @Override
    public void onGetForecast(Forecast city) {
        TextView tv14 = (TextView)findViewById(R.id.extendedforecast);

       final String u = city.getMobilelinkurl();
        tv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri webpage = Uri.parse(u);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        TextView tv11 = (TextView)findViewById(R.id.textView11);
        final String y = city.getDailyForecastArrayList().get(0).getDailymobilelink();
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri web = Uri.parse(y);
                Intent webp = new Intent(Intent.ACTION_VIEW, web);
                startActivity(webp);
            }
        });

        recyclerView1 = (RecyclerView) findViewById(R.id.cityrecview);
        TextView textView= (TextView) findViewById(R.id.textView2);
        textView.setText("Daily Forecast for"+city1+", "+country);
        TextView textView1= (TextView) findViewById(R.id.textView6);
        textView1.setText(city.getHeadlinetext());
        TextView textView2= (TextView) findViewById(R.id.textView7);
        String date1 = city.getDailyForecastArrayList().get(0).getDate();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date frmDate = null;
        try {
            frmDate = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdff = new SimpleDateFormat("MMM dd, yyyy");
        String frmDateStr = sdff.format(frmDate);
        textView2.setText("Forecast on : " +frmDateStr);

        TextView textView3= (TextView) findViewById(R.id.textView8);
        textView3.setText("Temperature :"+city.getDailyForecastArrayList().get(0).getMaxtemp()+"/"+city.getDailyForecastArrayList().get(0).getMintemp());
        ImageView imageView= (ImageView) findViewById(R.id.imageView);
        ImageView imageView1= (ImageView) findViewById(R.id.imageView2);
        if(city.getDailyForecastArrayList().get(0).getDayiconid().length()==1)
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/0"+city.getDailyForecastArrayList().get(0).getDayiconid()+"-s.png")
                    .into(imageView);
        else
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/"+city.getDailyForecastArrayList().get(0).getDayiconid()+"-s.png")
                    .into(imageView);
        if(city.getDailyForecastArrayList().get(0).getNighticonid().length()==1)
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/0"+city.getDailyForecastArrayList().get(0).getNighticonid()+"-s.png")
                    .into(imageView1);
        else
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/"+city.getDailyForecastArrayList().get(0).getNighticonid()+"-s.png")
                    .into(imageView1);

        TextView textView4= (TextView) findViewById(R.id.textView9);
        textView4.setText(city.getDailyForecastArrayList().get(0).getDayiconphrase());
        TextView textView5= (TextView) findViewById(R.id.textView10);
        textView5.setText(city.getDailyForecastArrayList().get(0).getNighticonphrase());
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        cityWeatherAdapter = new CityWeatherAdapter(this,R.layout.gridview,city.getDailyForecastArrayList(),CityWeather.this);

        recyclerView1.setAdapter(cityWeatherAdapter);
        cityWeatherAdapter.notifyDataSetChanged();

    }


    @Override
    public void onGetCurrentCity(CurrentCity city) {
         time = city.getLocalobvtime();
    }
}
