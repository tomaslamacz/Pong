package com.example.pong;

import android.app.Activity;
import android.os.Bundle;

public class WallOnePlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, 2));
        //setContentView(R.layout.activity_wall_one_player);
    }
}
