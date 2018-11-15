package com.example.pong;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.res.Resources;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    boolean gameStarted = false;
    private int gameMode = 0;//0==one player, 1==two players, 2== wall mode
    private MainThread thread;
    private Ball ball;
    private Paddle paddleL;
    private Paddle paddleR;
    private Wall wall;

    private Point screenSize = new Point(Resources.getSystem().getDisplayMetrics().widthPixels,
                                        Resources.getSystem().getDisplayMetrics().heightPixels);


    public GameView(Context context, int gameMode) {
        super(context);

        this.gameMode = gameMode;
        Log.d("this game mode is ", this.gameMode+"");

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        ball = new Ball(screenSize);
        paddleL = new Paddle(screenSize, false);

        if(gameMode == 0 || gameMode == 1){
            paddleR = new Paddle(screenSize, true);
        } else if(gameMode == 2){
            wall = new Wall(screenSize);
        }

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {

        if(ball.getPosY() < 20){//horni hrana
            if(ball.getDirection() == Ball.directions.UP_RIGHT){
                ball.setDirection(Ball.directions.DOWN_RIGHT);
            } else if(ball.getDirection() == Ball.directions.UP_LEFT){
                ball.setDirection(Ball.directions.DOWN_LEFT);
            }
        } else if (ball.getPosY() > screenSize.y - 20){//dolni hrana
            if(ball.getDirection() == Ball.directions.DOWN_RIGHT){
                ball.setDirection(Ball.directions.UP_RIGHT);
            } else if(ball.getDirection() == Ball.directions.DOWN_LEFT){
                ball.setDirection(Ball.directions.UP_LEFT);
            }
        }

        if (gameStarted){
            ball.update();
        }

        paddleL.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            ball.draw(canvas);
            paddleL.draw(canvas);
            if(paddleR != null)
                paddleR.draw(canvas);
            if(wall != null)
                wall.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                if(!gameStarted)
                    gameStarted=true;

                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                paddleL.move((int) event.getY());

                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
