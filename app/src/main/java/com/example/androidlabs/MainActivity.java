package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String welcome = sh.getString("welcome", "");

// We can then use the data
        EditText editText = findViewById(R.id.editText);
        editText.setText(welcome);
        Button button = findViewById(R.id.button);

        button.setOnClickListener( click -> {
            Intent nextPage = new Intent(this,NameActivity.class);
            String content = editText.getText().toString();
            nextPage.putExtra("welcome",content);
            startActivityForResult(nextPage,0);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        EditText editText = findViewById(R.id.editText);
        myEdit.putString("welcome", editText.getText().toString());
        myEdit.commit();
    }
    }