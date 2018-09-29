package com.exercises.programing.simplegames.tictactoe;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class TicAIPerfect {
// WORK ON PROGRESS: TRYING TO FIGURE HOW TO DO MINIMAX
    /*
    private TicMainActivity board;
    //ArrayList<ArrayList> scoreList  ;
    String[][] field = new String[3][3]; //know in the original as textBoard
    // The state of the board in text form
    private String level; // Current level of operation (Max or Min)
    private String max = "O";
    //private int deph = 9;
    public void nextMove(TicMainActivity boardImport) {
        this.board = boardImport;

        if (board.p1_turn){
            return;
        }

        level = "max";

        Log.d("MYLOG", "----Next Move MiniMax - Player 1 turn: " + board.p1_turn + "----");
        Handler handler = new Handler(); // Give a delay of 1 second, it's visually more pleasant
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d("MYLOG", "Inside the Handler");

                //setTextBoard(); // Convert the current board state in text format
                TicPossibleMoves bestMove = minimax(field, level, 0, 0); // (textBoard, level, recurse, depth)
                applyTextBoard(bestMove.boardState); // Apply the text board to the actual view

                board.changeTurn();

            } //close run
        }, 1000); //close handler runnable
    } //close method

    private TicPossibleMoves getResult(ArrayList<TicPossibleMoves> listScore, String level){
        TicPossibleMoves result = listScore.get(0);
        if(level.equals("max")) {
            for(int i = 1; i < listScore.size(); i++) {
                if((listScore.get(i).getScore() > result.getScore()) ||
                        (listScore.get(i).getScore() == result.getScore()
                                && listScore.get(i).depth < result.depth))
                    result = listScore.get(i);
            }
        } else {
            for(int i = 1; i < listScore.size(); i++) {
                if((listScore.get(i).getScore() < result.getScore()) ||
                        (listScore.get(i).getScore() == result.getScore()
                                && listScore.get(i).depth < result.depth))
                    result = listScore.get(i);
            }
        }
        return result;
    }

    private TicPossibleMoves minimax(String[][] textBoard, String level, int recurse, int depth) {
        ArrayList<String[][]> children = (ArrayList<String[][]>) generateSuccessor(textBoard, level);
        // Get list of all possible moves

        if(children == null || gameOver(textBoard)) {
            return new TicPossibleMoves(textBoard, getScore(textBoard), depth);
            // if no children or game over
        } else {
            ArrayList<TicPossibleMoves> listScore = new ArrayList<>();
            // Get a list of scores for all available children
            for(int i = 0; i < children.size(); i++) {
                listScore.add(minimax(children.get(i), level, 1, depth + 1));
                // recursive call
                // with recurse = 1 and depth + 1
            }

            TicPossibleMoves res = getResult(listScore, level); // Get the child
            // with maximum (for max condition) or minimum (for min condition) score
            if(recurse == 1)
                res.setboardState(textBoard); // If this is a recursive call, set the result
            return res;
        }
    }


    private ArrayList<String[][]> generateSuccessor(String[][] textBoard, String level) {
        ArrayList<String[][]> successor = new ArrayList<>();

        for(int i = 0; i < textBoard.length; i++) {
            Log.d("MYLOG", "textBoard.length is "+textBoard.length);
            for (int j=0; j<3; j++) {
                if (textBoard[i][j].equals("")) {
                    String[][] child = new String[3][3];
                    System.arraycopy(textBoard, 0, child, 0, 9);
                    // First match the child with the parent board state
                    successor.add(child);
                }
            }
        }
        return (successor.size() == 0) ? null : successor;
    }

    private boolean gameOver(String[][] textBoard) {
        return (getScore(textBoard) != 0); // Is the game over (is the score not 0)
    }

    // Return +1 when the computer wins, -1 when the computer loses and 0 otherwise
    private int getScore(String[][] field) {
        if(max.equals("O")) {
            //String[][] field = new String[3][3];
            for (int i = 0; i < 3; i++) {
                if (    //Lines:
                        field[i][0].equals(field[i][1])
                                && field[i][0].equals(field[i][2])
                                && field[i][0].equals("X") ||
                                //Columns:
                                field[0][i].equals(field[1][i])
                                        && field[0][i].equals(field[2][i])
                                        && field[0][i].equals("X") ||
                                //Diagonals:
                                field[0][0].equals(field[1][1])
                                        && field[0][0].equals(field[2][2])
                                        && field[0][0].equals("X") ||
                                field[0][2].equals(field[1][1])
                                        && field[0][2].equals(field[2][0])
                                        && field[0][2].equals("X")
                        ) {
                    return -1;
                } else if (    //Lines:
                        field[i][0].equals(field[i][1])
                                && field[i][0].equals(field[i][2])
                                && field[i][0].equals("O") ||
                                //Columns:
                                field[0][i].equals(field[1][i])
                                        && field[0][i].equals(field[2][i])
                                        && field[0][i].equals("O") ||
                                //Diagonals:
                                field[0][0].equals(field[1][1])
                                        && field[0][0].equals(field[2][2])
                                        && field[0][0].equals("O") ||
                                field[0][2].equals(field[1][1])
                                        && field[0][2].equals(field[2][0])
                                        && field[0][2].equals("O")
                        ) {
                    return 1;
                }
            }
        }
        return 0;
    }


    private void setTextBoard() {
        //String[][] field = new String[3][3];
        for (int t=0; t<3; t++) {
            for (int r=0; r<3; r++){
                field[t][r] = board.buttons[t][r].getText().toString();
            }
        }
    }

    private void applyTextBoard(String[][] field) {
        //String[][] field = new String[3][3];
        for (int t=0; t<3; t++) {
            for (int r=0; r<3; r++){
                board.buttons[t][r].setText(field[t][r]);
            }
        }
    }
*/
/*
    private int chooseSpace(Game game){
        return bestMove(game, 0, potentialOutcomes);
    }

    private void bestMove(board, deph, potentialOutcomes) {
        int draw = 0;
        int win = -1;
        if (game.isGameTied()){
            return TIEDGAME;
        }
        if (game.isGameOver()){
            return WONGAME;
        }
        else {
            checkPossibilities(game, )
        }
    }
    */
}
