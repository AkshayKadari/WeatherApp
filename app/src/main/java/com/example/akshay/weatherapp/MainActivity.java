package com.example.akshay.weatherapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.akshay.weatherapp.R.id.editText;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,LocationAsync.LocationData,CurrentCityAsync.CurrentCityData{

    EditText cityname;
    EditText countryname;
    Button btnSearch,button;
    int count;
    private RecyclerView recyclerView;
    ArrayList<CurrentCity> citieslist = new ArrayList<CurrentCity>();
    String srt,srt1;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cities = ref.child("cities");
    ArrayList<SavedCity> firebaseList=new ArrayList<>();

    static String KEY1="city key";
    static String KEY2="country key";
    final static String TEMPERATURE_UNIT = "temperatureUnit";
    final static String TEMPERATURE_KEY = "temperatureKey";
    private boolean tType= false;
    final static int MAIN_ACTIVITY = 0011;
    static final int CITY_WEATHER = 001;
    static final String CITY_COUNTRY ="city_country";
    static SharedPreferences mPrefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenu = getMenuInflater();
        myMenu.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case MAIN_ACTIVITY:

                case CITY_WEATHER:
                    // BindCity();
                    break;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mainsetting:
                Intent setting  = new Intent(MainActivity.this,Setting.class);
                startActivityForResult(setting,MAIN_ACTIVITY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        cityname = (EditText) findViewById(editText);
        countryname = (EditText) findViewById(R.id.editText2);
        btnSearch = (Button) findViewById(R.id.button2);
        btnSearch.setOnClickListener(MainActivity.this);
        button = (Button)findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recview);


        cities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    firebaseList.clear();
                    count= (int) dataSnapshot.getChildrenCount();


                    for (DataSnapshot d:dataSnapshot.getChildren()) {
                        SavedCity firebasedb=d.getValue(SavedCity.class);
                        firebaseList.add(firebasedb);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        SavedCityAdapter adapter=new SavedCityAdapter(this,R.layout.item_each_city,firebaseList,MainActivity.this,tType);
       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rView = (RecyclerView)findViewById(R.id.recview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(linearLayoutManager);
        rView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        int listitems= rView.getChildCount();

        if(listitems!=0){
            TextView t2 = (TextView)findViewById(R.id.textView4);
            t2.setText("Saved Cities");
            Toast.makeText(getApplicationContext(),"saved cities",Toast.LENGTH_SHORT).show();
            TextView t3 = (TextView)findViewById(R.id.textView5);
            t3.setText("");


        }
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Enter City Details");


                final EditText city = new EditText(MainActivity.this);
                final EditText country = new EditText(MainActivity.this);
                button.setVisibility(View.INVISIBLE);
                BindCity();
                city.setHint("Enter your city");
                country.setHint("Enter your country");
                LinearLayout ll=new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(city);
                ll.addView(country);
                alert.setView(ll);
                alert.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
TextView c = (TextView)findViewById(R.id.current);
                        c.setVisibility(View.INVISIBLE);
                         srt = city.getEditableText().toString();
                        Toast.makeText(MainActivity.this,srt,Toast.LENGTH_LONG).show();
                        srt1 = country.getEditableText().toString();
                        Toast.makeText(MainActivity.this,srt1,Toast.LENGTH_LONG).show();
                        new   LocationAsync(MainActivity.this).execute("http://dataservice.accuweather.com/locations/v1/"+srt1+"/search?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q="+srt);

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
    });
}


    private void BindCity(){
        mPrefs = getSharedPreferences(MainActivity.TEMPERATURE_UNIT,MODE_PRIVATE);
        String type = mPrefs.getString(TEMPERATURE_KEY,null);
        if(mPrefs!=null) {
            String key = mPrefs.getString("key","empty");
            String citynam=mPrefs.getString("city","empty");
            String country=mPrefs.getString("country","empty");
            if(!citynam.equals("empty"))
            {
                if(!country.equals("empty"))
                {
                    TextView textViewhead = (TextView) findViewById(R.id.current);
                    Log.d("demo",citynam+"---"+country);
                    textViewhead.setText(citynam + "," + country);
                    new   LocationAsync(MainActivity.this).execute("http://dataservice.accuweather.com/locations/v1/"+country+"/search?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q="+citynam);
                }
            }
            if (!key.equals("empty")) {
                new CurrentCityAsync(MainActivity.this).execute("http://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q");
            }
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.button2:
                if(cityname.getText().toString().equals("") || countryname.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"please enter city and country",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(URLUtil.isValidUrl("http://dataservice.accuweather.com/locations/v1/"+countryname.getText().toString()+"/search?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q="+cityname.getText().toString()))
                    {
                        Intent intent = new Intent(MainActivity.this, CityWeather.class);
                        intent.putExtra(KEY1, countryname.getText().toString());
                        intent.putExtra(KEY2, cityname.getText().toString());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"please enter valid city and country",Toast.LENGTH_SHORT).show();
                    }

                }

        }
    }

    @Override
    public void onGetLocation(String key) {
        Toast.makeText(getApplicationContext(),"current city set",Toast.LENGTH_SHORT).show();
        mPrefs= getSharedPreferences(MainActivity.TEMPERATURE_UNIT,MODE_PRIVATE);
        final SharedPreferences.Editor editor= mPrefs.edit();
        editor.clear().apply();
        editor.putString("city",srt);
        editor.putString("country",srt1);
        editor.putString("key",key);
        editor.apply();
        if(key==null)
            Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_SHORT).show();
        else
            new CurrentCityAsync(MainActivity.this).execute("http://dataservice.accuweather.com/currentconditions/v1/"+key+"?apikey=wEfxa8SCjrsxFxI7XA1zP3jtdHRqZQAD&q");

    }
    public   void update(SavedCity firebaseDb)
    {
        String id=firebaseDb.getId();
        DatabaseReference newref=cities.child(id);
        newref.setValue(firebaseDb);
    }
    public void delete(SavedCity firebaseDb)
    {
        SavedCityAdapter adapter=new SavedCityAdapter(this,R.layout.item_each_city,firebaseList,MainActivity.this,tType);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rView = (RecyclerView)findViewById(R.id.recview);
        rView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
        rView.setLayoutManager(linearLayoutManager);
        rView.setAdapter(adapter);
        cities.child(firebaseDb.getId()).removeValue();
    }


    @Override
    public void onGetCurrentCity(CurrentCity city) {
        Toast.makeText(getApplicationContext(),"current city set",Toast.LENGTH_SHORT).show();

       // button.setVisibility(View.INVISIBLE);
        TextView tv1= (TextView) findViewById(R.id.textView13);
        tv1.setText(srt+","+srt1);
        TextView tv2= (TextView) findViewById(R.id.textView17);
        tv2.setText(city.getWeathertext());
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        TextView tv3= (TextView) findViewById(R.id.textView);
        tv3.setText("Temperature"+city.getMetric());
        TextView tv4= (TextView) findViewById(R.id.textView15);
        tv4.setText("Updated: "+city.getLocalobvtime());
        if(city.getWeathericon().length()==1)
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/0"+city.getWeathericon()+"-s.png")
                    .into(imageView);
        else
            Picasso.with(this)
                    .load("http://developer.accuweather.com/sites/default/files/"+city.getWeathericon()+"-s.png")
                    .into(imageView);
        Log.d("demo",city.toString());
    }
    }

