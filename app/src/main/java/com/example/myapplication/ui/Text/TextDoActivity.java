package com.example.myapplication.ui.Text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class TextDoActivity extends AppCompatActivity {
    SentenceAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_do);

        Intent intent = getIntent();
        String str = intent.getStringExtra("en");
        String[] text = create_text(str);

        ListView sentenceView = (ListView) findViewById(R.id.sentenceView);
        adapter = new SentenceAdapter();
        adapter.addItem(new Sentence(intent.getStringExtra("title"),"제목"));
        for(String s: text){
            adapter.addItem(new Sentence(s,"본문"));
        }
        sentenceView.setAdapter(adapter);

    }

    public String[] create_text(String a) {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        String source = a;
        ArrayList<String> tmp = new ArrayList<String>();
        iterator.setText(source);
        int start = iterator.first();
        iterator.next();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            tmp.add(source.substring(start,end));
        }
        String[] text = new String[tmp.size()];
        text = tmp.toArray(text);
        return text;
    }

    class SentenceAdapter extends BaseAdapter {
        ArrayList<Sentence> items = new ArrayList<Sentence>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Sentence item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SentenceView view = null;
            if (convertView == null) {
                view = new SentenceView(getApplicationContext());
            } else {
                view = (SentenceView) convertView;
            }
            //convertView is for memory
            Sentence item = items.get(position);
            view.setEnglish(item.getEnglish());
            view.setKorean(item.getKorean());
            return view;
        }
    }
}
