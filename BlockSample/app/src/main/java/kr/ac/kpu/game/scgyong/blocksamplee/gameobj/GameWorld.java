package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {

    private static final String TAG = GameWorld.class.getSimpleName();
    private static final int BALL_COUNT = 1;

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
    public void init(View view) {
        this.view = view;

        initLayers();
        initObjects();
    }
    public void update() {
        for (ArrayList<GameObject> layer: layers) {
            for (GameObject o: layer) {
                o.update();
            }
        }
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
    private void add(Layer layer, GameObject obj) {
        objectsAt(layer).add(obj);
    }

    private View view;

    public void onSize(Rect rect) {
        this.rect = rect;
    }

    private enum Layer {
        bg, player, missile, enemy, COUNT
    }

    private static GameWorld singleton;
    private GameWorld() {}

    private void initObjects() {
        add(Layer.player, new Plane(view, 500, 500, 0, 0));
        Random rand = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            int x = rand.nextInt(1000);
            int y = rand.nextInt(1000);
            int dx = rand.nextInt(50) - 25; if (dx >= 0) dx++;
            int dy = rand.nextInt(50) - 25; if (dy >= 0) dy++;
            add(Layer.enemy, new Ball(view, x, y, dx, dy));
        }
    }
}
