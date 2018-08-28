package com.huaxiaobin.smalldinosaurapp.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.huaxiaobin.smalldinosaurapp.R;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏显示
        init();
    }

    private void init() {
        gameView = findViewById(R.id.game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause_game();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.destroy_game();
    }
}
