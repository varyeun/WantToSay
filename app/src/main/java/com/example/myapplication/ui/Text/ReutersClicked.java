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


public class ReutersClicked extends AppCompatActivity {

    private ListView newsListView;
    private List<News> newsList;

    private String htmlPageUrl = "https://www.reuters.com/news/world"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);

//        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
//        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        //리스트뷰 클
        newsListView = (ListView)findViewById(R.id.NewsListView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ReutersNewsClicked.class);

                intent.putExtra("title",newsList.get(position).getTitle());
                intent.putExtra("url",newsList.get(position).getContext());
                intent.putExtra("date",newsList.get(position).getDate());
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
                String resId = "";

                Document doc = Jsoup.connect(htmlPageUrl).get();

                newsList = new ArrayList<News>();
                //테스트1
                //Elements titles= doc.select("div.news-con h1.tit-news");
                //Elements news= doc.select("div.story-content h3.story-title");
                /*
                Elements news= doc.select("div.story-content");
                for(Element e: news){
                    title=e.select("a").select("h3.story-title").text();
                    url=e.attr("href");
                    String at="at ";
                    date = e.select("span[class=timestamp]").text();
                    at = at.concat(date);
                    newsList.add(new News(title,"reuters",at,url));
                   // htmlContentInStringFormat += e.text().trim() + "\n";
                }*/

                Elements news = doc.select("article[class=story]");
                for(Element e: news){
                    title = e.select("div[class=story-content]").select("a").select("h3").text().trim();
                    url = e.select("div[class=story-content]").attr("href");
                    resId = e.select("div[class=story-photo lazy-photo ]").select("a").select("img").attr("org-src");
                    date = e.select("div[class=story-content]").select("time").select("span").text();
                    String at = "at ";
                    at = at.concat(date);
                    newsList.add(new News(title,"REUTERS",at,url,resId));
                }

//                //테스트2
//                titles= doc.select("div.news-con h2.tit-news");
//
//                Log.d(this.getClass().getName(),"-------------------------------------------------------------");
//                for(Element e: titles){
//                    Log.d(this.getClass().getName(),"title: " + e.text());
//                    htmlContentInStringFormat += e.text().trim() + "\n";
//                }
//
//                //테스트3
//                titles= doc.select("li.section02 div.con h2.news-tl");
//
//                Log.d(this.getClass().getName(),"-------------------------------------------------------------");
//                for(Element e: titles){
//                    Log.d(this.getClass().getName(),"title: " + e.text());
//                    htmlContentInStringFormat += e.text().trim() + "\n";
//                }
//                Log.d(this.getClass().getName(),"-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NewsListAdapter adapter = new NewsListAdapter(getApplicationContext(),newsList);
            newsListView.setAdapter(adapter);
//            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }


}