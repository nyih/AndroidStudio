package com.exercises.programing.simplegames.memory;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.exercises.programing.simplegames.R;
import java.util.Random;

public class MemoGameActivity extends AppCompatActivity implements  View.OnClickListener {
    /////////////////TRY TO DO THIS WITH LINEAR LAYOUT INSTEAD OF GRID!!
    /// (each linear is a row that we populate with images)
    private int numElements, numSeconds, numPairs, boardSize, numRow, numCol;
    int punctuation = 0;
    private int[] timerSeconds = {25, 45, 95};
    private MemoButton selectBtn1, selectBtn2;
    private  MemoButton[] allBtn;
    private int [] gameImgs, ImgsPositions; //random all the location
    private boolean isBusy = false;
// for the sake of differentiating intent variables from other variables, intent's are in UPPERCASE:
    final String ROW_LVL = "ROW_LVL", COL_LVL = "COL_LVL", FOR_TIMER = "FOR_TIMER";
    private TextView timer_txt, pointScore;
    private MemoTimer timer;
    GridLayout gridLay;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_game);

        setTime();
        dataForTimer(numSeconds + 1, timer_txt);
        gridLay = (GridLayout)findViewById(R.id.gridlay_allimg);
        pointScore = findViewById(R.id.txtview_memoscore);

        boardSize = getIntent().getIntExtra(ROW_LVL, 0);
        numCol = getIntent().getIntExtra(COL_LVL, 0);
        Log.d("MYLOG","COL_LVL = "+numCol);
        numRow = boardSize;

        if (numCol == 0) {
            numPairs = numRow * (numRow / 2);
            //rows * columns (in this case is the same number) / 2 (we are pairing images)
            this.numElements = boardSize * numRow;
        }
        else {
            numPairs = (numCol*numRow)/2;
            this.numElements = numCol * numRow;
        }
        this.allBtn = new MemoButton[numElements];
        this.gameImgs = new int [numElements /2];
        Log.d("MYLOG","numElements = "+ numElements);
        putGameImgs();

        this.ImgsPositions = new int [numElements];
        shuffleGameImgs();
        if (numCol == 0) {
            createImgOnGrid(numRow, boardSize, gridLay);
        }
        else {
            createImgOnGrid(numRow, numCol, gridLay);
            Log.d("MYLOG","numRow = "+numRow);
            Log.d("MYLOG","numCol = "+numCol);
        }
////////////////////////////////////////////////////////////////////////
        //// To try later: save scores with date and time instead
        //// of requesting player name
////////////////////////////////////////////////////////////////////////
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // create the editText that will receive input
                final EditText itemEditText = new EditText(MemoGameActivity.this);
                itemEditText.setTextColor(getResources().getColor
                        (R.color.colorMint, getResources().newTheme()));
                // create popup alert
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder
                        (MemoGameActivity.this, R.style.AlertDialogStyle)
                        .setTitle("Save Score")
                        .setMessage("Insert your name");
                // it's possible to set the information inside the alert by not closing the statement
                // until the last set is set or... by using the instance name like shown above
                //set the view to the new one we have just created
                alertBuilder.setView(itemEditText);
                // create the buttons
                alertBuilder
                        .setCancelable(false)
                        .setPositiveButton("save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //CREATE SHARED PREFERENCES
                                        SharedPreferences pref = getSharedPreferences
                                                ("MEMO_PREF", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        // get user input and set it to result
                                        String name = itemEditText.getText().toString();
                                        String prevScores = pref.getString
                                                ("MEMO_SCORES1", "");
                                        String prevScores2 = pref.getString
                                                ("MEMO_SCORES2", "");

                                        editor.putString("MEMO_SCORES1", name+"\n"+prevScores);
                                        editor.putString("MEMO_SCORES2", punctuation+"\n"+prevScores2);
                                        Log.d("MYLOG","punctuation is: "+ punctuation);
                                        editor.apply();
                                        //apply is same as commit but do it in the background instead of immediately
                                        finish();
                                    }
                                })
                        .setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                alertBuilder.create();
                // show it
                alertBuilder.show();
            }
        });
    } // closes onCreate

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        // if the timer is not canceled here the player gets a toast
        // saying time up in the menu or when it's not supposed to
    }

    //The method bellow sets the timer to be one of the times in the array according with the level
    private void setTime() {
        timer_txt = findViewById(R.id.txtview_memotimer);
        int level = getIntent().getIntExtra(FOR_TIMER, 0);
        numSeconds = timerSeconds[level];
    }

    private void dataForTimer(long timeLeft, TextView timerText) {
        timer = new MemoTimer(timeLeft * 1000, 1000, timerText, this);
        //Android timer by default uses milliseconds, which means that 1000 = 1 second
        timer.start();
    }
