package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.input.sensor.GyroSensor;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Cookie;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.HorzScrollBackground;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private Cookie cookie;
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
        int mdpi_100 = UiBridge.x(100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        cookie = new Cookie(mdpi_100, sh - mdpi_100);
        gameWorld.add(Layer.enemy.ordinal(), cookie);
        gameWorld.add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_1));
    }
}
