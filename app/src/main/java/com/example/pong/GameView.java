package com.example.pong;

import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.res.Resources;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private int gameMode = 0;
    private MainThread thread;
    private Ball ball;

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
        ball.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            ball.draw(canvas);
        }
    }
}