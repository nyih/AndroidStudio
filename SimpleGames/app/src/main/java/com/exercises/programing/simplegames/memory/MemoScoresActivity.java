package com.exercises.programing.simplegames.memory;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.exercises.programing.simplegames.R;

public class MemoScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_scores);

        SharedPreferences pref =  getSharedPreferences("MEMO_PREF", MODE_PRIVATE);
        String scores1 = pref.getString("MEMO_SCORES1", "NO NAME");
        String scores2 = pref.getString("MEMO_SCORES2", "NO SCORES SAVED");
        // the default value is displayed whenever there is no value defined
        TextView txtview_scores1 = findViewById(R.id.txtview_memoscores1);
        txtview_scores1.setText(scores1);
        TextView txtview_scores2 = findViewById(R.id.txtview_memoscores2);
        txtview_scores2.setText(scores2);
    }
}
