package com.example.recyclerview;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class profile_page extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

    private ArrayList<SettingsProperty> settings;

    private static final int AUTO_HIDE_DELAY_MILLIS = 0;

    private static final int UI_ANIMATION_DELAY = 0;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        hide();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0,0);
    }

    private RecyclerView settingsRecyclerView;
    TextView btnLogout;
    RelativeLayout profileEditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_page);

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);



        settings = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.mainPreferenceKey), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        settingsRecyclerView = findViewById(R.id.settingsRecView);
        SettingsViewAdapter adapter = new SettingsViewAdapter(this,this,this);

        //settings.add(new SettingsProperty("Username ","Team HLG",false,false));
        settings.add(new SettingsProperty("Name",sharedPreferences.getString("name","username"),false,false));
        settings.add(new SettingsProperty("Klasse",String.valueOf(sharedPreferences.getInt("class",1)),false,false));
        settings.add(new SettingsProperty("Schule",sharedPreferences.getString("school","hlg/kaifu"),false,false));
        //settings.add(new SettingsProperty("E-Mail","tiktok2-0@gmail.com",false,false));
        //settings.add(new SettingsProperty("Private Account","tiktok2-0@gmail.com",false,true));
        //settings.add(new SettingsProperty("Notifications","tiktok2-0@gmail.com",true,true));

        adapter.setSettings(settings);

        settingsRecyclerView.setAdapter(adapter);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        hide();

        BottomNavigationView bottomNavigation = findViewById(R.id.menu_bar);

        bottomNavigation.setSelectedItemId(R.id.menu_settings);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.menu_home):
                        switchActivity(HomeScreen.class);
                        break;
                    case(R.id.menu_news):

                        switchActivity(newsActivity.class);
                        break;
                    case(R.id.menu_settings):

                        switchActivity(profile_page.class);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });



        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                switchActivity(loginPageActivity.class);
            }
        });

        profileEditBtn = findViewById(R.id.profileEditBtn);
        Context context = this;
        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,loginPageActivity.class);
                intent.putExtra("fromLaunch",false);
                intent.putExtra("headline","Profile");
                intent.putExtra("textSize",60);
                startActivity(intent);
            }
        });

    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

}