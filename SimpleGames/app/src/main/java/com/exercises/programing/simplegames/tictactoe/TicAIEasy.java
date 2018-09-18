package com.exercises.programing.simplegames.tictactoe;

import android.os.Handler;
import android.util.Log;
import java.util.Random;

public class TicAIEasy {

    private TicMainActivity board;

    public void nextMove(final TicMainActivity boardImport) {
        this.board = boardImport;

        Log.d("MY LOG", "----Next Move - Player 1 turn: " + board.p1_turn + "----");
        Handler handler = new Handler(); // Give a delay of 1 second, it's visually more pleasant
        handler.postDelayed(new Runnable() {

           @Override
            public void run() {
               Log.d("MY LOG", "Inside the Handler");
                if(!board.p1_turn) {
                    Log.d("MY LOG", "Trying to win!");
                    trywin();}// First priority goes to winning

                if(!board.p1_turn) {
                    Log.d("MY LOG", "Defending!");
                    defend();} // Second priority goes to preventing player from winning

                if(!board.p1_turn) {
                    Log.d("MY LOG", "No idea what I'm doing");
                    randomMove();} // Make a random move

            } //close run
        }, 1000); //close handler runnable
    } //close method



    private void trywin() {
        String[][] field = new String[3][3];
        Log.d("MY LOG", "INSIDE TRY TO WIN");
        Log.d("MY LOG", "----Player 1 turn: " + board.p1_turn + "----");

        for (int i=0; i<3; i++) { //why do i need this for the code to work if it does nothing?
            //because it defines the value of field [][]
            for (int j = 0; j < 3; j++) {
                field[i][j] = board.buttons[i][j].getText().toString();
                //In this case, field will receive the letters associated with [i][j], X or O.

            }
        }
        Log.d("MY LOG", "OUT OF FOR i: 1");
//CHECK IF A LINE IS ABOUT TO BE MADE:
        //if i0 (the 1st button of any line) equals the button next to it
        for (int i=0; i<3; i++) {
            //IN A FOR LIKE THIS, ALWAYS USE ONLY ONE IF INSIDE OR THE PROGRAM MAY GET KIND OF CRAZY
            if (field[i][0].equals(field[i][1]) && field[i][0].equals("O")
                    && field[i][2].isEmpty()) {
                board.buttons[i][2].setText("O");
                Log.d("MY LOG", "WIN 1st If is btn"+i+"2");
                board.changeTurn();
                break;
            }
        else if (field[i][0].equals(field[i][2]) && field[i][0].equals("O")
                    && field[i][1].isEmpty()) {
                board.buttons[i][1].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 2st If is btn"+i+"1");
                break;
            }
        else if (field[i][1].equals(field[i][2])
                    && field[i][1].equals("O")
                    && field[i][0].isEmpty()) {
                board.buttons[i][0].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 3st If is btn"+i+"0");
                break;
            }
        }
//CHECK IF A COLUMN IS ABOUT TO BE MADE:
        for (int i=0; i<3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals("O")
                    && field[2][i].isEmpty()) {
                board.buttons[2][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 4st If is btn2"+i);
                break;
            }
        else if (field[0][i].equals(field[2][i]) && field[0][i].equals("O")
                    && field[1][i].isEmpty()) {
                board.buttons[1][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 4st If is btn1"+i);
                break;
            }
        else if (field[1][i].equals(field[2][i])
                    && field[1][i].equals("O")
                    && field[0][i].isEmpty()) {
                board.buttons[0][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 5st If is btn0"+i);
                break;
            }
        }
//CHECK IF A DIAGONAL IS ABOUT TO BE MADE:
        if (!board.p1_turn) {
            // diagonal (left top to right bottom) line
            if (field[0][0].equals("O")) {
                if (field[0][0].equals(field[2][2])
                        && field[1][1].isEmpty()) {
                    board.buttons[1][1].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "WIN 5st If is btn11");
                }
                if (field[0][0].equals(field[1][1])
                        && field[2][2].isEmpty()) {
                    board.buttons[2][2].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "WIN 6st If is btn22");
                }
            }

            if (field[1][1].equals(field[2][2])
                    && field[1][1].equals("O")
                    && field[0][0].isEmpty()) {
                board.buttons[0][0].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 7st If is btn00");
            }
            // diagonal (right top to left bottom) line
            if (field[0][2].equals("O")) {
                if (field[0][2].equals(field[1][1])
                        && field[2][0].isEmpty()) {
                    board.buttons[2][0].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "WIN 8st If is btn20");
                }
                if (field[0][2].equals(field[2][0])
                        && field[1][1].isEmpty()) {
                    board.buttons[1][1].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "WIN 9st If is btn11");
                }
            }
            if (field[1][1].equals(field[2][0])
                    && field[1][1].equals("O")
                    && field[0][2].isEmpty()) {
                board.buttons[0][2].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "WIN 10st If is btn02");
            }
            Log.d("MY LOG", "OUT OF TRY TO WIN");
        }
    }



    private void defend() {

        Log.d("MY LOG", "INSIDE DEFENCE");
        String[][] field = new String[3][3];
        Log.d("MY LOG", "----Player 1 turn: " + board.p1_turn + "----");
        //board.p1_turn = false;
        //Log.d("MY LOG", "----Player 1 turn: " + board.p1_turn + "----");

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = board.buttons[i][j].getText().toString();
                Log.d("MYLOG", "i="+i+" j="+j);
                Log.d("MYLOG", "field ="+field[i][j]);
                //field will receive the letters associated with [i][j], X or O.
            }
