package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Tour_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        //hide top Bar
        getSupportActionBar().hide();
    }
}