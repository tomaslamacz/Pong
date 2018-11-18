package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;

public class GameEndText {
    private int posX;
    private int posY;


    private String text;
    private Paint paint;



    private Point screenSize;

    public GameEndText(Point screenSize, Context context){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/press_start_2p.ttf");
        paint.setTypeface(tf);

        this.screenSize = screenSize;


        posX = screenSize.x / 2;
        posY = screenSize.y / 2;



    }

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(text, posX, posY, paint);
        //Log.i("score draw",""+leftPlayerScore);
    }


}
