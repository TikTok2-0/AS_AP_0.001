package com.example.recyclerview;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<Course> courses;
    private RecyclerView courseRecyclerView;

    private jsonPars json_Pars;
    private ArrayList<News> news;

    private static final boolean AUTO_HIDE = true;

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
        hide();
        super.onStart();

        overridePendingTransition(0,0);
    }

    ImageView storyImage;
    TextView txtName;
    CardView newsCard;
    RelativeLayout nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);

        //json_Pars = new jsonPars(this);
        //news = json_Pars.parseJson();

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        storyImage = findViewById(R.id.storyImage);
        txtName = findViewById(R.id.txtName);
        newsCard = findViewById(R.id.newsCard);
        nextBtn = findViewById(R.id.nextBtn);

        courses = new ArrayList<>();

        //hide();


        BottomNavigationView bottomNavigation = findViewById(R.id.menu_bar);

        bottomNavigation.setSelectedItemId(R.id.menu_home);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(newsActivity.class);
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.menu_home):

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

            newsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        switchActivity(universalMenu.class,0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });


        news = jsonPars.getNewsal();

        courseRecyclerView = findViewById(R.id.untisRecView);
        UntisViewAdapter adapter = new UntisViewAdapter(this,this,this);

        courses.add(new Course("Mathe 11","Ho","R102",R.color.white,R.color.red));
        courses.add(new Course("English","vB","R306",R.color.purple,R.color.yellow));
        courses.add(new Course("Deutsch 2","Ve","R205",R.color.red,R.color.light_blue_900));
        courses.add(new Course("Physik 3","Dn","R306",R.color.white,R.color.grey));

        adapter.setCourses(courses);



        courseRecyclerView.setAdapter(adapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        try {
            txtName.setText(news.get(0).getTitle());

            Glide.with(this)
                    .asBitmap()
                    .load(news.get(0).getImageUrl())
                    .into(storyImage);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
    public void switchActivity(Class<?> cls, int position){
        //News news = new News(title,caption,imageURL,id,dates,category,text)
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