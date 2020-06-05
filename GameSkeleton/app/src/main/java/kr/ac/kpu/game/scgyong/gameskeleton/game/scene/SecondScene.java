package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.input.sensor.GyroSensor;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.scgyong.gameskeleton.game.map.TextMap;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Cookie;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.HorzScrollBackground;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();
    private TextMap map;
    private int mdpi_100;

    public enum Layer {
        bg, platform, item, obstacle, player, ui, COUNT
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
        float dx = -2 * mdpi_100 * GameTimer.getTimeDiffSeconds();
        map.update(dx);
        for (int layer = Layer.platform.ordinal(); layer <= Layer.obstacle.ordinal(); layer++) {
            ArrayList<GameObject> objects = gameWorld.objectsAtLayer(layer);
            for (GameObject obj : objects) {
                obj.move(dx, 0);
            }
        }
    }

    @Override
    public void enter() {
        super.enter();
//        GyroSensor.get();
        initObjects();
        map = new TextMap("stage_01.txt", gameWorld);
    }

    @Override
    public void exit() {
//        GyroSensor.get().destroy();
        super.exit();
    }

    private void initObjects() {
        timer = new GameTimer(60, 1);
        Random rand = new Random();
        mdpi_100 = UiBridge.x(100);
        Log.d(TAG, "mdpi_100: " + mdpi_100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        cookie = new Cookie(mdpi_100, sh - mdpi_100);
        gameWorld.add(Layer.player.ordinal(), cookie);
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1, ImageScrollBackground.Orientation.horizontal, -100));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_2, ImageScrollBackground.Orientation.horizontal, -200));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_3, ImageScrollBackground.Orientation.horizontal, -300));
    }
}
