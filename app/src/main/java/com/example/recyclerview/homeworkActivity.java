package com.example.recyclerview;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class homeworkActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
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

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0,0);
    }

    ImageView addBtn;
    RecyclerView homeworkRecyclerView;
    SharedPreferences sharedPreferences;
    HomeworkViewAdapter adapter;
    SwitchCompat switchHomework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homework);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        addBtn = findViewById(R.id.addBtn);


        // Set up the user interaction to manually show or hide the system UI.
        hide();

        BottomNavigationView bottomNavigation = findViewById(R.id.menu_bar);
        switchHomework = findViewById(R.id.switchHomework);

        bottomNavigation.setSelectedItemId(R.id.menu_homework);

        sharedPreferences = this.getSharedPreferences(getString(R.string.mainPreferenceKey),Context.MODE_PRIVATE);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.menu_home):
                        switchActivity(HomeScreen.class);
                        break;
                    case(R.id.menu_news):

                        //switchActivity(MainActivity.class);
                        switchActivity(NewsActivity.class);
                        break;
                    case(R.id.menu_settings):

                        switchActivity(ProfilePageActivity.class);
                        break;
                    case(R.id.menu_homework):

                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        if(getList(getString(R.string.homeworkPreferenceKey))!=null){
            Homework.homeworkList = getList(getString(R.string.homeworkPreferenceKey));
        }

        homeworkRecyclerView = findViewById(R.id.homeworkRecView);
        adapter = new HomeworkViewAdapter(this,this,this);
        adapter.sort(Homework.homeworkList,getString(R.string.homeworkPreferenceKey));
        adapter.sort(Homework.completedHomework,getString(R.string.completedHomeworkPreferenceKey));
        adapter.updateHomework();

        homeworkRecyclerView.setAdapter(adapter);
        homeworkRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        Context context = this;
        homeworkActivity thisInstance = this;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-----------------Aktuelle Hausaufgaben:"+Homework.homeworkList);
                System.out.println("\n\n\n\n--------------------------------------");
                HomeworkBottomSheetDialog bottomSheet = new HomeworkBottomSheetDialog(getSupportFragmentManager(),adapter,thisInstance,false);

                bottomSheet.show(getSupportFragmentManager(), "homeworkBottomSheet");
            }
        });

        //adapter = new HomeworkViewAdapter(this,this,this);
        switchHomework.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Homework.updateActiveHomework();
                System.out.println(Homework.activeHomwork.toString());
                adapter.updateHomework();
                adapter.notifyDataSetChanged();
                adapter.notifyItemRangeChanged(0,adapter.getItemCount());
            }
        });

    }

    public HomeworkViewAdapter getAdapter(){
        return adapter;
    }
    public ArrayList<Homework> getList(String key){
        ArrayList<Homework> arrayItems = null;
        String serializedObject = sharedPreferences.getString(key,null);
        if(serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Homework>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject,type);
        }
        return arrayItems;
    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);  // (mainActivity, menu1.class);
        startActivity(intent);
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


}