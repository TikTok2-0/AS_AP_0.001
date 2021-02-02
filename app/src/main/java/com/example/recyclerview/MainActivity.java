package com.example.recyclerview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//sdk min 23: ContactsViewAdapter: l. 64,65: ContextCompat.getColor()

public class MainActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ImageView logo;
    private View decorView;
    String logoLink = "https://www.hlg-hamburg.de/wp-content/uploads/2019/06/logo.png";
    RelativeLayout homeScreen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        homeScreen = findViewById(R.id.homeMenu);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });



        contactsRecyclerView = findViewById(R.id.contactsRecView);

        logo = findViewById(R.id.hlgLogo);



        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("HLG und Corona", false,"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Helene-Lange-Gymnasium_%28Hamburg-Harvestehude%29.2.29247.ajb.jpg/1280px-Helene-Lange-Gymnasium_%28Hamburg-Harvestehude%29.2.29247.ajb.jpg"));
        contacts.add(new Contact("Die Arbeit mit Moodle", true,"https://i.ibb.co/F7d5cmc/moodle.jpg"));
        contacts.add(new Contact("Itslearning",true,"https://www.univention.de/wp-content/uploads/2019/01/190618-itsLearning-logo-blog-header.png"));
        //contacts.add(new Contact("BBBbb Stever Jobs", "steve@microsoft.com","https://cdn.discordapp.com/attachments/663113955278979096/798914901468774420/IMG_20201216_221527.jpg"));

        for(int i = 0; i<10;i++)contacts.add(new Contact("PlaceHolder "+(i+1), false,"https://cdn.discordapp.com/attachments/663113955278979096/798914901468774420/IMG_20201216_221527.jpg"));

        ContactsViewAdapter adapter = new ContactsViewAdapter(this,this,this);
        adapter.setContacts(contacts);

        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));   //display items in linear layout untereinander

        Glide.with(this).asBitmap().load(logoLink).into(logo);

        //grid layout kann auch benutzt werden
        //contactsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }


    public void switchActivity(Class<?> cls, String headline){

        Intent intent = new Intent(this,cls);  // (mainActivity, menu1.class);
        intent.putExtra("headline",headline);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }



}