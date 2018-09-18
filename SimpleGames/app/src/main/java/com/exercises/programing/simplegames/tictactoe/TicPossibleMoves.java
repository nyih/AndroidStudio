package com.exercises.programing.simplegames.tictactoe;

public class TicPossibleMoves {
    String[][] boardState; // The state of the board at a given stage
    int score; // The score associated with the board (win = +1, lose = -1, else = 0)
    int depth; // The depth in the tree structure in which this state exists

    public TicPossibleMoves(String[][] boardState, int score, int depth) {
        this.boardState = boardState;
        this.score = score;
        this.depth = depth;
    }

    public int getScore() {
        return score;
    }

    public void setboardState(String[][] boardState) {
        this.boardState = boardState;
    }
}
