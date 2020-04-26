package kr.ac.kpu.game.scgyong.blocksamplee.game.framework;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.Recyclable;

public class GameWorld {

    private static final String TAG = GameWorld.class.getSimpleName();
    protected long currentTimeNanos;
    protected long timeDiffNanos;
    protected RecyclePool recyclePool;

    public float getDotsPerInch() {
        return dotsPerInch;
    }

    protected float dotsPerInch;

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

    protected Rect rect;
    protected Random rand = new Random();

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

        recyclePool = new RecyclePool();
        initLayers();
//        initObjects();

//        Log.d(TAG, "mod = " + (-3 % 10));
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
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> layer: layers) {
            for (GameObject o: layer) {
                o.draw(canvas);
            }
        }
    }

    protected ArrayList<ArrayList<GameObject>> layers;
    private void initLayers() {
        layers = new ArrayList<>();
        for (int i = 0; i < Layer.COUNT.ordinal(); i++) {
            Log.d(TAG, "Adding layer " + i + " - " + Layer.values()[i]);
            layers.add(new ArrayList<GameObject>());
        }
    }
    public int getAllObjectCount() {
        int count = 0;
        for (int i = 0; i < Layer.COUNT.ordinal(); i++) {
            count += layers.get(i).size();
        }
        return count;
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }
    public void add(Layer layer, GameObject obj) {
        objectsAt(layer).add(obj);
    }

    protected View view;

    public void onSize(Rect rect) {
        boolean first = this.rect == null;
        this.rect = rect;
        if (first) {
            initObjects();
        }
//        plane.placePlane();
    }

    protected void initObjects() {
    }

    private ArrayList<GameObject> trash = new ArrayList<>();
    public void removeObject(GameObject obj) {
        trash.add(obj);
    }

    private void removeTrashObjects() {
        // backword traverse
        for (int tIndex = trash.size() - 1; tIndex >= 0; tIndex--) {
            GameObject obj = trash.get(tIndex);
            for (ArrayList<GameObject> layer : layers) {
                int lIndex = layer.indexOf(obj);
                if (lIndex >= 0) {
//                    Log.d(TAG, "Removing obj at index: " + index + " / " + layer.size());
                    layer.remove(lIndex);
                    if (obj instanceof Recyclable) {
                        Recyclable robj = (Recyclable) obj;
                        robj.recycle();
                        recyclePool.add(robj);
                    }
                    trash.remove(tIndex);
                    break;
                }
            }
        }
    }

    public Resources getResources() {
        return view.getResources();
    }

    public boolean sizeDetermined() {
        return rect != null;
    }

    public RecyclePool getRecyclePool() {
        return recyclePool;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public enum Layer {
        bg, missile, enemy, player, ui, COUNT
    }

    protected static GameWorld singleton;
    protected GameWorld() {}

}
