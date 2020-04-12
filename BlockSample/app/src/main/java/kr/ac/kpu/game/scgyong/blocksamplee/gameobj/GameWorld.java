package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class GameWorld {

    private static final String TAG = GameWorld.class.getSimpleName();
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
        add(Layer.enemy, new Ball(view, 10, 10, 1, 1));
        add(Layer.enemy, new Ball(view, 1000, 10, -2, 3));
    }
}
