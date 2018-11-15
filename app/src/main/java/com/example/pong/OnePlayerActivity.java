package com.example.pong;

import android.app.Activity;
import android.os.Bundle;

public class OnePlayerActivity extends Activity {
    private GameView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = new GameView(this, 0);
        setContentView(gv);
    }


    @Override
    protected void onResume() {
        super.onResume();

        gv.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        gv.pause();

    }

}
