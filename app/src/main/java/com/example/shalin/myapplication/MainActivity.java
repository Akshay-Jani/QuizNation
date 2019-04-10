package com.example.shalin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView practice, test, flashcard, scorecard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        practice = findViewById(R.id.practice);
        test = findViewById(R.id.test);
        flashcard = findViewById(R.id.flash_cards);
        scorecard = findViewById(R.id.scorecard);

        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,Practice.class);
                startActivity(p);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this,Test_page.class);
                startActivity(t);
            }
        });

        flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(MainActivity.this,Flashcard.class);
                startActivity(f);
            }
        });

        scorecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this,ResultAdapter.class);
                startActivity(s);
            }
        });

    }
}

