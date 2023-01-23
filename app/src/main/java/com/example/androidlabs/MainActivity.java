package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_linear);

            TextView title = findViewById(R.id.text);

            EditText edittext = findViewById(R.id.textView);

            final Button btn = findViewById(R.id.button2);
            btn.setOnClickListener((click) -> {
                title.setText(edittext.getText().toString());
                Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_LONG).show();
            });

            CheckBox cb = findViewById(R.id.checkBox);
            cb.setOnCheckedChangeListener((cpb, b) -> Snackbar.make(findViewById(R.id.layout), setOnOff(b), Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, click -> cpb.setChecked(!b))
                    .show());


        }

    public int setOnOff(boolean b) {
        if (b) {
            return R.string.seton;
        } else {
            return R.string.setoff;
        }
    }
    }