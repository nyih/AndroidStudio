package com.exercises.programing.simplegames.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exercises.programing.simplegames.R;

import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class TicMainActivity extends AppCompatActivity implements View.OnClickListener{

    // Step 2.1 - Declare all your variables
    protected Button[][] buttons = new Button[3][3];
    protected boolean p1_turn = true;
    private int roundCount;
    private int p1_points;
    private int p2_points;
    private TextView txtView_p1;
    private TextView txtView_p2;

    // Step 7 - Declare your new variable for the AI implementation
    public RadioButton radio_AInope, radio_AIeasy, radio_AIhard;
    public RadioGroup radiog;
    public Button btnReset, btnGo;
    public TicAIEasy aiEasy; // For when not using minimax
    public TicAIPerfect aiHard; // For when using minimax
    public boolean startGame = false;

    // Step 2.2 - Implement your buttons on the onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_main);

        txtView_p1 = findViewById(R.id.txtView_p1);
        txtView_p2 = findViewById(R.id.txtView_p2);
        aiEasy = new TicAIEasy();       // Step 7
        aiHard = new TicAIPerfect();    // Step 7

        for (int i=0; i<3; i++){
            //Loop information for each i until i=3, since the array length is 3
            //remembering that arrays count starting in 0, i starts with 0 too.
            //That also makes 2 the 3rd number in the array, which is why i=3 is not looped.
            for(int j=0; j<3; j++){
                //loop information for each j just like with i
                //Pay attention if all the letters are the same here. If one is different: error.
                String btnID = "btn_"+i+j;
                //The line above declares that btnID carries the names of the board buttons.
                //looping the 1st time will be btn_00 since i=0 and j=0
                //Since j loop is inside i loop, btnID will become btn_01 and 02 before becoming
                //btn_10. The j loop will reset to 0 once the i loop is run for the 2nd time.
                int resID = getResources().getIdentifier(btnID, "id", getPackageName());
                //The code above allows resID to be R.id.btnID
                buttons[i][j] = findViewById(resID);
                //and, because this is still inside the j loop, the 1st time this line will be read
                //as buttons[0][0] = findViewById(R.id.btn_00) and will chance for each loop run.
                buttons[i][j].setOnClickListener(this);
            }
        }

        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                startGame = false;
                Log.d("MYLOG", "startGame status: "+startGame);
                //the line above is implemented on Step 5
            }
        });
        // Step 7
        btnGo = findViewById(R.id.btn_ticgo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame = true;
                Log.d("MYLOG", "startGame status: "+startGame);
            }
        });
        radio_AInope = (RadioButton) findViewById(R.id.radio_AInope);
        radio_AIeasy = (RadioButton) findViewById(R.id.radio_AIeasy);
        radio_AIhard = (RadioButton) findViewById(R.id.radio_AIhard);
    }

    @Override
    public void onClick(View v) {
        // Step 2.2
        //If the button in this view (! means different) does NOT equal to an empty string
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if (startGame) { // with this if the player can only play after pressing Go
            // Step 8 - Set what will happen if one of the radios is clicked.
// Tip: Since we need the same Method in both (aiHard and aiEasy), allow AndroidStudio
// to create them for you in their classes (click on the red light bulb and chose create method)

            if (radio_AInope.isChecked()) {
                roundCount++;
                Log.d("MYLOG", "startGame status: " + startGame);
                Log.d("MYLOG", "X Round: " + roundCount);
                if (p1_turn) {
                    Log.d("MYLOG", "Player 1: normal");
                    ((Button) v).setText("X");
                }
                else{
                    Log.d("MYLOG", "Player 2: normal");
                    ((Button) v).setText("O");
                }
                changeTurn();
                Log.d("MYLOG", "Player 1 turn: " + p1_turn);
            }

            if (radio_AIeasy.isChecked()){
                //roundCount++;
                //is the bunch of if the thing blocking the autoplay without click?
                if (p1_turn) {
                    roundCount++;
                    Log.d("MYLOG", "X Round: " + roundCount);
                    Log.d("MYLOG", "Player 1: AI mode");
                    ((Button) v).setText("X");
                    Log.d("MYLOG", "a) Player 1 turn: " + p1_turn);
                    changeTurn();
                    Log.d("MYLOG", "b) Player 1 turn: " + p1_turn);
                }
                // For some reason, if changeTurn is left out of the ifs, the AI
                // methods don't work properly.
                // For some reason, if using else instead of if !(not)p1_turn, the autoplay doesn't run.
                if (!p1_turn)  {
                    roundCount++;
                    Log.d("MYLOG", "O Round: " + roundCount);
                    Log.d("MYLOG", "Player 2: AI mode");
                    aiEasy.nextMove(this);
                    Log.d("MYLOG", "main Player 2 turn: " + !p1_turn);
                    //changeTurn(); //it makes odd stuff and already have inside the random working
                    //Log.d("MYLOG", "main Player 2 turn: " + !p1_turn);
                }
            }

            /*
            if (radio_AIhard.isChecked()){
                if (p1_turn) {
                    Log.d("MYLOG", "Player 1: AI hard mode");
                    Log.d("MYLOG", "startGame status: " + startGame);
                    ((Button) v).setText("X");
                    roundCount++;
                    Log.d("MYLOG", "X Round: " + roundCount);
                    changeTurn();
                    Log.d("MYLOG", "Player 1 turn: " + p1_turn);
                }
                else if (!p1_turn) {
                    Log.d("MYLOG", "Player 2: AI hard mode");
                    aiHard.nextMove(this);
                    roundCount++;
                    Log.d("MYLOG", "O Round: " + roundCount);
                    Log.d("MYLOG", "Player 2 turn: " + !p1_turn);
                }
            }
            */
        }

// Step 4 - Call the methods created on Step 3
// The series of methods bellow check if one of the players won and, if one did or the game
// the game got a draw, the board is resetted.
        if (checkForWin()){
            if (p1_turn){
                p1_wins();
            }
            else{
                p2_wins();
            }
        }
        else if (roundCount>8){
            // if we get to the 9th turn, it's a draw because we only have 9 spaces to fill
            // and 1 space is filled each turn, also, it'd impossible to win in the 9th turn,
            // you get to that point because no one was able to win.
            draw();
        }
    }
    // Step 8 - Because we need to change turn from the AI, we create this method
    public void changeTurn() {
        //this will switch the players turn, if p1_turn is true, it'll become false
        //and vice versa
        if (checkForWin()){
            if (p1_turn){
                p1_wins();
            }
            else{
                p2_wins();
            }
        }
        p1_turn = !p1_turn;
    }

    // Step 3
    public boolean checkForWin() {

        String[][] field = new String[3][3];
        //Just like it was done for the buttons, the String fields will receive bellow different
        //information for each time the loop runs.

        for (int i=0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
                //In this case, field will receive the letters associated with [i][j], X or O.
            }

            //if i0 (the 1st button of any line) equals the button next to it
            if (field[i][0].equals(field[i][1])
                    //AND (&& means that the code after it must also be true)
                    //i0 equals the button in the end of the same line of i0
                    && field[i][0].equals(field[i][2])
                    //AND i0 is NOT empty
                    && !field[i][0].equals("")) {
                return true;
                //return true if any line was created
            }
        }
        for (int i=0; i<3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
                //return true if any column was created
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
            //return true if diagonal (left top to right bottom) line was created
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
            //return true if diagonal (right top to left bottom) line was created
        }
        return false;
    }

