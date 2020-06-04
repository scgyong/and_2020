package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.input.sensor.GyroSensor;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Ball;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.CityBackground;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.GyroBall;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private GyroBall ball;
    private GameTimer timer;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
        if (timer.done()) {
            pop();
        }

    }

    @Override
    public void enter() {
        super.enter();
        GyroSensor.get();
        initObjects();
    }

    @Override
    public void exit() {
        GyroSensor.get().destroy();
        super.exit();
    }

    private void initObjects() {
        timer = new GameTimer(60, 1);
        Random rand = new Random();
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        ball = new GyroBall(cx, cy);
        gameWorld.add(Layer.enemy.ordinal(), ball);
        gameWorld.add(Layer.bg.ordinal(), new CityBackground());
    }
}
