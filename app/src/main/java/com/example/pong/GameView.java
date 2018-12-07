package com.example.pong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
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
    private boolean gameEnded = false;
    private int gameMode = 0;//0==one player, 1==two players, 2== wall mode
    private MainThread thread;
    private Ball ball;
    private Ball clone;
    private Paddle paddleL;
    private Paddle paddleR;
    private Wall wall;
    private ScoreTable scoreTable;
    private GameEndText geText;
    private Line line;
    private boolean scoreSaved = false;
    private SharedPreferences prefs;
    private int sound;
    private MediaPlayer paddleMPlayer;
    private MediaPlayer missMPlayer;
    private MediaPlayer wallMPlayer;


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

        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        sound = prefs.getInt("sound", 1);

        paddleMPlayer = MediaPlayer.create(context, R.raw.paddle);
        missMPlayer = MediaPlayer.create(context, R.raw.miss);
        wallMPlayer = MediaPlayer.create(context, R.raw.wall);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        ball = new Ball(screenSize);
        clone = new Ball(screenSize);
        paddleL = new Paddle(screenSize, false);

        if(gameMode == 0 || gameMode == 1){
            paddleR = new Paddle(screenSize, true);
        } else if(gameMode == 2){
            wall = new Wall(screenSize);
        }

        scoreTable = new ScoreTable(screenSize,0,0, gameMode, context);
        geText  = new GameEndText(screenSize,context);
        line = new Line(screenSize, gameMode);

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
        //odraz od levého pádla:
        if(ball.getPosX() >= screenSize.x * 1/10 - paddleL.getWidth() && ball.getPosX() <= screenSize.x * 1/10){

            if(ball.getPosY() + ball.getHeight() > paddleL.getPosY() && ball.getPosY() < paddleL.getPosY() + paddleL.getHeight()){//trefil
                if(sound==1){

                    paddleMPlayer.start();
                }


                //vypocteme, na kterou ctvrtinu padla dopadl stred micku
                int ctvrtinaPadla = paddleL.getHeight() / 4;//velikost ctvrtiny padla
                int ctvrtinaDopadu = ((ball.getPosY() + ball.getHeight()/2) - paddleL.getPosY()) / ctvrtinaPadla;

                if (ctvrtinaDopadu == 0){
                    ball.setDirection(Ball.directions.UP_RIGHT_DIAG);
                } else if (ctvrtinaDopadu == 1){
                    ball.setDirection(Ball.directions.UP_RIGHT);
                } else if (ctvrtinaDopadu == 2){
                    ball.setDirection(Ball.directions.DOWN_RIGHT);
                } else {
                    ball.setDirection(Ball.directions.DOWN_RIGHT_DIAG);
                }
                Log.i("ctvrtina",""+ctvrtinaDopadu);


                //umela inteligence: (https://gamedev.stackexchange.com/questions/124037/how-to-make-pong-ai-paddle)
                if(gameMode == 0){
                    clone.copy(ball);

                    int distanceX = 0;
                    do {
                        distanceX = Math.abs(clone.getPosX() - paddleR.getPosX());

                        clone.update();

                        Log.i("dy",distanceX+"");
                    } while (distanceX > 100);

                    int tolerance = (int) (Math.random() * 200 - 100);
                    Log.i("tlr",tolerance+"");
                    paddleR.setDesiredPosY(clone.getPosY()+clone.getHeight()/2+tolerance);



                }

            } else{//netrefil

                if(sound==1) {
                    missMPlayer.start();
                }

                scoreTable.setRightPlayerScore(scoreTable.getRightPlayerScore()+1);
                gameEnded = true;
                reset();
            }
        }

        //odraz od pravého pádla:
        if(paddleR != null && ball.getPosX() + ball.getWidth() >= screenSize.x * 9/10 && ball.getPosX() + ball.getWidth() <= screenSize.x * 9/10 + paddleR.getWidth()){

            if(ball.getPosY() + ball.getHeight() > paddleR.getPosY() && ball.getPosY() < paddleR.getPosY() + paddleR.getHeight()){//trefil

                if(sound==1) {
                    paddleMPlayer.start();
                }

                //vypocteme, na kterou ctvrtinu padla dopadl stred micku
                int ctvrtinaPadla = paddleR.getHeight() / 4;//velikost cvtvrtiny padla
                int ctvrtinaDopadu = ((ball.getPosY() + ball.getHeight()/2) - paddleR.getPosY()) / ctvrtinaPadla;

                if (ctvrtinaDopadu == 0){
                    ball.setDirection(Ball.directions.UP_LEFT_DIAG);
                } else if (ctvrtinaDopadu == 1){
                    ball.setDirection(Ball.directions.UP_LEFT);
                } else if (ctvrtinaDopadu == 2){
                    ball.setDirection(Ball.directions.DOWN_LEFT);
                } else {
                    ball.setDirection(Ball.directions.DOWN_LEFT_DIAG);
                }

            } else{//netrefil

                if(sound==1) {
                    missMPlayer.start();
                }

                scoreTable.setLeftPlayerScore(scoreTable.getLeftPlayerScore()+1);
                gameEnded = true;
                reset();
            }
        }

        //odraz od zdi:
        if(wall != null){
            if(ball.getPosX() + ball.getWidth() >= screenSize.x * 9/10){

                if(sound==1) {
                    wallMPlayer.start();
                }

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

            }

        }

        if (gameStarted && !gameEnded){
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

            if(gameEnded && gameMode == 2) {
                if (!scoreSaved){//je tu proto, aby se pres high score text neprekreslil text your score
                    //SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    int highScore = prefs.getInt("highScore", 0); //0 is the default value

                    if(scoreTable.getLeftPlayerScore() > highScore){
                        geText.setText("High score: " + scoreTable.getLeftPlayerScore());


                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("highScore", scoreTable.getLeftPlayerScore());
                        editor.commit();
                    }
                    else
                        geText.setText("Your score: " + scoreTable.getLeftPlayerScore());

                    scoreSaved = true;

                }

                canvas.drawColor(Color.BLACK);
                geText.draw(canvas);

            }
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

                if(gameEnded && gameMode == 2){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if(gameEnded && gameMode != 2){
                    reset();
                    gameEnded = false;
                }


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
