package com.exercises.programing.simplegames.hangingman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exercises.programing.simplegames.R;


public class HangMainActivity extends AppCompatActivity {

    private Button btnSingle, btnMulti, btnScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_main);
        btnSingle = (Button) findViewById(R.id.btn_top);
        btnMulti = (Button) findViewById(R.id.btn_mid);
        btnScore = (Button) findViewById(R.id.btn_bot);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HangMainActivity.this, HangGameActivity.class);
                startActivity(intent);
            }
        });
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HangMainActivity.this, HangMultiplayerActivity.class);
                startActivity(intent);
            }
        });
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HangMainActivity.this, HangScoresActivity.class);
                startActivity(intent);
            }
        });
    }
}
