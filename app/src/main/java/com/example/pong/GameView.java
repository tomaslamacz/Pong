package com.example.pong;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.res.Resources;
import android.widget.Toast;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private boolean gameStarted = false;
    private int gameMode = 0;//0==one player, 1==two players, 2== wall mode
    private MainThread thread;
    private Ball ball;
    private Paddle paddleL;
    private Paddle paddleR;
    private Wall wall;
    private ScoreTable scoreTable;
    private Line line;

    private SparseArray<PointF> activePointers = new SparseArray<PointF>();

    private Point screenSize = new Point(Resources.getSystem().getDisplayMetrics().widthPixels,
                                        Resources.getSystem().getDisplayMetrics().heightPixels);


    public GameView(Context context, int gameMode) {
        super(context);
        this.gameMode = gameMode;
        this.context = context;
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

        scoreTable = new ScoreTable(screenSize,0,0, this.gameMode, this.context);
        line = new Line(screenSize, this.gameMode);

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
        if(ball.getPosX()+ball.getWidth() < 0 || ball.getPosX() > screenSize.x){
            //TODO restart hry
            //ball.set...
        }

        if(ball.getPosY() < 1){//horni hrana
            if(ball.getDirection() == Ball.directions.UP_RIGHT){
                ball.setDirection(Ball.directions.DOWN_RIGHT);
            } else if(ball.getDirection() == Ball.directions.UP_LEFT){
                ball.setDirection(Ball.directions.DOWN_LEFT);
            } else if(ball.getDirection() == Ball.directions.UP_RIGHT_DIAG){
                ball.setDirection(Ball.directions.DOWN_RIGHT_DIAG);
            } else if(ball.getDirection() == Ball.directions.UP_LEFT_DIAG){
                ball.setDirection(Ball.directions.DOWN_LEFT_DIAG);
            }

            ball.adjustSpeed();
        } else if (ball.getPosY() + ball.getHeight() > screenSize.y - 1){//dolni hrana
            if(ball.getDirection() == Ball.directions.DOWN_RIGHT){
                ball.setDirection(Ball.directions.UP_RIGHT);
            } else if(ball.getDirection() == Ball.directions.DOWN_LEFT){
                ball.setDirection(Ball.directions.UP_LEFT);
            } else if(ball.getDirection() == Ball.directions.DOWN_RIGHT_DIAG){
                ball.setDirection(Ball.directions.UP_RIGHT_DIAG);
            } else if(ball.getDirection() == Ball.directions.DOWN_LEFT_DIAG){
                ball.setDirection(Ball.directions.UP_LEFT_DIAG);
            }

            ball.adjustSpeed();
        }

        //odraz od levého pádla:
        if(ball.getPosX() >= screenSize.x * 1/10 - paddleL.getWidth() && ball.getPosX() <= screenSize.x * 1/10){


            if(ball.getPosY() + ball.getHeight() > paddleL.getPosY() && ball.getPosY() < paddleL.getPosY() + paddleL.getHeight()){
                //vypocteme, na kterou petinu padla dopadl stred micku
                int ctvrtinaPadla = paddleL.getHeight() / 4;//velikost petiny padla
                int ctvrtinaDopadu = ((ball.getPosY() + ball.getHeight()/2) - paddleL.getPosY()) / ctvrtinaPadla;

                if (ctvrtinaDopadu == 0){
                    ball.setDirection(Ball.directions.UP_RIGHT_DIAG);
                } else if (ctvrtinaDopadu == 1){
                    ball.setDirection(Ball.directions.UP_RIGHT);
                } else if (ctvrtinaDopadu == 2){
                    ball.setDirection(Ball.directions.DOWN_RIGHT);
                } else/* if (petinaDopadu == 4)*/{
                    ball.setDirection(Ball.directions.DOWN_RIGHT_DIAG);
                }
                Log.i("petina",""+ctvrtinaDopadu);



            } else{//netrefil
                scoreTable.setRightPlayerScore(scoreTable.getRightPlayerScore()+1);

                reset();
            }
        }

        //odraz od pravého pádla:
        if(paddleR != null && ball.getPosX() + ball.getWidth() >= screenSize.x * 9/10 && ball.getPosX() + ball.getWidth() <= screenSize.x * 9/10 + paddleR.getWidth()){

            if(ball.getPosY() + ball.getHeight() > paddleR.getPosY() && ball.getPosY() < paddleR.getPosY() + paddleR.getHeight()){
                //vypocteme, na kterou petinu padla dopadl stred micku
                int ctvrtinaPadla = paddleR.getHeight() / 4;//velikost petiny padla
                int ctvrtinaDopadu = ((ball.getPosY() + ball.getHeight()/2) - paddleR.getPosY()) / ctvrtinaPadla;

                if (ctvrtinaDopadu == 0){
                    ball.setDirection(Ball.directions.UP_LEFT_DIAG);
                } else if (ctvrtinaDopadu == 1){
                    ball.setDirection(Ball.directions.UP_LEFT);
                } else if (ctvrtinaDopadu == 2){
                    ball.setDirection(Ball.directions.DOWN_LEFT);
                } else/* if (petinaDopadu == 4)*/{
                    ball.setDirection(Ball.directions.DOWN_LEFT_DIAG);
                }
                Log.i("petinar",""+ctvrtinaDopadu);



            } else{//netrefil
                scoreTable.setLeftPlayerScore(scoreTable.getLeftPlayerScore()+1);
                reset();
            }
        }

        //odraz od zdi:
        if(wall != null){
            if(ball.getPosX() + ball.getWidth() >= screenSize.x * 9/10){
                if(ball.getDirection() == Ball.directions.UP_RIGHT_DIAG){
                    ball.setDirection(Ball.directions.UP_LEFT_DIAG);
                } else if(ball.getDirection() == Ball.directions.UP_RIGHT){
                    ball.setDirection(Ball.directions.UP_LEFT);
                } else if(ball.getDirection() == Ball.directions.DOWN_RIGHT){
                    ball.setDirection(Ball.directions.DOWN_LEFT);
                } else if(ball.getDirection() == Ball.directions.DOWN_RIGHT_DIAG){
                    ball.setDirection(Ball.directions.DOWN_LEFT_DIAG);
                }

                scoreTable.setLeftPlayerScore(scoreTable.getLeftPlayerScore()+1);
                //ball.setDirection(Ball.directions.DOWN_LEFT);
            }
        }



        if (gameStarted){
            ball.update();
        }

        paddleL.update();

        if(paddleR != null)
            paddleR.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            line.draw(canvas);
            ball.draw(canvas);
            paddleL.draw(canvas);
            if(paddleR != null)
                paddleR.draw(canvas);
            if(wall != null)
                wall.draw(canvas);

            scoreTable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();// get pointer index from the event object
        int pointerId = event.getPointerId(pointerIndex);// get pointer ID
        int maskedAction = event.getActionMasked();// get masked (not specific to a pointer) action
        switch (maskedAction) {
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers

                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                activePointers.put(pointerId, f);
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (!gameStarted)
                    gameStarted = true;

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if(gameMode == 1){
                    for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                        PointF point = activePointers.get(event.getPointerId(i));
                        if (point != null) {//multi touch move
                            point.x = event.getX(i);
                            point.y = event.getY(i);

                            if (point.x < screenSize.x / 2)
                                paddleL.move((int) point.y);
                            else {
                                paddleR.move((int) point.y);
                            }
                        } else{//single touch move
                            if (event.getX() < screenSize.x / 2)
                                paddleL.move((int) event.getY());
                            else {
                                paddleR.move((int) event.getY());
                            }
                        }
                    }
                } else{//gamemode je 0 nebo 2
                    paddleL.move((int) event.getY());
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                activePointers.remove(pointerId);
                break;
            }
        }

        return super.onTouchEvent(event);
    }

    public void resume(){
        Log.d("pr","resume");
    }
    public void pause(){
        Log.d("pr","pause");

    }



    public void reset(){
        ball.reset();
        paddleL.reset();
        if(paddleR != null)
            paddleR.reset();

    }


}
