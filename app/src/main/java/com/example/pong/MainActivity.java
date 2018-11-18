package com.example.pong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tvHighscore;
    private TextView tvPong;
    private Button btnOneP;
    private Button btnTwoP;
    private Button btnWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fonty funguji pouze po pridani programove:
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/press_start_2p.ttf");
        tvHighscore = (TextView) findViewById(R.id.textView4);
        tvPong = (TextView) findViewById(R.id.textView5);
        btnOneP = (Button) findViewById(R.id.btn_one_player);
        btnTwoP = (Button) findViewById(R.id.btn_two_players);
        btnWall = (Button) findViewById(R.id.btn_wall_mode);

        tvHighscore.setTypeface(tf);
        tvPong.setTypeface(tf);
        btnOneP.setTypeface(tf);
        btnTwoP.setTypeface(tf);
        btnWall.setTypeface(tf);

        SharedPreferences prefs = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        tvHighscore.setText("High score: " + highScore);

    }

    public void onePlayerOnClick(android.view.View view){
        Intent onePlayerActivity = new Intent(getBaseContext(), com.example.pong.OnePlayerActivity.class);
        startActivity(onePlayerActivity);
    }

    public void twoPlayersOnClick(android.view.View view){
        Intent twoPlayersActivity = new Intent(getBaseContext(), com.example.pong.TwoPlayersActivity.class);
        startActivity(twoPlayersActivity);
    }

    public void WallOnePlayerOnClick(android.view.View view){
        Intent wallOnePlayerActivity = new Intent(getBaseContext(), com.example.pong.WallOnePlayerActivity.class);
        startActivity(wallOnePlayerActivity);
    }


}
