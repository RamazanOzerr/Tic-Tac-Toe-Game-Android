package com.example.tictactoegame;

import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

public class GameLogic {

    private int[][] gameBoard;

    private int[] winType; // row, col, lineType
    private int player;

    private String[] playerNames;
    private AppCompatButton btnPlayAgain, btnHome;
    private AppCompatTextView textPlayerTurn;

    public GameLogic(){
        player = 1;
        winType = new int[]{-1, -1, -1};
        playerNames = new String[2];
        gameBoard = new int[3][3];
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int col){
        if(gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            if(player == 1){
                textPlayerTurn.setText(playerNames[1] + "'s turn");
            }else{
                textPlayerTurn.setText(playerNames[0] + "'s turn");
            }

            return true;
        }else{
            return false;
        }
    }

    public boolean winnerCheck(){

        boolean isWinner = false;

        // horizontal check
        for(int r = 0; r < 3; r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2]
                    && gameBoard[r][0] != 0){

                winType = new int[] {r, 0, 1};
                isWinner = true;
            }
        }

        // vertical chech
        for(int c = 0; c < 3; c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c]
                    && gameBoard[0][c] != 0){

                winType = new int[] {0, c , 2};
                isWinner = true;
            }
        }

        // negative diagonal check(winType == 3)
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2]
                && gameBoard[0][0] != 0){

            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        // positive diagonal check(winType == 4)
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2]
                && gameBoard[2][0] != 0){

            winType = new int[] {2, 2, 4}; // burası 2 2 4 tü değiştirdik
            isWinner = true;
        }

        int boardFilled = 0;

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(gameBoard[r][c] != 0){
                    boardFilled++;
                }
            }
        }

        if(isWinner){
            btnPlayAgain.setVisibility(View.VISIBLE);
            btnHome.setVisibility(View.VISIBLE);
            textPlayerTurn.setText(playerNames[player-1] + " won!!!!!");
            return true;

        }else if(boardFilled == 9){
            btnPlayAgain.setVisibility(View.VISIBLE);
            btnHome.setVisibility(View.VISIBLE);
            textPlayerTurn.setText("Tie Game!!!!!");
            return false;

        }else{
            return false;
        }




    }

    public void resetGame(){
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                gameBoard[r][c] = 0;
            }
        }

        player = 1;

        btnPlayAgain.setVisibility(View.GONE);
        btnHome.setVisibility(View.GONE);

        textPlayerTurn.setText(playerNames[0] + "'s turn");
    }
    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setTextPlayerTurn(AppCompatTextView textPlayerTurn) {
        this.textPlayerTurn = textPlayerTurn;
    }

    public void setBtnHome(AppCompatButton btnHome) {
        this.btnHome = btnHome;
    }

    public void setBtnPlayAgain(AppCompatButton btnPlayAgain) {
        this.btnPlayAgain = btnPlayAgain;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[] getWinType() {
        return winType;
    }
}
