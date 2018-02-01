package com.example.akshay.weatherapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    CharSequence type[] = {"C °","F °"};
    int selectedType =0;

    int defaultSelect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout layout = (LinearLayout)findViewById(R.id.tempType);
        LinearLayout layout1 = (LinearLayout)findViewById(R.id.citytype);
        layout.setOnClickListener(new View.OnClickListener() {
            SharedPreferences mPrefs = getSharedPreferences(MainActivity.TEMPERATURE_UNIT,MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            String getType = mPrefs.getString(MainActivity.TEMPERATURE_KEY,null);

            @Override
            public void onClick(View v) {
                if(getType!=null) {
                    defaultSelect = (getType.equals("C")) ? 0 : 1;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("Select Temperature Unit")
                        .setSingleChoiceItems(type,defaultSelect, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedType = which;

                            }
                        }).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tType = "C";
                        if(selectedType == 0)
                        {
                            tType = "C";
                        }else
                        {
                            tType ="F";
                        }


                        prefsEditor.putString(MainActivity.TEMPERATURE_KEY, tType);
                        prefsEditor.commit();

                        Intent toSend = new Intent();
                        setResult(RESULT_OK,toSend);
                        finish();
                    }
                }).show();

            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            SharedPreferences mPrefs = getSharedPreferences(MainActivity.TEMPERATURE_UNIT,MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            String getType = mPrefs.getString(MainActivity.TEMPERATURE_KEY,null);

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Setting.this);
                alert.setTitle("Enter City Details");


                final EditText city = new EditText(Setting.this);
                final EditText country = new EditText(Setting.this);

                city.setHint("Enter your city");
                country.setHint("Enter your country");
                LinearLayout ll=new LinearLayout(Setting.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(city);
                ll.addView(country);
                alert.setView(ll);
                alert.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

Intent intent = new Intent(Setting.this,MainActivity.class);
                        startActivity(intent);

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
}
