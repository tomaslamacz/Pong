package com.example.pong;

import android.app.Activity;
import android.os.Bundle;

public class OnePlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new GameView(this, 0));

    }

}
