package com.example.myapplication.ui.Text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class SelfInput extends AppCompatActivity {

    private Button input;
    private EditText title;
    private EditText context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfinput);

        title = (EditText) findViewById(R.id.title);
        context = (EditText) findViewById(R.id.context);
        input = (Button) findViewById(R.id.input);
        input.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //SubActivity로 가는 인텐트를 생성
                        Intent intent = new Intent(getApplicationContext(), NewsClicked.class);
                        intent.putExtra("title",title.getText().toString());
                        intent.putExtra("context",context.getText().toString());

                        //액티비티 시작!
                        startActivity(intent);
                    }
                }
        );
    }
}
