package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Ball {
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Rect rect;
    private Paint paint;

    public Ball(Point screenSize){
        paint = new Paint();
        paint.setColor(Color.GREEN);

        width = screenSize.x / 30;
        height = width;

        posX = screenSize.x / 2 - width / 2;
        posY = screenSize.y / 2 - height / 2;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

    }

    public Rect getRect() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }

    public void update(){
        posX += 25;
        rect.left = posX;
        rect.right = posX + width;
        Log.d("update ball",posX+" is now pos x");
    }

    public void draw(Canvas canvas) {

        canvas.drawRect(this.getRect(), this.getPaint());
    }
}
