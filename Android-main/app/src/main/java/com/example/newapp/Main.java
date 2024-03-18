package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<Destination> arrayList = dbHelper.getDes();
        DesAdapter desAdapter = new DesAdapter(this,R.layout.list_item,arrayList);
        listView.setAdapter(desAdapter);


    }
}