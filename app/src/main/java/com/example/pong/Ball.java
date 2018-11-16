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
    private Point screenSize;

    private int speedX = 12;
    private int speedY = 6;
    private int diagSpeedY = 12;



    enum directions{
        UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT,
        RIGHT, LEFT,
        UP_RIGHT_DIAG, DOWN_RIGHT_DIAG, DOWN_LEFT_DIAG, UP_LEFT_DIAG;//vice nahoru nebo dolu
    }
    private directions direction = directions.LEFT;

    public Ball(Point screenSize){
        this.screenSize=screenSize;

        paint = new Paint();
        paint.setColor(Color.YELLOW);

        width = screenSize.x / 30;
        height = width;

        posX = screenSize.x / 2 - width / 2;
        posY = screenSize.y / 2 + height;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

    }

    public void reset() {
        posX = screenSize.x / 2 - width / 2;
        posY = screenSize.y / 2 + height;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

        direction = directions.LEFT;
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
        } else if (direction == directions.LEFT){
            posX -= speedX;
            //posY += speedY;
        } else if (direction == directions.RIGHT){
            posX += speedX;
            //posY += speedY;
        } else if (direction == directions.UP_RIGHT_DIAG){
            posX += speedX;
            posY -= diagSpeedY;
        } else if (direction == directions.UP_LEFT_DIAG){
            posX -= speedX;
            posY -= diagSpeedY;
        } else if (direction == directions.DOWN_LEFT_DIAG){
            posX -= speedX;
            posY += diagSpeedY;
        } else if (direction == directions.DOWN_RIGHT_DIAG){
            posX += speedX;
            posY += diagSpeedY;
        }

        /*if (direction == directions.UP_RIGHT){
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
        }*/


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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void setDiagSpeedY(int diagSpeedY) {
        this.diagSpeedY = diagSpeedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getDiagSpeedY() {
        return diagSpeedY;
    }
}
