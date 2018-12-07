package com.example.pong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tvHighscore;
    private TextView tvPong;
    private Button btnOneP;
    private Button btnTwoP;
    private Button btnWall;
    private TextView tvSound;
    private TextView tvDifficulty;

    private int sound;
    private SharedPreferences prefs;

    private int difficulty;//0=easy,1=hard

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
        tvSound = (TextView) findViewById(R.id.tv_sound);
        tvDifficulty = (TextView) findViewById(R.id.tv_difficulty);

        tvHighscore.setTypeface(tf);
        tvPong.setTypeface(tf);
        btnOneP.setTypeface(tf);
        btnTwoP.setTypeface(tf);
        btnWall.setTypeface(tf);
        tvSound.setTypeface(tf);
        tvDifficulty.setTypeface(tf);


        //SharedPreferences prefs = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        tvHighscore.setText("High score: " + highScore);


        sound = prefs.getInt("sound", 1);
        if (sound == 1){
            tvSound.setText("Sound: ON");
        } else {
            tvSound.setText("Sound: OFF");
        }

        difficulty = prefs.getInt("difficulty", 0);
        if (difficulty == 0){
            tvDifficulty.setText("EASY");
        } else {
            tvDifficulty.setText("HARD");
        }

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


    public void onSoundClick(View view) {
        SharedPreferences.Editor editor = prefs.edit();

        if (sound == 1){
            sound = 0;
            tvSound.setText("Sound: OFF");
        } else {
            sound = 1;
            tvSound.setText("Sound: ON");
        }
        editor.putInt("sound", sound);
        editor.commit();
    }

    public void onDifficultyClick(View view) {
        SharedPreferences.Editor editor = prefs.edit();

        if (difficulty == 0){
            difficulty = 1;
            tvDifficulty.setText("HARD");
        } else {
            difficulty = 0;
            tvDifficulty.setText("EASY");
        }
        editor.putInt("difficulty", difficulty);
        editor.commit();

    }
}
