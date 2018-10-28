package com.example.pong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
