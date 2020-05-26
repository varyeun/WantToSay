package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.PapagoAPI;
import com.example.myapplication.R;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class TextDoActivity extends Activity {
    private TextToSpeech tts;
    private LinearLayout container_text;
    private LinearLayout container_contentbox;
    private ScrollView container_scroll;
    private Animation translateUp;
    private Animation translateDown;
    private Button btn_speakPractice;
    private Button btn_speakListen;
    private TextView tv_translated;
    private TextView tv_text;
    private TextView tv_selected_text;
    private boolean isBoxOpen = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_do);

        tv_translated = findViewById(R.id.tv_translated);
        tv_text = findViewById(R.id.tv_text);
        tv_selected_text = findViewById(R.id.tv_selected_text);
        //container_scroll = findViewById(R.id.container_scroll);
        container_text = findViewById(R.id.container_text);
        container_contentbox = findViewById(R.id.container_contentbox);
        btn_speakPractice = findViewById(R.id.btn_speakPractice);


        Create_TTS();  //tts initializing

        btn_speakListen = findViewById(R.id.btn_speakListen);

        btn_speakListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(tv_selected_text.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    tts.speak(tv_selected_text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        //animation 객체로 만들어야함 Left
        translateUp = AnimationUtils.loadAnimation(this,R.anim.translate_up);
        //animation 객체로 만들어야함 Right
        translateDown = AnimationUtils.loadAnimation(this,R.anim.translate_down);

        // 애니메이션 리스너 등록해야 쓸수 있다.
        SlidingAnimationListener listener = new SlidingAnimationListener();
        translateUp.setAnimationListener(listener);
        translateDown.setAnimationListener(listener);

        container_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxOpen) {
                    container_contentbox.setVisibility(View.INVISIBLE);
                    container_contentbox.startAnimation(translateDown);
                }
            }
        });

        Intent intent = getIntent();
        String str = intent.getStringExtra("user_text");
        create_text(str);

    }

    class SlidingAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {


        }

        @Override
        public void onAnimationEnd(Animation animation) {

            if(isBoxOpen){
                isBoxOpen = false;
            }else{
                isBoxOpen = true;
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public void create_text(String a) {
        String[] tmp = a.split("\\.|\\?");
        String tmp2 = a;
        int start_idx = 0;
        for(int i = 0; i < tmp.length; i++) {
            int t = tmp2.indexOf(".");
            int t2 = tmp2.indexOf("?");
            if(t < t2) {
                if(t > -1) start_idx = t;
                else start_idx = t2;
            }
            else if(t2 < t){
                if(t2 > -1) start_idx = t2;
                else start_idx = t;
            }
            else {
                SpannableString ss1 = new SpannableString(tmp2);
                ss1.setSpan(makeClickableSpan(tmp2), 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_text.append(ss1);
                break;
            }
            String tmp3 = tmp2.substring(0,start_idx+1);

            SpannableString ss1 = new SpannableString(tmp3);
            ss1.setSpan(makeClickableSpan(tmp3), 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_text.append(ss1);
            tmp2 = tmp2.substring(start_idx+1);
        }
        tv_text.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public ClickableSpan makeClickableSpan(final String text) {
        return new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                if(tv_selected_text.getText().toString() != text) {
                    try{
                        String result = new PapagoAPI().execute(text).get();
                        tv_translated.setText(result);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(!isBoxOpen) {
                    container_contentbox.setVisibility(View.VISIBLE);
                    container_contentbox.startAnimation(translateUp);
                }
                tv_selected_text.setText(text);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.BLACK);//set text color
                ds.setTextSize(50);
                ds.setUnderlineText(false); // set to false to remove underline
            }
        };
    }

    public void Create_TTS(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }
}
