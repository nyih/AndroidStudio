package com.exercises.programing.simplegames.memory;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.exercises.programing.simplegames.R;
import java.text.MessageFormat;

public class MemoTimer extends CountDownTimer {
    private TextView timerText;
    private MemoGameActivity theActivity;

    public MemoTimer(long numOfSeconds, long interval, TextView text, MemoGameActivity theActivity){
        super(numOfSeconds,interval);
        this.timerText = text;
        this.theActivity = theActivity;
    }

    // updates the timer text by the timer
    @TargetApi(Build.VERSION_CODES.N)
    public void onTick(long x){
        String timeLeft = String.valueOf(x/1000);
        timerText.setText(timeLeft);
        }

    public void onFinish(){
        timerText.setText("Game Over");
        theActivity.timeOut();
    }
}
