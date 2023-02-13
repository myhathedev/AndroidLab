package com.example.androidlabs;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.database.Cursor;
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
        int id;
        String text;
        String urgent;
        public object(int id,String text, String urgent) {
            this.id = id;
            this.text = text;
            this.urgent = urgent;
        }
    }
    static ArrayList<object> elements = new ArrayList<>();
    static ArrayList<String> taskList = new ArrayList<>();
    static ArrayList<Integer> idlist = new ArrayList<>();
    static ArrayList<String> urgentlist = new ArrayList<>();
    DBHelper db ;



//---------------------On create-----------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        resetElements();
        Cursor c = db.getCursor();
        db.printCursor(c);
        //-----------Adapter--------------------
        class MyListAdapter extends BaseAdapter {

            public int getCount() {
                return elements.size();}

            public Object getItem(int position) {
                return position;}

            public long getItemId(int position) {
                return position;}

            public View getView(int position, View old, ViewGroup parent) {
                View newView = old;
                LayoutInflater inflater = getLayoutInflater();
                if (newView == null) {
                    newView = inflater.inflate(R.layout.row_layout, parent, false);
                }
                TextView tView = newView.findViewById(R.id.textGoesHere);
                String setText= String.valueOf(elements.get(position).text);
                tView.setText(setText);
                String urgentValue = elements.get(position).urgent;
                if (urgentValue.equals("true")) {
                    tView.setBackgroundColor(Color.RED);
                    tView.setTextColor(Color.WHITE);
                } else {
                    tView.setBackgroundColor(Color.WHITE);
                    tView.setTextColor(Color.BLACK);
                }
                return newView;
            }
        }
        //----------------Back to main-------------------------------



        ListView todolist = findViewById(R.id.toDoList);
        MyListAdapter myAdapter = new MyListAdapter();
        todolist.setAdapter(myAdapter);
        Button addButton = findViewById(R.id.button);
        Switch sw = findViewById(R.id.urgent);
        sw.setChecked(false);

        //----------------Create new task---------------------------
        addButton.setOnClickListener(click ->
        {
            EditText addElement = findViewById(R.id.typeHere);
            String newTask = addElement.getText().toString();
            String newUrgent = String.valueOf(sw.isChecked());
            addElement.setText(null);
            db.insertTask(newTask,newUrgent);
            resetElements();
            if (sw.isChecked()) {
                sw.setChecked(false);
            }
            myAdapter.notifyDataSetChanged();
        });

        //-----------------Delete Function --------------------
        todolist.setOnItemLongClickListener( (parent, view, position, id) -> {
            View extraViewStuff = getLayoutInflater().inflate(R.layout.row_layout, null) ;
            String extraText = R.string.rowline + String.valueOf(position);
            ((TextView)extraViewStuff.findViewById(R.id.textGoesHere)).setText(extraText);


            AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
            aDialog.setMessage(R.string.delete)
                    .setPositiveButton(R.string.yes, (click, arg) -> {
                        db.deleteTask(elements.get(position).id);
                        resetElements();
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, (click, arg) -> {
                    })
                    .setView(extraViewStuff)
                    .create()
                    .show();
            return true;
        });

        //-------------Refresh-----------------------------------------------
        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(()-> swipeRefresh.setRefreshing(false));

    }

    protected void resetElements() {
        elements.clear();
        taskList = db.getAllTask();
        idlist = db.getAllId();
        urgentlist = db.getAllUrgent();
        int listSize = db.numberOfRows();
        for (int i = 0; i < listSize; i++) {
            object obj = new object(idlist.get(i), taskList.get(i), urgentlist.get(i));
            elements.add(obj);
        }
    }
}