package kr.ac.kpu.game.scgyong.gameskeleton.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.kpu.game.scgyong.gameskeleton.framework.input.sensor.GyroSensor;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.view.GameView;
import kr.ac.kpu.game.scgyong.gameskeleton.game.scene.FirstScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        new FirstScene().run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GyroSensor.isCreated()) {
            GyroSensor.get().register();
        }
    }

    @Override
    protected void onPause() {
        if (GyroSensor.isCreated()) {
            GyroSensor.get().unregister();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        GameScene.getTop().onBackPressed();
    }
}
