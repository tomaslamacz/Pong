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

    private int speedX = 120;
    private int speedY = 60;
    private int diagSpeedY = 120;



    enum directions{
        UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT,
        //RIGHT, LEFT,
        UP_RIGHT_DIAG, DOWN_RIGHT_DIAG, DOWN_LEFT_DIAG, UP_LEFT_DIAG;//vice nahoru nebo dolu
    }
    private directions direction = directions.UP_LEFT;

    public Ball(Point screenSize){
        this.screenSize=screenSize;

        paint = new Paint();
        paint.setColor(Color.YELLOW);

        width = screenSize.x / 30;
        height = width;

        posX = screenSize.x / 2 - width / 2;
        posY = screenSize.y / 2 + height*4;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

    }

    public void reset() {
        posX = screenSize.x / 2 - width / 2;
        posY = screenSize.y / 2 + height*4;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

        direction = directions.UP_LEFT;

        speedX = 120;
        speedY = 60;
        diagSpeedY = 120;
    }

    public Rect getRect() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }

    public void update(){


        if (direction == directions.UP_RIGHT){
            posX += speedX/10;
            posY -= speedY/10;
        } else if (direction == directions.UP_LEFT){
            posX -= speedX/10;
            posY -= speedY/10;
        } else if (direction == directions.DOWN_LEFT){
            posX -= speedX/10;
            posY += speedY/10;
        } else if (direction == directions.DOWN_RIGHT){
            posX += speedX/10;
            posY += speedY/10;
        }/* else if (direction == directions.LEFT){
            posX -= speedX;
            //posY += speedY;
        } else if (direction == directions.RIGHT){
            posX += speedX;
            //posY += speedY;
        }*/ else if (direction == directions.UP_RIGHT_DIAG){
            posX += speedX/10;
            posY -= diagSpeedY/10;
        } else if (direction == directions.UP_LEFT_DIAG){
            posX -= speedX/10;
            posY -= diagSpeedY/10;
        } else if (direction == directions.DOWN_LEFT_DIAG){
            posX -= speedX/10;
            posY += diagSpeedY/10;
        } else if (direction == directions.DOWN_RIGHT_DIAG){
            posX += speedX/10;
            posY += diagSpeedY/10;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void adjustSpeed(){
        if (speedX < 180){
            speedX *= 1.05;
            speedY *= 1.03;
            diagSpeedY *= 1.03;
        }



        Log.i("speedx",""+speedX);
    }
}
