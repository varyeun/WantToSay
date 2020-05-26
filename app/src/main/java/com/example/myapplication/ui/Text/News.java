package com.example.myapplication.ui.Text;

import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.google.gson.internal.$Gson$Preconditions;

public class News {

    private String title;
    private String company;
    private String date;
    private String context;
    private String resId;

    public News(String title, String company, String date, String context) {
        this.title = title;
        this.company = company;
        this.date = date;
        this.context = context;
    }
    public News(String title, String company, String date, String context,String resId) {
        this.title = title;
        this.company = company;
        this.date = date;
        this.context = context;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setcompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}
