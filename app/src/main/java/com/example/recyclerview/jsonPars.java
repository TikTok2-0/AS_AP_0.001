package com.example.recyclerview;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class jsonPars {

    private static String url;
    private String jsonStr;
    private static RequestQueue mQueue;
    private static Context context;
    private static ArrayList<News> newsal;
    private static jsonPars jsonPars;

    private jsonPars(Context context) {
        //url = "https://cdn.discordapp.com/attachments/715575746181202022/808299256318001152/jsonExports.json";
        //url = "https://cdn.discordapp.com/attachments/663113955278979096/808722126756380748/jsonExports.json";
        url = "https://raw.githubusercontent.com/TikTok2-0/ScreenScrape/main/jsonExports.json";
        this.context = context;
        newsal = new ArrayList<>();
    }
    public static synchronized jsonPars getJsonPars(Context context){
        if(jsonPars==null){
            jsonPars = new jsonPars(context);
            jsonPars.parseJson();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jsonPars;
    }
    public static ArrayList<News> getNewsal() {
        return newsal;
    }

    private void parseJson(){
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
                                String dates = news.getString("dates");
                                String category = news.getString("category");
                                String text = news.getString("text");

                                newsal.add(new News(title,caption,imageURL,id,dates,category,text));
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

        //return (newsal);
  }

}

