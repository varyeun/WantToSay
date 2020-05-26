package com.example.myapplication.ui.Text;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KoreaTimesClicked extends AppCompatActivity {

    private ListView newsListView;
    private List<News> newsList;

    private String htmlPageUrl = "https://www.koreatimes.co.kr/www2/index.asp"; //파싱할 홈페이지의 URL주소

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);

        //리스트뷰 클
        newsListView = (ListView)findViewById(R.id.NewsListView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), KoreaTimesNewsClicked.class);

                intent.putExtra("title",newsList.get(position).getTitle());
                intent.putExtra("url",newsList.get(position).getContext());
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                String title="";
                String url="";
                String date = "";
                String resId="";
                Document doc = Jsoup.connect(htmlPageUrl).get();

                newsList = new ArrayList<News>();

                Elements news = doc.select("div[class=index_more_article]");
                for(Element e: news){
                    title = e.select("div[class=index_more_headline HD]").select("a").text();
                    url = e.select("div[class=index_more_headline HD]").select("a").attr("href");
                    resId = e.select("div[class=index_more_photo]").select("a img").attr("src");
                    newsList.add(new News(title,"The Korea Times",date,url,resId));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsListAdapter adapter = new NewsListAdapter(getApplicationContext(),newsList);
            newsListView.setAdapter(adapter);
        }
    }


}