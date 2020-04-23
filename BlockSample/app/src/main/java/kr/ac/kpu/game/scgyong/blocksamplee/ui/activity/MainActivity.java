package kr.ac.kpu.game.scgyong.blocksamplee.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.World;
import kr.ac.kpu.game.scgyong.blocksamplee.res.sound.SoundEffects;
import kr.ac.kpu.game.scgyong.blocksamplee.ui.view.GameView;

public class MainActivity extends AppCompatActivity {

    private static final long GAMEVIEW_UPDATE_INTERVAL_MSEC = 30;
    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        gameView = findViewById(R.id.gameView);
        World.create();
        gameView = new GameView(this);
        setContentView(gameView);

//        postUpdate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.v(TAG, "onConfigChange: " + newConfig);
        super.onConfigurationChanged(newConfig);
    }
    //    private void postUpdate() {
//        gameView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                gameView.update();
//                gameView.invalidate();
//                postUpdate();
//            }
//        }, GAMEVIEW_UPDATE_INTERVAL_MSEC);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//        case MotionEvent.ACTION_DOWN:
//            GameWorld.get().doAction(GameWorld.Action.fireHadoken, null);
//            break;
//        case MotionEvent.ACTION_MOVE:
//            pt.x = event.getX();
//            pt.y = event.getY();
//            GameWorld.get().doAction(GameWorld.Action.fireBullet, pt);
//        }
//        return true;
//    }
}
