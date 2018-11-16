package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Line {
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Rect rect;
    private Paint paint;

    private Point screenSize;
    private int gameMode;

    public Line(Point screenSize, int gameMode){
        paint = new Paint();
        paint.setColor(Color.GRAY);

        width = screenSize.x / 200;
        height = screenSize.y;

        this.screenSize = screenSize;
        this.gameMode = gameMode;


        posX = screenSize.x * 1/2 - width / 2;//vpravo
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
        if(gameMode != 2)
            canvas.drawRect(this.getRect(), this.getPaint());
    }


}