//CHECK IF A LINE IS ABOUT TO BE MADE:
            //if i0 (the 1st button of any line) equals the button next to it
            Log.d("MYLOG","field "+i+"0 = "+field[i][0]);
            Log.d("MYLOG","field "+i+"1 = "+field[i][1]);
            Log.d("MYLOG","field "+i+"2 = "+field[i][2]);
            Log.d("MYLOG","field 0"+i+" = "+field[i][0]);
            Log.d("MYLOG","field 1"+i+" = "+field[i][1]);
            Log.d("MYLOG","field 2"+i+" = "+field[i][2]);
            if (field[i][0] != null && field[i][0].equals(field[i][1])
                    // AND i0 is NOT empty
                    && field[i][0].equals("X")
                    // AND i2 (the last button of any line) is empty
                    && field[i][2].isEmpty()) {
                board.buttons[i][2].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 1st If is btn" + i + "2");
                Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                break;
            }
            else if (field[i][0] != null && field[i][0].equals(field[i][2])
                    && field[i][0].equals("X")
                    && field[i][1].isEmpty()) {
                board.buttons[i][1].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 2nd If is btn" + i + "1");
                Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                break;
            }
        else if (field[i][1] != null && field[i][1].equals(field[i][2])
                    && field[i][1].equals("X")
                    && field[i][0].isEmpty()) {
                board.buttons[i][0].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 3rd If is btn"+i+"0");
                Log.d("MY LOG", "Player 1 = "+board.p1_turn);
                break;
            }
        }
//CHECK IF A COLUMN IS ABOUT TO BE MADE:
        for (int i=0; i<3; i++) {
            if (field[0][i] != null && field[0][i].equals(field[1][i])
                    && field[0][i].equals("X")
                    && field[2][i].isEmpty()) {
                board.buttons[2][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 4th If is btn2" + i);
                Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                break;
            }
            else if (field[0][i] != null && field[0][i].equals(field[2][i])
                    && field[0][i].equals("X")
                    && field[1][i].isEmpty()) {
                board.buttons[1][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 5th If is btn1" + i);
                Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                break;
            }
            else if (field[1][i] != null && field[1][i].equals(field[2][i]) //-----------229
                    && field[1][i].equals("X")
                    && field[0][i].isEmpty()) {
                board.buttons[0][i].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 6th If is btn0"+i);
                Log.d("MY LOG", "Player 1 = "+board.p1_turn);
                break;
            }
        }
//CHECK IF A DIAGONAL IS ABOUT TO BE MADE:
        if (!board.p1_turn) {
            // diagonal (left top to right bottom) line
            if (field[0][0].equals("X")) {
                if (field[0][0].equals(field[2][2])
                        && field[1][1].isEmpty()) {
                    board.buttons[1][1].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "DEFEND 7th If is btn11");
                    Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                }
                if (field[0][0].equals(field[1][1])
                        && field[2][2].isEmpty()) {
                    board.buttons[2][2].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "DEFEND 8th If is btn22");
                    Log.d("MY LOG", "Player 1 = " + board.p1_turn);
                }
            }
            if (field[1][1].equals(field[2][2])
                    && field[1][1].equals("X")
                    && field[0][0].isEmpty()) {
                board.buttons[0][0].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 9th If is btn00");
                Log.d("MY LOG", "Player 1 = " + board.p1_turn);
            }
            // diagonal (right top to left bottom) line
            if (field[0][2].equals("X")) {
                if (field[0][2].equals(field[1][1])
                        && field[2][0].isEmpty()) {
                    board.buttons[2][0].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "DEFEND 10th If is btn20");
                }
                if (field[0][2].equals(field[2][0])
                        && field[1][1].isEmpty()) {
                    board.buttons[1][1].setText("O");
                    board.changeTurn();
                    Log.d("MY LOG", "DEFEND 11th If is btn11");
                }
            }
            if (field[1][1].equals(field[2][0])
                    && field[1][1].equals("X")
                    && field[0][2].isEmpty()) {
                board.buttons[0][2].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "DEFEND 12th If is btn02");
            }
            Log.d("MY LOG", "OUT OF DEFENCE");
        }
    }



    private void randomMove() {
        Random r = new Random();
        //int pts = r.nextInt(max - min + 1) + min;

        String[][] field = new String[3][3];
        Log.d("MY LOG", "INSIDE RANDOM");
        int m = r.nextInt(2 + 1);
        Log.d("MY LOG", "------------------ m = " + m + " ---------------------");
        int n = r.nextInt(2 + 1);
        Log.d("MY LOG", "------------------ n = " + n + " ---------------------");
        Log.d("MY LOG", "----Player 1 turn: " + board.p1_turn + "----");
        while (!board.p1_turn) {
            field[m][n] = board.buttons[m][n].getText().toString();
            if (field[m][n].isEmpty()) {
                Log.d("MY LOG", "Marcado em: btn"+m+n);
                board.buttons[m][n].setText("O");
                board.changeTurn();
                Log.d("MY LOG", "----Player 1 turn: " + board.p1_turn + "----");
            }
            m = r.nextInt(2 + 1);
            n = r.nextInt(2 + 1);
        }
        Log.d("MY LOG", "OUT OF RANDOM");
    }
}
