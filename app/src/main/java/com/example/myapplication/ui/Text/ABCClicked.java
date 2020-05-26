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


public class ABCClicked extends AppCompatActivity {

    private ListView newsListView;
    private List<News> newsList;

    private String htmlPageUrl = "https://abcnews.go.com/"; //파싱할 홈페이지의 URL주소

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
                Intent intent = new Intent(getApplicationContext(), ABCNewsClicked.class);

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
                String date="";
                String resId="";

                Document doc = Jsoup.connect(htmlPageUrl).get();

                newsList = new ArrayList<News>();

                Elements news= doc.select("div.headlines-li-div h1 a");

                for(Element e: news){
                    title=e.select("a").text();
                    url=e.attr("href");
                    resId = "";
                    newsList.add(new News(title,"abc NEWS",date,url,resId));
                    // htmlContentInStringFormat += e.text().trim() + "\n";
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