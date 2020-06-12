package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.scgyong.gameskeleton.game.map.TextMap;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Cookie;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Platform;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();
    private TextMap map;
    private int mdpi_100;

    private RectF rect = new RectF();
    private ScoreObject scoreObject;
    private static SecondScene instance;

    public Platform getPlatformAt(float x, float y) {
        Platform platform = null;
        ArrayList<GameObject> objects = gameWorld.objectsAtLayer(Layer.platform.ordinal());
        for (GameObject obj : objects) {
            if (!(obj instanceof Platform)) {
                continue;
            }
            ((Platform) obj).getBox(rect);
            if (rect.left > x || rect.right < x) {
                continue;
            }
            if (rect.top < y - rect.height() / 2) {
                continue;
            }
            if (platform == null) {
                platform = (Platform) obj;
            } else {
                if (platform.getY() > obj.getY()) {
                    platform = (Platform) obj;
                }
            }
        }
        return platform;
    }

    public enum Layer {
        bg, platform, item, obstacle, player, ui, COUNT
    }

    private Cookie cookie;
//    private GameTimer timer;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
//        if (timer.done()) {
//            pop();
//        }
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
        instance = this;
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
//        timer = new GameTimer(60, 1);
        Random rand = new Random();
        mdpi_100 = UiBridge.x(100);
        Log.d(TAG, "mdpi_100: " + mdpi_100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        cookie = new Cookie(mdpi_100, mdpi_100);
        gameWorld.add(Layer.player.ordinal(), cookie);
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1, ImageScrollBackground.Orientation.horizontal, -100));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_2, ImageScrollBackground.Orientation.horizontal, -200));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_3, ImageScrollBackground.Orientation.horizontal, -300));

        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
        gameWorld.add(SecondScene.Layer.ui.ordinal(), scoreObject);

        Button btnJump = new Button(mdpi_100 / 2, sh - mdpi_100 / 4,
                R.mipmap.btn_jump, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        btnJump.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                cookie.jump();
            }
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), btnJump);
        Button btnSlide = new Button(sw - mdpi_100 / 2, sh - mdpi_100 / 4,
                R.mipmap.btn_slide, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        btnSlide.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                cookie.slide();
            }
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), btnSlide);
    }

    private int nextScore = 500;
    public void addScore(int amount) {
        int score = scoreObject.getScoreValue();
        if (score >= nextScore) {
            // launch DialogScene
            DialogScene scene = new DialogScene();
            scene.push();
            nextScore += 500;
            return;
        }
        scoreObject.add(amount);
    }

    public static SecondScene get() {
        return instance;
    }
}
