package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Wall {
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Rect rect;
    private Paint paint;

    private Point screenSize;

    public Wall(Point screenSize){
        Log.d("wall","new wall");

        paint = new Paint();
        paint.setColor(Color.WHITE);

        width = screenSize.x / 100;
        height = screenSize.y;

        this.screenSize = screenSize;


        posX = screenSize.x * 9/10 - width / 2;//vpravo
        posY = 0;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = height;
    }

    public Rect getRect() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(this.getRect(), this.getPaint());
    }


}
