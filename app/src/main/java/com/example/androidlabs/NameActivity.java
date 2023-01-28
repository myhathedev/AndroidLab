package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent intent = getIntent();
        String name = "Welcome, " + intent.getStringExtra("welcome");
        TextView welcome = findViewById(R.id.textView2);
        welcome.setText(name);

        Button back_button = findViewById(R.id.button2);
        back_button.setOnClickListener( click -> {
            setResult(0);
            finish();
                });

        Button ty_button = findViewById(R.id.button3);
        ty_button.setOnClickListener( click -> {
            setResult(1);
            finish();
        });
    }
}