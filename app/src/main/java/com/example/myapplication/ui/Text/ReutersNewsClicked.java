package com.example.myapplication.ui.Text;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReutersNewsClicked extends Activity {

    private String url="";
    private String body="";
    private TextView context;
    private Button study;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_item);


        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        ReutersNewsClicked.JsoupAsyncTask jsoupAsyncTask = new ReutersNewsClicked.JsoupAsyncTask();
        jsoupAsyncTask.execute();

        TextView title =(TextView)findViewById(R.id.title);
        context =(TextView)findViewById(R.id.context);
        title.setText(intent.getStringExtra("title"));

        study = (Button) findViewById(R.id.study);
        study.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TextDoActivity.class);
                        intent.putExtra("en", context.getText().toString());
                        startActivity(intent);
                    }
                }
        );



//        Document doc = null;
//        try {
//            doc = Jsoup.connect("https://www.reuters.com/news/world").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Elements news= doc.select("div.StandardArticleBody_body p");
//
//        for(Element e: news){
//            body += e.text().trim()+"\n";
//        }

    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://www.reuters.com"+url).get();
                Elements news= doc.select("div.StandardArticleBody_body");

                for(Element e: news){
                    body += e.text().trim()+"\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            context.setText(body);
        }
    }
}
