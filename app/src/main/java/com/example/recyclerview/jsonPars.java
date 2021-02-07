package com.example.recyclerview;
import android.content.Context;
import android.webkit.HttpAuthHandler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class jsonPars {

    private String url;
    private String jsonStr;
    private RequestQueue mQueue;
    private Context context;

    public jsonPars(Context context) {
        url = "https://cdn.discordapp.com/attachments/663113955278979096/808105035103469578/jsonExports_1.json";
        this.context = context;

    }

    //TODO mach das wieder zu ArrayList<Contact> statt void, also in in arraylist speichern und die dann übergeben
    public void parseJson(){
        mQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("news");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject news = jsonArray.getJSONObject(i);

                                String title = news.getString("title");
                                String caption = news.getString("caption");
                                String imageURL = news.getString("imageURL");
                                String id = news.getString("id");
                                String category = news.getString("category");
                                String text = news.getString("text");

                                System.out.println("TITEL!!!!!!!!!!!!!!"+ title);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
  }

}


