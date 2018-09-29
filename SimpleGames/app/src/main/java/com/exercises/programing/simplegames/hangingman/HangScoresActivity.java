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
        String scores1 = pref.getString("SCORES1", "NO NAME");
        String scores2 = pref.getString("SCORES2", "NO SCORES SAVED");
        // the default value is displayed whenever there is no value defined
        TextView txtview_scores1 = findViewById(R.id.txtview_scores1);
        txtview_scores1.setText(scores1);
        TextView txtview_scores2 = findViewById(R.id.txtview_scores2);
        txtview_scores2.setText(scores2);
    }
}