//Bellow it's where the cards are created inside the grid layout
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createImgOnGrid(int numRow, int numCol, GridLayout importedGrid){
        for (int row = 0; row < numRow ; row++){
            for(int col = 0 ; col <numCol ; col++){
                MemoButton tempButton = new MemoButton(
                        this,row,col, gameImgs[
                                ImgsPositions[row *numCol +col]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                allBtn[row * numCol +col] = tempButton;
                importedGrid.addView(tempButton);
            }
        }
    }

    protected void shuffleGameImgs(){
        Random rand = new Random();
        for (int i = 0; i < numElements; i++ ){
            this.ImgsPositions[i] = i % (numElements /2);
        }
        for (int i = 0; i < numElements; i++ ){//swap location
            int temp = this.ImgsPositions[i];
///////////////////CHANGE numElements IF CHANGING THE ROW_LVL NUMBER/////////////////////////////
            if(numElements == 6){
///////////////////need to be changed to row * column (2*2=4; 3*3=9; 4*4=16; ////////////////////
                int swapIndex = rand.nextInt(6);
                ImgsPositions[i] = ImgsPositions[swapIndex];
                ImgsPositions[swapIndex] = temp;
            }else if(numElements == 16){
                int swapIndex = rand.nextInt(16);
                ImgsPositions[i] = ImgsPositions[swapIndex];
                ImgsPositions[swapIndex] = temp;
            }else{
                int swapIndex = rand.nextInt(24);
                ImgsPositions[i] = ImgsPositions[swapIndex];
                ImgsPositions[swapIndex] = temp;
            }
        }
    } // closes shuffleGameImgs

    private void putGameImgs() {
        if (numPairs >8){
            toastMessage("Please scroll up and down and sideways to make sure you see all the game", Toast.LENGTH_LONG);
        }
        for (int i = 0, j = 1; i< numPairs; i++, j++) {
            String imgID = "ic_memo" + j;
            Log.d("MYLOG", "i = " + i + " j = " + j);
            int resID = getResources().getIdentifier(imgID, "drawable", getPackageName());
            this.gameImgs[i] = (resID);
            //resID and R.drawable.___ are the same, as demonstrated by the 2 Log.d bellow
            Log.d("MYLOG", "R.drawable? " + resID);
            Log.d("MYLOG", "R.drawable.ic_memo1? " + R.drawable.ic_memo1);
        }
    }

    private boolean checkIfDone() {
        for (int i = 0; i < numElements; i++) {
            if (allBtn[i].isEnabled()) {
                return false;
                // this method is a boolean so it need to return true or false,
                // it's also what we use on onClick to check if the player got all the pairs
            }
        }
        return true;
    }

    //When time is out, this is our game over
    @TargetApi(Build.VERSION_CODES.M)
    public void timeOut() {
        toastMessage("Time up!", Toast.LENGTH_LONG);
// This cleans the grid layout so the player wont be able to do anything
// (another option it would be to freeze the cards, not allowing clicks)
        gridLay.removeAllViewsInLayout();
        // create a button and set all of its properties
        final Button btnrestart = new Button(MemoGameActivity.this);
        btnrestart.setText("Restart game");
        btnrestart.setTextSize(20);
        btnrestart.setAllCaps(false);
        btnrestart.setTextColor(getResources().getColor
                (R.color.colorPrimaryLight, getResources().newTheme()));
        gridLay.addView(btnrestart);
        btnrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
            }
        });
    }

    //This onClick is handling all clicks in the cards
    @Override
    public void onClick(View view) {
        if(isBusy){
            return; // it prevents the game from having more than 2 cards displayed at all times
            ///////////////////////////////////////////////////////////////////////////////////////
            // why do I need to have an empty return?
            // I suppose the empty return work like the break inside a loop,
            // preventing the rest of the code in the method to run
            // Which is also the reason this needs to be the 1st if of the onClick
            ///////////////////////////////////////////////////////////////////////////////////////
        }
        MemoButton button = (MemoButton) view;
        if(selectBtn1 == null){
            // if the selected button is not empty
            selectBtn1 = button;
            // the selected button is the view
            selectBtn1.flip();
            // call the method flip() inside the MemoButton class to show the card
        }
        if(selectBtn1.getId() == button.getId()){
            // if the selected button is clicked twice, return (do nothing)
            return;
        }
        if(selectBtn1.getFrontImgID() == button.getFrontImgID()){
            button.flip();
            button.setMatched(true);
            selectBtn1.setMatched(true);
            selectBtn1.setEnabled(false);
            button.setEnabled(false);
            selectBtn1 = null;
            punctuation++;
            Log.d("MYLOG", "Score is: "+punctuation);
            pointScore.setText(punctuation +" points");
// Check if the game is over because the player got all the pairs
            if(checkIfDone()){
                MemoGameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toastMessage("You won!", Toast.LENGTH_SHORT);
// Give different bonus points if the player won in less than half of the time or more than half
                       int timeLeft = Integer.valueOf(timer_txt.getText().toString());
                        Log.d("MYLOG", "timeLeft is: "+timer_txt.getText().toString());
                        if (timeLeft >= (numSeconds/2)) {
                            punctuation += timeLeft ; // Bonus points for winning
                            Log.d("MYLOG", "Score is: "+punctuation);
                            pointScore.setText(punctuation +" points");
                        }
                        else {
                            punctuation += (numSeconds/5); // Bonus points for winning
                            Log.d("MYLOG", "Score is: "+punctuation);
                            pointScore.setText(punctuation +" points");
                        }
                    } // closes run
                }); // closes runnable
                timer.cancel();
                clearScreen();
            } // closes if checkIfDone

        }else{// cards are not the same
            selectBtn2 = button;
            selectBtn2.flip();
            isBusy = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectBtn2.flip();
                    selectBtn1.flip();
                    selectBtn1 = null;
                    selectBtn2 = null;
                    isBusy = false;
                }
            },1000); //closes Handler
        } // closes else
    } // closes onClic

