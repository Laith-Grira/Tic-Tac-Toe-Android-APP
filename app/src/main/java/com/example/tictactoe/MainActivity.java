package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main class of the program.
 * It has all of the methods and veriables used in the app
 * There is no other java class except this one.
 *
 * @author Laith Grira #300134752, University of Ottawa
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * The board of the game
     * Stored in a 2D array
     */
    private Button[][] board = new Button[3][3];

    /**
     * Checking the player1's turn
     */
    private boolean player1Turn = true;

    /**
     * storing the number of cells used in an integer cellsUsed
     */
    private int cellsUsed;

    /**
     * storing the score of the player X in an integer variable PlayerXscore
     */
    private int playerXscore;

    /**
     * storing the score of the player O in an integer variable PlayerOscore
     */
    private int playerOscore;

    //Test view in the UI for player X
    private TextView TextPlayerX;

    //Test view in the UI for player O
    private TextView TextPlayerO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextPlayerX = findViewById(R.id.text_view_player1);
        TextPlayerO = findViewById(R.id.text_view_player2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                board[i][j] = findViewById(resID);
                board[i][j].setOnClickListener(this);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        cellsUsed++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (cellsUsed == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    /**
     * This method compares the cells to check for a winner
     * @return
     * returns true if we got a winner and false if the game is still on or we have a draw
     */
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // copy all the game board in a 2D string filed
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = board[i][j].getText().toString();
            }
        }

        /**
         * for i = 0
         *
         *        X | X | X
         *        ---------
         *          |   |
         *        ---------
         *          |   |
         *
         *for i = 1
         *
         *           |   |
         *        ---------
         *        X | X | X
         *        ---------
         *          |   |
         *
         * for i = 2
         *
         *           |   |
         *        ---------
         *          |   |
         *        ---------
         *        X | X | X
         *
         * Check if the first 3 horizontal cells have the same text
         * and not empty
         */
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        /**
         *
         * for i = 0
         *
         *       X |   |
         *      ---------
         *       X |   |
         *      ---------
         *       X |   |
         *
         * or for i = 1
         *
         *         | X |
         *       ---------
         *         | X |
         *       ---------
         *         | X |
         *
         * or for i = 2
         *
         *         |   | X
         *       ---------
         *         |   | X
         *       ---------
         *         |   | X
         *
         *
         * Check if the first 3 vertical cells have the same text
         * and not empty
         */
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        /**
         *
         *       X |   |
         *       ---------
         *         | X |
         *       ---------
         *         |   | X
         *
         * Check if the inclined cells have the same text
         * and not empty
         */
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        /**
         *
         *   |   | X
         * ---------
         *   | X |
         * ---------
         * X |   |
         *
         * Check if the other type of inclined cells have the same text
         * and not empty
         */
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    /**
     * X wins! pops up to the user if the player X win
     */
    private void player1Wins() {
        playerXscore++;
        Toast.makeText(this, "X wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    /**
     * O wins! pops up if the player O wins
     */
    private void player2Wins() {
        playerOscore++;
        Toast.makeText(this, "O wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    /**
     * Draw! pops up to the user if we have no winner
     */
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    /**
     * Update the score of each player in the board
     */
    private void updatePointsText() {
        TextPlayerX.setText("X  " + playerXscore +": ");
        TextPlayerO.setText(playerOscore +"  O");
    }

    /**
     * Restarting the board
     */
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setText("");
            }
        }
        cellsUsed = 0;
        player1Turn = true;
    }
}