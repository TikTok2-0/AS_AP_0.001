package com.hlgkaifu.recyclerview;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class News {
    private String title;
    private String caption;
    private String imageUrl;
    private String id;
    private String dates;
    private String category;
    private String text;
    private String link;

    private boolean lightMode;

    public News(String title, String caption, String imageUrl, String id, String dates, String category, String text, String links) {
        this.title = title;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.id = id;
        this.dates = dates;
        this.category = category;
        this.text = text;
        this.link = links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }
}
