package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    DatabaseHelper dbHelper;
    TextView eUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        dbHelper = new DatabaseHelper(this);
        eUsername = findViewById(R.id.name);
        eUsername.setText(dbHelper.getUser());

    }
}   