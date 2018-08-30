package com.exercises.programing.simplegames.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exercises.programing.simplegames.R;

public class TicMainActivity extends AppCompatActivity implements View.OnClickListener{

    // Step 2.1 - Declare all your variables and implement your buttons on the onCreate
    private Button[][] buttons = new Button[3][3];
    private boolean p1_turn = true;
    private int roundCount;
    private int p1_points;
    private int p2_points;
    private TextView txtView_p1;
    private TextView txtView_p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_main);

        txtView_p1 = findViewById(R.id.txtView_p1);
        txtView_p2 = findViewById(R.id.txtView_p2);

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

        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                //the line above is implemented on Step 5
            }
        });
    }

    @Override
    public void onClick(View v) {
        //If the button in this view (! means different) does NOT equal to an empty string
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if (p1_turn){
            ((Button)v).setText("X");
        }
        else{
            ((Button)v).setText("O");
        }
        roundCount++;
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
        else if (roundCount==9){
            draw();
        }
        else{
            //this will switch the players turn, if p1_turn is true, it'll become false
            //and vice versa
            p1_turn = !p1_turn;
        }
    }
    // Step 3
    private boolean checkForWin() {

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
            }
        }
        for (int i=0; i<3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

// Step 5 - Add Toasts to be a visual feedback of why the board is being resetted
// and update the players points (or not if it's a draw)
    private void p1_wins(){
        p1_points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void p2_wins(){
        p2_points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
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
        p1_turn=true;
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
