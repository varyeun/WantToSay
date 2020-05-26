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


public class KoreaHeraldClicked extends AppCompatActivity {

    private ListView newsListView;
    private List<News> newsList;

    private String htmlPageUrl = "http://www.koreaherald.com/list.php?ct=020000000000"; //파싱할 홈페이지의 URL주소

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);
/*
        //뉴스 불러오기 버튼 활성화
        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println( (cnt+1) +"번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
*/
        //리스트뷰 클
        newsListView = (ListView)findViewById(R.id.NewsListView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), KoreaHeraldNewsClicked.class);

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
                String resId="";

                Document doc = Jsoup.connect(htmlPageUrl).get();

                newsList = new ArrayList<News>();

                Elements news= doc.select("ul[class=main_sec_li main_sec_li_only]").select("li a");

                for(Element e: news){
                    resId = e.select("div[class=main_l_img]").select("img").attr("src");
                    title=e.select("div.main_l_t1").text();
                    date = e.select("div[class=main_l_t2]").select("span").text();
                    String on = "on ";
                    on = on.concat(date);
                    url=e.attr("href");
                    newsList.add(new News(title,"The Korea Herald",on,url,resId));
                    // htmlContentInStringFormat += e.text().trim() + "\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //View footer = getLayoutInflater().inflate(R.layout.listview_footer,null,false);
            //newsListView.addFooterView(footer);
            final NewsListAdapter adapter = new NewsListAdapter(getApplicationContext(),newsList);
            newsListView.setAdapter(adapter);
/*
            Button button = (Button) footer.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

 */
        }
    }


}