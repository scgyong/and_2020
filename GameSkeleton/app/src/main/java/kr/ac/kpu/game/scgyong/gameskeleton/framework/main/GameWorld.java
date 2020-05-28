package kr.ac.kpu.game.scgyong.gameskeleton.framework.main;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.gameskeleton.framework.iface.Recyclable;

public class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();
    protected RecyclePool recyclePool;
    protected ArrayList<ArrayList<GameObject>> layers;
    protected ArrayList<GameObject> trash = new ArrayList<>();

    public GameWorld(int layerCount) {
        layers = new ArrayList<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            Log.d(TAG, "Adding layer " + i);
            layers.add(new ArrayList<GameObject>());
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public void update() {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        if (trash.size() > 0) {
            clearTrash();
        }
    }

    public void add(final int layerIndex, final GameObject obj) {
        UiBridge.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layerIndex);
                objects.add(obj);
            }
        });
    }
    private void clearTrash() {
        UiBridge.post(new Runnable() {
            @Override
            public void run() {
                for (int ti = trash.size() - 1; ti >= 0; ti--) {
                    GameObject o = trash.get(ti);
                    for (ArrayList<GameObject> objects: layers) {
                        int i = objects.indexOf(o);
                        if (i >= 0) {
                            objects.remove((i));
                            break;
                        }
                    }
                    trash.remove(ti);
                    if (o instanceof Recyclable) {
                        ((Recyclable) o).recycle();
                        recyclePool.add(o);
                    }
                }
            }
        });
    }

    public RecyclePool getRecyclePool() {
        return recyclePool;
    }
}
