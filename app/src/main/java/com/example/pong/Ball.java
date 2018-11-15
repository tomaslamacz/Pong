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

    private int speedX = 12;
    private int speedY = 8;

    enum directions{
      UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT;
    }
    private directions direction = directions.DOWN_LEFT;

    public Ball(Point screenSize){

        paint = new Paint();
        paint.setColor(Color.WHITE);

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


        if (direction == directions.UP_RIGHT){
            posX += speedX;
            posY -= speedY;
        } else if (direction == directions.UP_LEFT){
            posX -= speedX;
            posY -= speedY;
        } else if (direction == directions.DOWN_LEFT){
            posX -= speedX;
            posY += speedY;
        } else if (direction == directions.DOWN_RIGHT){
            posX += speedX;
            posY += speedY;
        }


        rect.left = posX;
        rect.right = posX + width;
        rect.top = posY;
        rect.bottom = posY + height;

        Log.d("update ball",posX+" is now pos x");
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(this.getRect(), this.getPaint());
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public directions getDirection() {
        return direction;
    }

    public void setDirection(directions direction) {
        this.direction = direction;
    }
}
