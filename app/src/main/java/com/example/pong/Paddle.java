package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Paddle {
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Rect rect;
    private Paint paint;

    private Point screenSize;

    private int speed = 30;

    public Paddle(Point screenSize, boolean side){


        paint = new Paint();
        paint.setColor(Color.WHITE);

        width = screenSize.x / 100;
        height = screenSize.y / 6;

        this.screenSize = screenSize;

        if(side)
            posX = screenSize.x * 9/10/* - width / 2*/;//vpravo
        else
            posX = screenSize.x * 1/10 - width /*/ 2*/;//vlevo

        posY = screenSize.y / 2 - height / 2;

        rect = new Rect();
        rect.left = posX;
        rect.top = posY;
        rect.right = posX + width;
        rect.bottom = posY + height;

        Log.d("wall p left",""+rect.left);
        Log.d("wall p top",""+rect.top);
        Log.d("wall p right",""+rect.right);
        Log.d("wall p bottom",""+rect.bottom);

    }

    public Rect getRect() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }

    public void update(){
        rect.top = posY;
        rect.bottom = posY + height;
        Log.d("update paddle",posY+" is now pos y");
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(this.getRect(), this.getPaint());
    }

    public int getPosY() {
        return posY;
    }

    public void moveUp() {
        if(posY > speed){
            posY = posY - speed;
        } else{
            posY = 0;
        }

        Log.d("move ","up");
    }
    public void moveDown() {
        if(posY < screenSize.y - height - speed){
            posY = posY+speed;
        } else {
            posY = screenSize.y - height - speed;
        }

        Log.d("pos yy ",""+posY);
    }

    public void move(int fingerPosY){
        if(fingerPosY > height/2 && fingerPosY < screenSize.y - height/2)
            posY = fingerPosY - height/2;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void reset() {
        posY = screenSize.y / 2 - height / 2;
    }
}
