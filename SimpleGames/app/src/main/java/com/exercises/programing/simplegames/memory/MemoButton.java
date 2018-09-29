package com.exercises.programing.simplegames.memory;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.widget.GridLayout;
import com.exercises.programing.simplegames.R;

public class MemoButton extends AppCompatButton {

    protected int row, col, frontImgID;
    protected boolean isFlipped = false;
    protected boolean isMatched = false;
    protected Drawable front, back;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MemoButton(Context context, int row, int col, int frontImageID ){
        super(context);

        this.row = row;
        this.col = col;
        this.frontImgID = frontImageID;

        front = context.getDrawable(frontImageID);
        back = context.getDrawable(R.drawable.ic_memo_question3);

        setBackground(back);
        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(
                GridLayout.spec(row), GridLayout.spec(col));
////////////////TRY TO SEE IF IT'S POSSIBLE TO NOT GO BIGGER THAN SCREEN
        tempParams.width = (int) getResources().getDisplayMetrics().density * 90;
        //multiplying makes the images bigger, but if not multiply, images don't show
        //because the metrics are too small
        tempParams.height = (int) getResources().getDisplayMetrics().density * 90;
        setLayoutParams(tempParams);
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontImgID() {
        return frontImgID;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void flip(){
        if(isMatched){
            return;
        }
        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        }
        else{
            setBackground(front);
            isFlipped = true;
        }
    }
}
