package com.example.recyclerview;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<Course> courses;
    private RecyclerView courseRecyclerView;

    private jsonPars json_Pars;
    private ArrayList<News> news;

    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
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
            mContentView.setSystemUiVisibility(//View.SYSTEM_UI_FLAG_LOW_PROFILE
                    View.SYSTEM_UI_FLAG_FULLSCREEN
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
                actionBar.hide();
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
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        //delayedHide(AUTO_HIDE_DELAY_MILLIS);
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
        //hide();
    }

    ImageView storyImage;
    TextView txtName;
    TextView durchschnittBtn;
    CardView newsCard;
    RelativeLayout nextBtn;
    ViewPager2 viewPager2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);

        transparentStatusAndNavigation();

        //json_Pars = new jsonPars(this);
        //news = json_Pars.parseJson();

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        storyImage = findViewById(R.id.storyImage);
        txtName = findViewById(R.id.txtName);
        newsCard = findViewById(R.id.newsCard);
        nextBtn = findViewById(R.id.nextBtn);

        durchschnittBtn = findViewById(R.id.durchschnittBtn);


        context = this;

        courses = new ArrayList<>();

        //show();


        BottomNavigationView bottomNavigation = findViewById(R.id.menu_bar);

        bottomNavigation.setSelectedItemId(R.id.menu_home);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StundenplanBottomSheetDialog bottomSheet = new StundenplanBottomSheetDialog();

                bottomSheet.show(getSupportFragmentManager(), "studenplanBottomSheet");

            }
        });


        durchschnittBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                hide();
                switchActivity(NotenrechnerActivity.class);

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
                    case(R.id.menu_homework):
                        switchActivity(homeworkActivity.class);
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

        //json_Pars =  jsonPars.getJsonPars(this);
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

        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
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

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
        mHideHandler.postDelayed(mHidePart2Runnable, 0);
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