package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotenrechnerActivity extends AppCompatActivity {

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

    private ImageView BackBtn;
    private RecyclerView recyclerView;
    private TextView Schnitt;
    private ImageView AddButton;
    private int positionInsert;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private ArrayList<Note> Noten = new ArrayList<>();
    private NotenrechnerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notenrechner);

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        hide();
        recyclerView = findViewById(R.id.notenrechnerRecView);
        BackBtn = findViewById(R.id.backBtn);
        Schnitt =(TextView) findViewById(R.id.Schnitt);
        AddButton = findViewById(R.id.addBtn);

        sharedPreferences = this.getSharedPreferences(
                "mainPreferenceKey", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(HomeScreenActivity.class);
            }
        });

        if(getList("list") != null){
            Noten = getList("list");
        }

        try {
            positionInsert = Noten.size();
        }catch (Exception e){
            System.out.println(e);
        }

        adapter = new NotenrechnerViewAdapter(this,this, Noten);
        initItemTouchHelper();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        changeDurchschnitt(adapter.getNoten());

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addNote(new Note("", "1+"));
                adapter.notifyItemInserted(positionInsert);
                positionInsert++;
            }
        });

    }

    private void initItemTouchHelper(){
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeNote(viewHolder.getAdapterPosition());
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    public void changeDurchschnitt(ArrayList<Note> Noten){
        double durch = 0;

        positionInsert = Noten.size();

        for(int i=0; i<Noten.size();i++){
            durch += convertNoteToPunkte(Noten.get(i).getNote());
        }
        durch = durch/Noten.size();
        durch = (17-durch)/3;
        durch = Math.round(durch*100.0)/100.0;

        if(durch>0) {
            //try {
            Schnitt.setText(String.valueOf(durch));
            //}catch(Exception e){
            //System.out.println("---------"+e+"  "+durch);
            //}
        }
    }

    public ArrayList<Note> getList(String key){
        ArrayList<Note> arrayItems = null;
        String serializedObject = sharedPreferences.getString(key,null);
        if(serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Note>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject,type);
        }
        return arrayItems;
    }

    public int convertNoteToPunkte(String Note){

        switch(Note){
            case "1+":
                return 15;
            case "1":
                return 14;
            case "1-":
                return 13;
            case "2+":
                return 12;
            case "2":
                return 11;
            case "2-":
                return 10;
            case "3+":
                return 9;
            case "3":
                return 8;
            case "3-":
                return 7;
            case "4+":
                return 6;
            case "4":
                return 5;
            case "4-":
                return 4;
            case "5+":
                return 3;
            case "5":
                return 2;
            case "5-":
                return 1;
            case "6":
                return 0;
        }

        return 0;
    }

    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);
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