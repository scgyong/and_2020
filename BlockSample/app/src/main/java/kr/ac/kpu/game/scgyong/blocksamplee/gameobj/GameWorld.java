package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {

    private static final String TAG = GameWorld.class.getSimpleName();
    private static final int BALL_COUNT = 10;
    private Fighter fighter;
    private long currentTimeNanos;
    private long timeDiffNanos;
    private Plane plane;
    private EnemyGenerator enemyGenerator;

    public float getDotsPerInch() {
        return dotsPerInch;
    }

    private float dotsPerInch;

    public int getLeft() {
        return rect.left;
    }
    public int getTop() {
        return rect.top;
    }
    public int getRight() {
        return rect.right;
    }
    public int getBottom() {
        return rect.bottom;
    }

    private Rect rect;

    public static GameWorld get() {
        if (singleton == null) {
            singleton = new GameWorld();
        }
        return singleton;
    }

    public View getView() {
        return view;
    }

    public void init(View view) {
        this.view = view;

        dotsPerInch = view.getResources().getDisplayMetrics().xdpi;

        initLayers();
        initObjects();
    }

    public long getCurrentTimeNanos() {
        return currentTimeNanos;
    }

    public long getTimeDiffNanos() {
        return timeDiffNanos;
    }
    public float getTimeDiffInSecond() {
        return timeDiffNanos / 1000000000f;
    }


    public void update(long frameTimeNanos) {
        this.timeDiffNanos = frameTimeNanos - currentTimeNanos;
        this.currentTimeNanos = frameTimeNanos;

        if (rect == null) return;

        for (ArrayList<GameObject> layer: layers) {
            for (GameObject o: layer) {
                o.update();
            }
        }
        if (trash.size() > 0) {
            removeTrashObjects();
        }

        enemyGenerator.update();
    }
    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> layer: layers) {
            for (GameObject o: layer) {
                o.draw(canvas);
            }
        }
    }

    private ArrayList<ArrayList<GameObject>> layers;
    private void initLayers() {
        layers = new ArrayList<ArrayList<GameObject>>();
        for (int i = 0; i < Layer.COUNT.ordinal(); i++) {
            Log.d(TAG, "Adding layer " + i + " - " + Layer.values()[i]);
            layers.add(new ArrayList<GameObject>());
        }
    }

    private ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }
    public void add(Layer layer, GameObject obj) {
        objectsAt(layer).add(obj);
    }

    private View view;

    public void onSize(Rect rect) {
        this.rect = rect;
    }

    public void doAction(Action action, Object param) {
        Log.d(TAG, "doAction() " + action);
        switch (action) {
            case fireHadoken:
                fighter.shoot();
                break;
            case fireBullet:
                plane.head((PointF)param);
                plane.fire();
                break;
        }
    }

    private ArrayList<GameObject> trash = new ArrayList<>();
    public void removeObject(GameObject obj) {
        trash.add(obj);
    }

    private void removeTrashObjects() {
        for (GameObject obj : trash) {
            for (ArrayList<GameObject> layer : layers) {
                int index = layer.indexOf(obj);
                if (index >= 0) {
                    Log.d(TAG, "Removing obj at index: " + index + " / " + layer.size());
                    layer.remove(index);
                    break;
                }
            }
        }
    }

    public Resources getResources() {
        return view.getResources();
    }

    public enum Layer {
        bg, missile, enemy, player, COUNT
    }

    private static GameWorld singleton;
    private GameWorld() {}

    private void initObjects() {
        Random rand = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            int x = rand.nextInt(1000);
            int y = rand.nextInt(1000);
            int dx = rand.nextInt(1000) - 500; if (dx >= 0) dx++;
            int dy = rand.nextInt(1000) - 500; if (dy >= 0) dy++;
            add(Layer.enemy, new Ball(view, x, y, dx, dy));
        }
        plane = new Plane(view, 500, 500);
        add(Layer.player, plane);

        fighter = new Fighter(view, 200, 700, 0, 0);
        add(Layer.player, fighter);

        enemyGenerator = new EnemyGenerator();
    }

    public enum Action {fireBullet, fireHadoken}
}
