package com.example.cs6011_project.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cs6011_project.R;

public class MainActivity extends AppCompatActivity {

    // The entrance of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // call the activity_main UI
        setContentView(R.layout.activity_main);
    }
}