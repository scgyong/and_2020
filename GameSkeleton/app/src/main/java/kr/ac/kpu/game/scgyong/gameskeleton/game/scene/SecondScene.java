package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Ball;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.CityBackground;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private Ball ball;
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
        initObjects();
    }

    private void initObjects() {
        timer = new GameTimer(10, 1);
        Random rand = new Random();
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        ball = new Ball(cx, cy, 0, 0);
        gameWorld.add(Layer.enemy.ordinal(), ball);
        gameWorld.add(Layer.bg.ordinal(), new CityBackground());
    }
}
