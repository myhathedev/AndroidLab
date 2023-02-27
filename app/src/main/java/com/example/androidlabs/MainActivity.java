package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //---------------------On create-----------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNameList getNameList = new getNameList();
        getNameList.execute("https://swapi.dev/api/people/?format=json");

    }

    //---------------------get list from API-------------------------------------
    private class getNameList extends AsyncTask<String, Integer, ArrayList<character>> {
        public ArrayList<character> doInBackground(String... args) {
            URL url = null;
            ArrayList<character> nameList = new ArrayList<>();
            try {
                url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //wait for data
                InputStream response = conn.getInputStream();
                //load JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                //create JSON object and the ArrayList
                JSONObject object = new JSONObject(result);
                System.out.println(result + "--------------------------");
                JSONArray results = object.getJSONArray("results");
                System.out.println(results);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject jcharacter = results.getJSONObject(i);
                    String name = jcharacter.getString("name");
                    String height = jcharacter.getString("height");
                    String mass = jcharacter.getString("mass");
                    character character = new character(name, height, mass);
                    nameList.add(character);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("-------------------Failed to connect");
            }
            return nameList;
        }

        public void onProgressUpdate(Integer... values) {
        }

        public void onPostExecute(ArrayList<character> characterList) {
            //   ------------------Adapter----------------------------
            class MyListAdapter extends BaseAdapter {
                public int getCount() {
                    return characterList.size();
                }
                public Object getItem(int position) {
                    return position;
                }
                public long getItemId(int position) {
                    return (long) position;
                }
                public View getView(int position, View old, ViewGroup parent) {
                    View newView = old;
                    LayoutInflater inflater = getLayoutInflater();
                    if (newView == null) {
                        newView = inflater.inflate(R.layout.row_layout, parent, false);
                    }
                    TextView tView = newView.findViewById(R.id.textGoesHere);
                    tView.setText(characterList.get(position).name);
                    return newView;
                }
            }

            ListView nameListView = findViewById(R.id.nameList);
            MyListAdapter myAdapter = new MyListAdapter();
            nameListView.setAdapter(myAdapter);

            nameListView.setOnItemClickListener( (parent, view, position, id) -> {
                Bundle dataToPass = new Bundle();
                dataToPass.putString("name",characterList.get(position).name);
                dataToPass.putString("height",characterList.get(position).height);
                dataToPass.putString("mass",characterList.get(position).mass);
                System.out.println(characterList.get(position).name);

                FrameLayout isTablet = findViewById(R.id.fragmentLocation);

                if (isTablet!=null) {
                    DetailsFragment fragment = new DetailsFragment();
                    fragment.setArguments(dataToPass);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentLocation, fragment).commit();
                }  else {
                    Intent empty = new Intent(MainActivity.this,EmptyActivity.class);
                    empty.putExtras(dataToPass);
                    System.out.println(dataToPass);
                    startActivity(empty);
                }

            });

        }
    }

}