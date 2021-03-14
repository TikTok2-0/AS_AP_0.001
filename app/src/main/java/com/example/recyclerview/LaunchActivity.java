package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {


    jsonPars json_Pars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        json_Pars =  jsonPars.getJsonPars(this);

        jsonPars.getNewsal();
        //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.mainPreferenceKey), Context.MODE_PRIVATE);



        System.out.println("----sharedPreferences: "+sharedPreferences.getAll().toString());
        if(sharedPreferences.getAll().toString().equals("{}")){


            switchActivity(loginPageActivity.class,true);
        }else{
            switchActivity(HomeScreen.class);


        }

    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void switchActivity(Class<?> cls, boolean fromLaunch){

        Intent intent = new Intent(this,cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("fromLaunch",fromLaunch);
        startActivity(intent);
    }

}