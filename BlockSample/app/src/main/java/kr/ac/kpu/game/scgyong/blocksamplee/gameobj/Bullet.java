package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.CollisionHelper;
import kr.ac.kpu.game.scgyong.blocksamplee.util.FrameAnimationBitmap;
import kr.ac.kpu.game.scgyong.blocksamplee.util.MatrixHelper;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    public static final int SPEED = 500;
    private FrameAnimationBitmap fab;
    private int power;
    private float x, y;

    private Bullet() {
        Log.v(TAG, "new Bullet()");
    }

    public static Bullet get(float x, float y, int power) {
        GameWorld gw = GameWorld.get();
        Bullet bullet = (Bullet) gw.getRecyclePool().get(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet();
            bullet.fab = FrameAnimationBitmap.load(gw.getResources(), R.mipmap.bullet_hadoken, 10, 6);
        }
        bullet.fab.reset();
        bullet.x = x;
        bullet.y = y;
        bullet.power = power;

        return bullet;
    }

    public void update() {
        GameWorld gw = GameWorld.get();

        y -= SPEED * gw.getTimeDiffInSecond();

        boolean toBeDeleted = false;
        ArrayList<GameObject> enemies = gw.objectsAt(GameWorld.Layer.enemy);
        for (GameObject e: enemies) {
            if (!(e instanceof BoxCollidable)) {
                continue;
            }
            BoxCollidable bc = (BoxCollidable) e;
            boolean collides = CollisionHelper.collides(bc, this);
            if (collides) {
                Enemy enemy = (Enemy) bc;
                enemy.decreaseLife(power);
                toBeDeleted = true;
                break;
            }
        }
        if (x > gw.getRight() || x < gw.getLeft()) {
            toBeDeleted = true;
        }
        if (y > gw.getBottom() || y < gw.getTop()) {
            toBeDeleted = true;
        }
        if (toBeDeleted) {
            gw.removeObject(this);
        }

        fab.update();
    }

    public void draw(Canvas canvas) {
//        fab.draw(canvas, matrix);
//        Log.d(TAG, "draw - coord: (" + x + "," + y + ") obj=" + this);
        fab.draw(canvas, x, y);
    }

    @Override
    public void getBox(RectF rect) {
        float halfWidth = fab.getWidth() / 2;
        float halfHeight = fab.getHeight() / 2;
        rect.left = x - halfWidth;
        rect.right = x + halfWidth;
        rect.top = y - halfHeight;
        rect.bottom = y + halfHeight;
    }

    @Override
    public void recycle() {

    }
}
