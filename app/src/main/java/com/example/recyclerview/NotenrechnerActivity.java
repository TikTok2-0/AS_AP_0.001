package com.example.recyclerview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class NotenrechnerActivity extends AppCompatActivity {

    private ImageView BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notenrechner);

        BackBtn = findViewById(R.id.backBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(HomeScreen.class);
            }
        });


    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

}