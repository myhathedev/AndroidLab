package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DadJoke extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener
      {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,myToolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

    }

          @Override
          public boolean onNavigationItemSelected(MenuItem item) {
              //do something when the item is clicked
              int id = item.getItemId();
              if (id == R.id.home) {
                  Intent intent = new Intent(DadJoke.this, MainActivity.class);
                  startActivity(intent);
              } else if (id == R.id.joke) {
                  return true;
              } else if (id == R.id.exit) {
                  finishAffinity();
              }
              return true;
          }

          @Override
          public boolean onOptionsItemSelected(MenuItem item) {
              String message = null;
              //do something when the item is clicked
              int id = item.getItemId();
              if (id == R.id.item1) {
                  message = "You clicked on Item 1";
              } else if (id == R.id.item2) {
                  message = "You clicked on Item 2";
              } else if (id == R.id.item3) {
                  message = "You clicked on Item 3";
              }
              Toast.makeText(this, message, Toast.LENGTH_LONG).show();
              return true;
          }

          @Override
          public boolean onCreateOptionsMenu(Menu menu) {
              MenuInflater inflater = getMenuInflater();
              inflater.inflate(R.menu.main_activity_actions, menu);
              return true;
          }
    }
