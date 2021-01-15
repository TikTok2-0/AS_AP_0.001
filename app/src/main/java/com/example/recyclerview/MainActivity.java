package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ImageView logo;
    String logoLink = "https://www.hlg-hamburg.de/wp-content/uploads/2019/06/logo.png";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsRecyclerView = findViewById(R.id.contactsRecView);

        logo = findViewById(R.id.hlgLogo);

        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Das HLG während Corona", "marot@gmail.com","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Helene-Lange-Gymnasium_%28Hamburg-Harvestehude%29.2.29247.ajb.jpg/1280px-Helene-Lange-Gymnasium_%28Hamburg-Harvestehude%29.2.29247.ajb.jpg"));
        contacts.add(new Contact("Die Arbeit mit Moodle", "mona.lisa@gmail.com","https://i.ibb.co/F7d5cmc/moodle.jpg"));
        //contacts.add(new Contact("BBBbb Stever Jobs", "steve@microsoft.com","https://cdn.discordapp.com/attachments/663113955278979096/798914901468774420/IMG_20201216_221527.jpg"));

        for(int i = 0; i<10;i++)contacts.add(new Contact("PlaceHolder "+String.valueOf(i+1), "dave@gmail.com","https://cdn.discordapp.com/attachments/663113955278979096/798914901468774420/IMG_20201216_221527.jpg"));

        ContactsViewAdapter adapter = new ContactsViewAdapter(this,this,this);
        adapter.setContacts(contacts);

        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));   //display items in linear layout untereinander

        Glide.with(this).asBitmap().load(logoLink).into(logo);

        //grid layout kann auch benutzt werden
        //contactsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
    public void switchActivity(Class<?> cls){

        Intent intent = new Intent(this,cls);  // (mainActivity, menu1.class);
        startActivity(intent);
    }
}