// This method clears the grid layout and recreate the game inside it
    public void clearScreen() {
        toastMessage("Shuffling the cards", Toast.LENGTH_LONG);
        Handler tempHandler = new Handler();
        tempHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gridLay.removeAllViewsInLayout();
                setTime();
                dataForTimer(numSeconds + 1, timer_txt);
                timer.start();
                pointScore.setText(punctuation +" points");
                putGameImgs();
                shuffleGameImgs();
                if (numCol == 0) {
                    createImgOnGrid(numRow, boardSize, gridLay);
                }
                else {
                    createImgOnGrid(numRow, numCol, gridLay);
                    Log.d("MYLOG","numRow = "+numRow);
                    Log.d("MYLOG","numCol = "+numCol);
                }
            }
        }, 4000);
    }

    //This method personalizes the Toast message
    @TargetApi(Build.VERSION_CODES.M)
    private void toastMessage(String message, int duration){
        Toast toast = Toast.makeText(this, message, duration);
        View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(getResources().getColor
                (R.color.colorPrimaryOld, getResources().newTheme()), PorterDuff.Mode.SRC_IN);
//Gets the TextView from the Toast so it can be edited
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
//The most important part, display the Toast
        toast.show();
    }


    ////////////////////////////////////////////////////////////////
    ////////// Try to figure a way to use the onSaveInstanceState
    ////////// and onRestoreInstanceState with the cards
    ////////// MAYBE TRY TO SAVE THE BOARD MATRIX? 
}