package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Study extends AppCompatActivity {

    private ArrayList elist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        TextView test=(TextView)findViewById(R.id.test);

        Intent intent=getIntent();
        elist=(ArrayList) intent.getSerializableExtra("elist");

        test.setText(elist.toString());
    }
}
