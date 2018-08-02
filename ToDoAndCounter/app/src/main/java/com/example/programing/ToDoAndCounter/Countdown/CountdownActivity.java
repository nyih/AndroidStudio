package com.example.programing.ToDoAndCounter.Countdown;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programing.ToDoAndCounter.R;

import java.util.Locale;

public class CountdownActivity extends AppCompatActivity {

    private EditText setTimeInput;
    private TextView txtCountdown;
    private Button btnSet;
    private Button btnStartPause;
    private Button btnReset;
    private CountDownTimer countTimer;
    private boolean timeRunning;
    private long setTimeMillis;
    private long timeLeft;
    private long timeEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        setTimeInput = findViewById(R.id.edit_time);
        txtCountdown = findViewById(R.id.txt_countdown);
        btnSet = findViewById(R.id.btn_set);
        btnReset = findViewById(R.id.btn_reset);
        btnStartPause = findViewById(R.id.btn_startPause);

        btnSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String input=setTimeInput.getText().toString();
                if(input.length()==0){
                    Toast.makeText(CountdownActivity.this,"Please enter a number",Toast.LENGTH_LONG).show();
                    return;
                }
                long millisInput=Long.parseLong(input)*60000;
                if(millisInput==0){
                    Toast.makeText(CountdownActivity.this,"Please enter a positive number",Toast.LENGTH_LONG).show();
                    return;
                }
                setTime(millisInput);
                setTimeInput.setText("");
            }
        });

        btnStartPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (timeRunning) {
                    pauseCounter();
                } else {
                    startCounter();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCounter();
            }
        });
    }

    private void setTime(long milliseconds){
        setTimeMillis=milliseconds;
        resetCounter();
        closeKeyBoard();
    }

        private void startCounter () {
            timeEnd=System.currentTimeMillis()+timeLeft;
            countTimer = new CountDownTimer(timeLeft,1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft=millisUntilFinished;
                    updateCountText();
                }

                @Override
                public void onFinish() {
                    timeRunning=false;
                    //btnStartPause.setText("start");
                    updateElements();
                    //btnStartPause.setVisibility(View.INVISIBLE);
                    //btnReset.setVisibility(View.VISIBLE);
                }

            }.start();
            timeRunning=true;
            //btnStartPause.setText("pause");
            updateElements(); //btnReset.setVisibility(View.INVISIBLE);
        }

        private void pauseCounter () {
            countTimer.cancel();
            timeRunning=false;
            //btnStartPause.setText("start");
            updateElements(); //btnReset.setVisibility(View.VISIBLE);
        }

        private void resetCounter () {
            timeLeft= setTimeMillis;
            updateCountText();
            updateElements();
            //btnReset.setVisibility(View.INVISIBLE);
            //btnStartPause.setVisibility(View.VISIBLE);
        }

        private void updateCountText () {
            int hours=(int)(timeLeft/1000)/3600;
            int minutes=(int)((timeLeft/1000)%3600)/60;
            int seconds=(int)(timeLeft/1000)%60;

            String timeFormated;

            if(hours>0){

                timeFormated=String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
            }
            else {
                timeFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            }
            txtCountdown.setText(timeFormated);
        }

        private void updateElements(){
            if(timeRunning){
                setTimeInput.setVisibility(View.INVISIBLE);
                btnSet.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnStartPause.setText("pause");
            }
            else{
                setTimeInput.setVisibility(View.VISIBLE);
                btnSet.setVisibility(View.VISIBLE);
                btnStartPause.setText("start");
                if(timeLeft<1000){
                    btnStartPause.setVisibility(View.INVISIBLE);
                }
                else{
                    btnStartPause.setVisibility(View.VISIBLE);
                }
                if(timeLeft< setTimeMillis){
                    btnReset.setVisibility(View.VISIBLE);
                }
                else{
                    btnReset.setVisibility(View.INVISIBLE);
                }
            }
        }

    private void closeKeyBoard(){
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();

        editor.putLong("setTimeMillis",setTimeMillis);
        editor.putLong("millisLeft",timeLeft);
        editor.putBoolean("timeRunning", timeRunning);
        editor.putLong("timeEnd",timeEnd);
        editor.apply();

        if(countTimer!=null){
            countTimer.cancel();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);

        setTimeMillis=prefs.getLong("setTimeMillis",600000);
        timeLeft=prefs.getLong("millisLeft", setTimeMillis);
        timeRunning=prefs.getBoolean("timeRunning",false);

        updateCountText();
        updateElements();

        if(timeRunning){
            timeEnd=prefs.getLong("timeEnd",0);
            timeLeft=timeEnd-System.currentTimeMillis();
            if(timeLeft<0){
                timeLeft=0;
                timeRunning=false;
                updateElements();
                updateCountText();
            }
            else{
                startCounter();
            }
        }
    }
}
