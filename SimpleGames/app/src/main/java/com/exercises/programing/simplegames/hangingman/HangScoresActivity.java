package com.exercises.programing.simplegames.hangingman;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.exercises.programing.simplegames.R;

public class HangScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_scores);

        SharedPreferences pref =  getSharedPreferences("HANG_PREF", MODE_PRIVATE);
        String scores = pref.getString("SCORES", "NO SCORES SAVED");
        // the default value is displayed whenever there is no value defined
        TextView txtview_scores = findViewById(R.id.txtview_scores);
        txtview_scores.setText(scores);
    }
}
