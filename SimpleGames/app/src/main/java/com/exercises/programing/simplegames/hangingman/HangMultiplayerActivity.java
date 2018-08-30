package com.exercises.programing.simplegames.hangingman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.exercises.programing.simplegames.R;

public class HangMultiplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_muiltplayer);
    }

    public void startMultiActivity(View v){
        EditText edtext = (EditText) findViewById(R.id.edtxt_word);
        String gameWord = edtext.getText().toString();
        edtext.setText("");

        Intent multIntent = new Intent(this, HangMultiActivity.class);
        multIntent.putExtra("WORD_ID", gameWord);
        //the putExtra is what sends the info (gameWord) to the HangMultiActivity
        startActivity(multIntent);

    }
}
