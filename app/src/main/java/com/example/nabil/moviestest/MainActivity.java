package com.example.nabil.moviestest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button choiceButtonInTheaters;
    Button choiceButtonComingSoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choiceButtonComingSoon = (Button) findViewById(R.id.option_coming_soon);
        choiceButtonInTheaters = (Button) findViewById(R.id.option_in_theaters);
        createOnClickListeners();
    }

    private void createOnClickListeners() {


        choiceButtonInTheaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char choice = 1;
                Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
                intent.putExtra("choice", choice);
                MainActivity.this.startActivity(intent);
            }
        });

        choiceButtonComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char choice = 0;
                Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
                intent.putExtra("choice", choice);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public void displayLocationsOnMap(View view){

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(intent);
    }


}
