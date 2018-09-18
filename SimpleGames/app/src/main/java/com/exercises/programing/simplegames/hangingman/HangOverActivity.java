package com.exercises.programing.simplegames.hangingman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercises.programing.simplegames.R;

public class HangOverActivity extends AppCompatActivity {

    int pontuation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_over);

        int points = getIntent().getIntExtra("POINTS_ID", 0);
        TextView txtview = (TextView) findViewById(R.id.txtview_points);
        txtview.setText(String.valueOf(points)+" Points");

        pontuation = points;
    }

    public void saveScore(View v) {
        //the onClick in the xml file bring us to this method
        SharedPreferences pref = getSharedPreferences("HANG_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        EditText edtxt = findViewById(R.id.edtxt_name);
        String name = edtxt.getText().toString();
        String prevScores = pref.getString("SCORES", "");

        editor.putString("SCORES", name+" "+pontuation+" POINTS \n"+prevScores);
        editor.apply();
        //apply is same as commit but do it in the background instead of immediately

        finish();
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("pontuation", pontuation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pontuation = savedInstanceState.getInt("pontuation");     //works

    } //closes onRestore
}
