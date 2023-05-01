package com.example.tictactoegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.tictactoegame.R;

public class GameBoard extends View {

    private final int boardColor, xColor, oColor, winningLineColor;
    private final Paint paint;

    private boolean winningLine;
    private int cellSize;
    private final GameLogic game;

    public GameBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();
        paint = new Paint();
        cellSize = getMeasuredWidth() / 3;
        winningLine = false;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GameBoard,0,0);

        try{
            boardColor = a.getInteger(R.styleable.GameBoard_boardColor,0);
            xColor = a.getInteger(R.styleable.GameBoard_xColor,0);
            oColor = a.getInteger(R.styleable.GameBoard_oColor,0);
            winningLineColor = a.getInteger(R.styleable.GameBoard_winningLineColor,0);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension / 3; // 3 tane kutucuk olacağı için ekran boyutunu 3 e böldük

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
//        drawX(canvas, 1,1);
//        drawO(canvas,0,0);

        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(game.getGameBoard()[r][c] != 0){
                     if(game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    }else{
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawGameBoard(Canvas canvas){

        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int c = 1; c < 3; c++){
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }

        for (int r = 1; r < 3; r++){
            canvas.drawLine(0, cellSize * r,canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(xColor);
        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col+1)*cellSize - cellSize*0.2),
                (float) ((row+1)*cellSize - cellSize*0.2),
                paint);
    }

    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(oColor);
        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col*cellSize + cellSize) - cellSize*0.2),
                (float) ((row*cellSize + cellSize) - cellSize*0.2),
                paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*cellSize +(float) cellSize / 2,
                cellSize*3, row * cellSize + (float) cellSize / 2,
                paint);
    }

    private void drawVertical(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize + (float) cellSize / 2, row,
                col *  cellSize + (float) cellSize / 2, cellSize * 3,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize * 3, cellSize * 3, 0
        ,paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0, 0, cellSize * 3, cellSize * 3
                ,paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVertical(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }

    public void setUpGame(AppCompatButton playAgain, AppCompatButton home, AppCompatTextView playerDisplay, String[] names){
        game.setBtnPlayAgain(playAgain);
        game.setBtnHome(home);
        game.setTextPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if(!winningLine){
                if(game.updateGameBoard(row, col)){
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    // burda oyuncu sırasını değiştiriyoruz, farklı şekilde de yapılabilir
                    if(game.getPlayer() % 2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    }else{
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }



            return true;
        }
        return false;

    }
}
