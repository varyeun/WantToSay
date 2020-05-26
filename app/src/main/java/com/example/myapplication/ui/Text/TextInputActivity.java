package com.example.myapplication.ui.Text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class TextInputActivity extends Activity {
    private Button btn_goToDoActivity;
    private EditText et_text_input;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        btn_goToDoActivity = findViewById(R.id.btn_goToDoActivity);
        et_text_input = findViewById(R.id.et_text_input);

        btn_goToDoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TextDoActivity.class);
                String str = et_text_input.getText().toString();
                intent.putExtra("user_text",str);
                startActivity(intent);
            }
        });
    }
}
