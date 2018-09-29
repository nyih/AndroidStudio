package com.exercises.programing.simplegames.memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.exercises.programing.simplegames.R;

public class MemoMainActivity extends AppCompatActivity {

    final String ROW_LVL = "ROW_LVL";
    final String COL_LVL = "COL_LVL";
    final String FOR_TIMER = "FOR_TIMER";

    final int EASY = 0;
    final int MEDIUM = 1;
    final int HARD = 2;
    // The levels are set as 0, 1 and 2 because we call them in the MemoGameActivity
    // to give the position in the timer array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        Button btnEasy = (Button) findViewById(R.id.btn_easy);
        Button btnMedium = (Button)findViewById(R.id.btn_medium);
        Button btnHard = (Button)findViewById(R.id.btn_hard);
        Button btnScores = findViewById(R.id.btn_scores);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoMainActivity.this,
                        MemoGameActivity.class);
                intent.putExtra(ROW_LVL,3);
                intent.putExtra(COL_LVL,2);
                //value of level need to be pair, because in memory games we try to find pairs (/2)
                //unless we start to make this with different numbers for row and col (ex: 4x3)
                // add at MemoGameActivity -> if we get nothing from the intent (value=0), col = row.
                intent.putExtra(FOR_TIMER,EASY);
                startActivity(intent);
            }
        });
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoMainActivity.this,
                        MemoGameActivity.class);
                intent.putExtra(ROW_LVL,4);
                intent.putExtra(FOR_TIMER,MEDIUM);
                startActivity(intent);
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoMainActivity.
                        this,MemoGameActivity.class);
                intent.putExtra(ROW_LVL,6);
                intent.putExtra(FOR_TIMER,HARD);
                startActivity(intent);
            }
        });

        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoMainActivity.
                        this,MemoScoresActivity.class);
                startActivity(intent);
            }
        });
    } // closes onCreate
} // closes Activity
