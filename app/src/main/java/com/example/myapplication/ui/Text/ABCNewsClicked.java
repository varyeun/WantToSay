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

public class ABCNewsClicked extends Activity {

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
        ABCNewsClicked.JsoupAsyncTask jsoupAsyncTask = new ABCNewsClicked.JsoupAsyncTask();
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
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements news= doc.select("article[class=Article__Content story] p");
//                Elements news= doc.select("section.Article__Column Article__Column--main");

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
    @Override
    protected void onStart() {
        super.onStart();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }
}
