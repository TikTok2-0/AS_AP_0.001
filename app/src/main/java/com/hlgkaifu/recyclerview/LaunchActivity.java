package com.hlgkaifu.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.hlgkaifu.recyclerview.HomeScreenActivity;
import com.hlgkaifu.recyclerview.LoginPageActivity;

public class LaunchActivity extends AppCompatActivity {


    com.hlgkaifu.recyclerview.JsonPars json_Pars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        json_Pars =  com.hlgkaifu.recyclerview.JsonPars.getJsonPars(this);

        com.hlgkaifu.recyclerview.JsonPars.getNewsal();
        //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.mainPreferenceKey), Context.MODE_PRIVATE);



        System.out.println("----sharedPreferences: "+sharedPreferences.getAll().toString());
        if(sharedPreferences.getAll().toString().equals("{}")){


            switchActivity(LoginPageActivity.class,true);
        }else{
            switchActivity(HomeScreenActivity.class);


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