// Step 5 - Add Toasts to be a visual feedback of why the board is being resetted
// and update the players points (or not if it's a draw)
    protected void p1_wins(){
                p1_points++;
                updatePointsText();
                resetBoard();
        Toast.makeText(this, "Player 1 wins!", LENGTH_SHORT).show();
        Log.d("MYLOG", "---------------------------------------Player 1 Wins");
    }

    protected void p2_wins(){
        Handler handler = new Handler(); // Give a delay of 1 second, it's visually more pleasant
        handler.postDelayed(new Runnable() {
            public void run() {
            p2_points++;
            updatePointsText();
            resetBoard();
        }}, 1000); //Allow the player to be able to see how he/she/it lost
        Toast.makeText(this, "Player 2 wins!", LENGTH_SHORT).show();
        Log.d("MYLOG", "---------------------------------------Player 2 Wins");
        //OBS: Apparently Toasts cannot be displayed inside Runnables
    }

    private void draw(){
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void updatePointsText(){
        txtView_p1.setText("Player 1: "+p1_points);
        txtView_p2.setText("Player 2: "+p2_points);
    }

    private void resetBoard(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        Log.d("MYLOG", "Reset Round: " + roundCount);
    }

    private void resetGame(){
        p1_points=0;
        p2_points=0;
        updatePointsText();
        resetBoard();
    }

// Step 6 - Make sure the game doesn't reset when the orientation changes
// Ctrl+o, chose the methods onSaveInstanceState and onRestoreInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("p1_points", p1_points);
        outState.putInt("p2_points", p2_points);
        outState.putBoolean("p1_turn", p1_turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        p1_points = savedInstanceState.getInt("p1_points");
        p2_points = savedInstanceState.getInt("p2_points");
        p1_turn = savedInstanceState.getBoolean("p1_turn");
    }
}
