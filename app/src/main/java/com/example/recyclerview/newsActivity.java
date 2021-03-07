package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//sdk min 23: ContactsViewAdapter: l. 64,65: ContextCompat.getColor()

public class newsActivity extends AppCompatActivity {

    private jsonPars json_Pars;
    private static ArrayList<News> news;
    private static ArrayList<News> newsHlg;
    private static ArrayList<News> newsKfu;
    private static int length;

    private static final int UI_ANIMATION_DELAY = 0;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
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
    private final Runnable mHideRunnable = this::hide;

    private final View.OnTouchListener mDelayHideTouchListener = (view, motionEvent) -> {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (AUTO_HIDE) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
            default:
                break;
        }
        return false;
    };

    private static RecyclerView contactsRecyclerView;
    private static ImageView logo;
    private Context context;
    RelativeLayout homeScreen;
    TextView txt1;
    TextView txt2;

    ActionBar toolbar;

/*
    @Override
    protected void onStart() {
        super.onStart();
        json_Pars =  jsonPars.getJsonPars(this);
        news = jsonPars.getNewsal();
        overridePendingTransition(100,100);

    }
*/

    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(0,0);
    }

    static NewsViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        setContentView(R.layout.activity_news);
        context = this;
        newsHlg = new ArrayList<>();
        newsKfu = new ArrayList<>();
        //json_Pars =  jsonPars.getJsonPars(this);
        news = jsonPars.getNewsal();

        for(int i=0; i<=5; i++){
            newsHlg.add(news.get(i));
        }
        for(int i=6; i<=11; i++){
            newsKfu.add(news.get(i));
        }


        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        hide();

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt2.getText().equals("KFU")) {
                    txt1.setText("KFU");
                    txt2.setText("HLG");
                    adapter.setNews(newsKfu);
                    contactsRecyclerView.setAdapter(adapter);
                }else{
                    txt1.setText("HLG");
                    txt2.setText("KFU");
                    adapter.setNews(newsHlg);
                    contactsRecyclerView.setAdapter(adapter);
                }
            }
        });



        BottomNavigationView bottomNavigation = findViewById(R.id.menu_bar);

        bottomNavigation.setSelectedItemId(R.id.menu_news);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.menu_home):
                        switchActivity(HomeScreen.class);
                        break;
                    case(R.id.menu_news):

                        //switchActivity(MainActivity.class);
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

        contactsRecyclerView = findViewById(R.id.contactsRecView);
        adapter = new NewsViewAdapter(this, this, this);
        adapter.setNews(newsHlg);
        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));   //display items in linear layout untereinander

        //grid layout kann auch benutzt werden
        //contactsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }


    public void switchActivity(Class<?> cls, int position){
        News newsObj = news.get(position);
        Intent intent = new Intent(this,cls);  // (mainActivity, menu1.class);

        intent.putExtra("title", newsObj.getTitle());
        intent.putExtra("caption",newsObj.getCaption());
        intent.putExtra("imageURL",newsObj.getImageUrl());
        intent.putExtra("id",newsObj.getId());
        intent.putExtra("dates",newsObj.getDates());
        intent.putExtra("category",newsObj.getCategory());
        intent.putExtra("text",newsObj.getText());

        startActivity(intent);
    }
    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);  // (mainActivity, menu1.class);
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

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);

    }

}