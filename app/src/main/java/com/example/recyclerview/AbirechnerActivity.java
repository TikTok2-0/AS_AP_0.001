package com.example.recyclerview;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AbirechnerActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

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
        }
    };

    private TextView numSemster;
    private TextView numAbitur;
    private TextView numTotal;
    private TextView numAverage;
    private RecyclerView recyclerView;
    private AbiNoteViewAdapter adapter;
    private ArrayList<AbiNote> Abinoten = new ArrayList<>();
    private ImageView addBtn;
    private int pointsSemester;
    private int pointsAbitur;
    private int pointsTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abirechner);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        numSemster = findViewById(R.id.numSemester);
        numAbitur = findViewById(R.id.numAbitur);
        numTotal = findViewById(R.id.numTotal);
        numAverage = findViewById(R.id.numAverage);

        Abinoten.add(new AbiNote("Mathe", 11,12,13,14,true,13,true));

        updateStats();

        recyclerView = findViewById(R.id.detailRecView);
        adapter = new AbiNoteViewAdapter(this,this,Abinoten);
        initItemTouchHelper();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        addBtn = findViewById(R.id.addBtn);
        AbirechnerBottomSheetDialog bottomSheet = new AbirechnerBottomSheetDialog(this);
        View.OnClickListener abirechnerBottomSheetOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.show(getSupportFragmentManager(), "abirechnerBottomSheet");
            }
        };
        addBtn.setOnClickListener(abirechnerBottomSheetOnClickListener);


    }

    public void addAbinote(AbiNote abiNote){
        adapter.addAbinote(abiNote);
    }

    private void calcPointsSem(){
        pointsSemester = 0;

        for(int i=0; i<Abinoten.size(); i++){
            int loopTotal = 0;
            loopTotal += Abinoten.get(i).getPointsSem1() + Abinoten.get(i).getPointsSem2()
                    +Abinoten.get(i).getPointsSem3() + Abinoten.get(i).getPointsSem4();

            if(Abinoten.get(i).isHigherLevel()){
                loopTotal *= 2;
            }
            pointsSemester += loopTotal;
        }
    }

    private void calcPointsAbitur(){
        pointsAbitur = 0;

        for(int i=0; i<Abinoten.size(); i++){
            if(Abinoten.get(i).isExam()){
                pointsAbitur += Abinoten.get(i).getExamGrade() *5;
            }
        }
    }

    private void calcPointsTotal(){
        pointsTotal =  pointsAbitur + pointsSemester;
    }

    /*
    //TODO format durchschnitt
    public float calcAverage(){

    }

     */

    public void updateStats(){
        calcPointsSem();
        calcPointsAbitur();
        calcPointsTotal();

        numSemster.setText(String.valueOf(pointsSemester));
        numAbitur.setText(String.valueOf(pointsAbitur));
        numTotal.setText(String.valueOf(pointsTotal));

    }

    private void initItemTouchHelper(){
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeAbinote(viewHolder.getAdapterPosition());
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
        startActivity(intent);
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

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}