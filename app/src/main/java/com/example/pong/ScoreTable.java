package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

public class ScoreTable {
    private int posX;
    private int posY;


    private String text;
    private Paint paint;


    private int leftPlayerScore;
    private int rightPlayerScore;
    private int gameMode;

    private Point screenSize;

    public ScoreTable(Point screenSize, int leftPlayerScore, int rightPlayerScore, int gameMode, Context context){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/press_start_2p.ttf");
        paint.setTypeface(tf);

        this.screenSize = screenSize;


        posX = screenSize.x / 2;
        posY = screenSize.y / 5;


        this.gameMode = gameMode;
        this.leftPlayerScore = leftPlayerScore;
        if (gameMode == 2){
            this.text = leftPlayerScore+"";
        } else {
            this.rightPlayerScore = rightPlayerScore;
            this.text = leftPlayerScore + " : " + rightPlayerScore;
        }

    }

    public void setLeftPlayerScore(int score){
        this.leftPlayerScore = score;
    }

    public void setRightPlayerScore(int score){
        this.rightPlayerScore = score;
    }

    public int getLeftPlayerScore() {
        return leftPlayerScore;
    }

    public int getRightPlayerScore() {
        return rightPlayerScore;
    }

    public void draw(Canvas canvas) {
        if (gameMode == 2){
            this.text = leftPlayerScore+"";
        } else {
            this.text = leftPlayerScore + " : " + rightPlayerScore;
        }
        canvas.drawText(text, posX, posY, paint);
        Log.i("score draw",""+leftPlayerScore);
    }


}
