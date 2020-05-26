package com.example.myapplication.ui.Text;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    LayoutInflater inflater;
    private List<News> newsList;

    public NewsListAdapter(Context context, List<News> newsList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.news, null);
        TextView noticeText = (TextView)v.findViewById(R.id.title);
        TextView nameText = (TextView)v.findViewById(R.id.company);
        TextView dateText = (TextView)v.findViewById(R.id.date);
        ImageView newsimg = (ImageView)v.findViewById(R.id.newsimg);

        noticeText.setText(newsList.get(position).getTitle());
        nameText.setText(newsList.get(position).getCompany());
        dateText.setText(newsList.get(position).getDate());
        if(newsList.get(position).getResId()==""){
            System.out.println(nameText.getText());
           if(nameText.getText().equals("abc NEWS")) {
               newsimg.setImageResource(R.drawable.abclist);
           }
        }else{
            Glide.with(context).load(newsList.get(position).getResId()).into(newsimg);
        }

        v.setTag(newsList.get(position).getTitle());
        return v;
    }
}