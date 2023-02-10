package com.example.androidlabs;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static class object {
        String text;
        String urgent;
        public object(String text, String urgent) {
            this.text = text;
            this.urgent = urgent;
        }
    }
    ArrayList<object> elements = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        class MyListAdapter extends BaseAdapter {

            public int getCount() {
                return elements.size();}

            public Object getItem(int position) {
                return position;}

            public long getItemId(int position) {
                return (long) position;}

            public View getView(int position, View old, ViewGroup parent) {
                View newView = old;
                LayoutInflater inflater = getLayoutInflater();
                if (newView == null) {
                    newView = inflater.inflate(R.layout.row_layout, parent, false);
                }
                TextView tView = newView.findViewById(R.id.textGoesHere);
                tView.setText(elements.get(position).text);
                if (elements.get(position).urgent=="true") {
                    tView.setBackgroundColor(Color.RED);
                    tView.setTextColor(Color.WHITE);
                } else {
                    tView.setBackgroundColor(Color.WHITE);
                    tView.setTextColor(Color.BLACK);
                }
                return newView;
            }
        }

        ListView todolist = findViewById(R.id.toDoList);
        MyListAdapter myAdapter = new MyListAdapter();
        todolist.setAdapter(myAdapter);


        todolist.setOnItemLongClickListener( (parent, view, position, id) -> {
            View extraViewStuff = getLayoutInflater().inflate(R.layout.row_layout, null) ;
            String extraText = R.string.rowline + String.valueOf(position);
            ((TextView)extraViewStuff.findViewById(R.id.textGoesHere)).setText(extraText);

            AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
            aDialog.setMessage(R.string.delete)
                    .setPositiveButton(R.string.yes, (click, arg) -> {
                        elements.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, (click, arg) -> {
                    })
                    .setView(extraViewStuff)
                    .create()
                    .show();
            return true;
        });


        Button addButton = findViewById(R.id.button);
        Switch sw = findViewById(R.id.urgent);
        sw.setChecked(false);
        addButton.setOnClickListener(click ->
        {
            EditText addElement = findViewById(R.id.typeHere);
            object obj = new object(addElement.getText().toString(),String.valueOf(sw.isChecked()));
            if (sw.isChecked()) {
                sw.setChecked(false);
            }
            elements.add(obj);
            addElement.setText(null);
            myAdapter.notifyDataSetChanged();
        });

        SwipeRefreshLayout swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(()-> swiperefresh.setRefreshing(false));

    }
}