package com.example.myapplication.ui.Video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YoutubeListActivity extends AppCompatActivity {
    private ImageView iv_thum1;
    private ImageView iv_thum2;
    private ImageView iv_thum3;
    private ImageView iv_thum4;
    private TextView tv_title1;
    private TextView tv_title2;
    private TextView tv_title3;
    private TextView tv_title4;
    private TextView tv_channel1;
    private TextView tv_channel2;
    private TextView tv_channel3;
    private TextView tv_channel4;
    private Bitmap bitmap;
    private List<Bitmap> thumList;
    private ArrayList<String> str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_list);

        final ArrayList<Bitmap> thumList = new ArrayList<Bitmap>();
        iv_thum1 = findViewById(R.id.iv_thum1);
        iv_thum2 = findViewById(R.id.iv_thum2);
        iv_thum3 = findViewById(R.id.iv_thum3);
        iv_thum4 = findViewById(R.id.iv_thum4);
        tv_title1 = findViewById(R.id.tv_title1);
        tv_title2 = findViewById(R.id.tv_title2);
        tv_title3 = findViewById(R.id.tv_title3);
        tv_title4 = findViewById(R.id.tv_title4);
        tv_channel1 = findViewById(R.id.tv_channel1);
        tv_channel2 = findViewById(R.id.tv_channel2);
        tv_channel3 = findViewById(R.id.tv_channel3);
        tv_channel4 = findViewById(R.id.tv_channel4);
        Intent intent = getIntent();
        str = intent.getStringArrayListExtra("videoList");
        tv_title1.setText(str.get(1));
        tv_channel1.setText(str.get(3));
        tv_title2.setText(str.get(5));
        tv_channel2.setText(str.get(7));
        tv_title3.setText(str.get(9));
        tv_channel3.setText(str.get(11));
        tv_title4.setText(str.get(13));
        tv_channel4.setText(str.get(15));
        Thread ThumnailLoder = new Thread() {
            @Override
            public void run() {
                try{
                    /*
                    URL url = new URL(str.get(2));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    thumList.add(bitmap);
                    /*
                     */
                    for(int i = 2; i < str.size(); i+=4) {
                        URL url = new URL(str.get(i));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        thumList.add(bitmap);
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ThumnailLoder.start();
        try{
            ThumnailLoder.join();
            iv_thum1.setImageBitmap(thumList.get(0));
            iv_thum2.setImageBitmap(thumList.get(1));
            iv_thum3.setImageBitmap(thumList.get(2));
            iv_thum4.setImageBitmap(thumList.get(3));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
