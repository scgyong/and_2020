package kr.ac.kpu.game.scgyong.blocksamplee.game.world;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.game.obj.Ball;
import kr.ac.kpu.game.scgyong.blocksamplee.game.obj.Fighter;
import kr.ac.kpu.game.scgyong.blocksamplee.game.obj.Plane;
import kr.ac.kpu.game.scgyong.blocksamplee.game.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.scgyong.blocksamplee.game.obj.bg.TileScrollBackground;

public class World extends GameWorld {
    private static final int BALL_COUNT = 10;
    private static final String TAG = World.class.getSimpleName();
    private Fighter fighter;
    private Plane plane;
    private EnemyGenerator enemyGenerator;

    public enum Action {fireBullet, fireHadoken}

    public void doAction(Action action, Object param) {
//        Log.d(TAG, "doAction() " + action);
        switch (action) {
            case fireHadoken:
                fighter.shoot();
                break;
            case fireBullet:
                plane.move((PointF)param);
//                plane.fire();
                break;
        }
    }

    protected void initObjects() {
        Random rand = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            int x = rand.nextInt(1000);
            int y = rand.nextInt(1000);
            int dx = rand.nextInt(1000) - 500; if (dx >= 0) dx++;
            int dy = rand.nextInt(1000) - 500; if (dy >= 0) dy++;
            add(Layer.enemy, new Ball(view, x, y, dx, dy));
        }
        plane = new Plane();
        add(Layer.player, plane);

        fighter = new Fighter(view, 200, 700, 0, 0);
        add(Layer.player, fighter);

        enemyGenerator = new EnemyGenerator();

//        add(Layer.bg, new ImageScrollBackground(
//                R.mipmap.bg_city, ImageScrollBackground.Orientation.vertical, 25));
        add(Layer.bg, new TileScrollBackground(
                R.mipmap.bg_city, TileScrollBackground.Orientation.vertical, -25));
        add(Layer.bg, new ImageScrollBackground(
                R.mipmap.clouds, ImageScrollBackground.Orientation.vertical, 100));
    }

    @Override
    public void update(long frameTimeNanos) {
        super.update(frameTimeNanos);
        enemyGenerator.update();
    }

    private PointF pt = new PointF();
    private PointF ptDown = new PointF();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doAction(Action.fireHadoken, null);
                ptDown.x = event.getX();
                ptDown.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                pt.x = x - ptDown.x;
                pt.y = y - ptDown.y;
                doAction(Action.fireBullet, pt);
                ptDown.x = x;
                ptDown.y = y;
                break;
        }

        return false;
    }

    public static void create() {
        if (singleton != null) {
            Log.e(TAG, "Object already created. bug?");
            return;
        }
        singleton = new World();
    }
}
