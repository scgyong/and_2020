package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.BoxCollidable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.Recyclable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.util.CollisionHelper;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    public static final int SPEED = 1500;
//    public static final int FRAME_COUNT1 = 13;
    public static final int FRAME_COUNT1 = 10;
    public static final int FRAME_COUNT2 = 13;
    public static final int FRAMES_PER_SECOND = 10;
    private FrameAnimationBitmap fab1, fab2;
    private int power;
    private float x, y;
    private int level;
    private FrameAnimationBitmap[] fabs;

    private Bullet() {
        Log.v(TAG, "new Bullet() " + this);
    }

    private static final int INITIAL_BULLET_POWER = 100;
//    private static final int[] POWERS_BY_LEVEL = {
//
//    };
    public static Bullet get(float x, float y, int level) {
        GameWorld gw = GameWorld.get();
        Bullet bullet = (Bullet) gw.getRecyclePool().get(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet();
//            bullet.fab1 = new FrameAnimationBitmap(R.mipmap.metal_slug_missile, FRAMES_PER_SECOND, FRAME_COUNT1);
            bullet.fab1 = new FrameAnimationBitmap(R.mipmap.laser_light, FRAMES_PER_SECOND, FRAME_COUNT1);
            bullet.fab2 = new FrameAnimationBitmap(R.mipmap.metal_slug_missile, FRAMES_PER_SECOND, FRAME_COUNT2);
            bullet.fabs = new FrameAnimationBitmap[] { bullet.fab1, bullet.fab2 };
        }
        bullet.fab1.reset();
        bullet.x = x;
        bullet.y = y;
        bullet.level = level;
        int power = 10 + level * 90;
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

//        fab1.update();
    }

    public void draw(Canvas canvas) {
        int fi = (level - 1) / 5;
        int ll = (level - 1) % 5 + 1;
        if (fi > 1) fi = 1;

        FrameAnimationBitmap fab = fabs[fi];
//        fab1.draw(canvas, matrix);
//        Log.d(TAG, "draw - coord: (" + x + "," + y + ") obj=" + this);
        int bw = fab.getWidth();
//        int width = bw * (4 + level) / 4;
//        int height = fab1.getHeight();
//        fab1.draw(canvas, x, y, width / 2, height / 2);

//        float bx = this.x - width / 2 + bw / 2;
        for (int i = ll - 1; i >= 0; i -= 2) {
            int dx = i * bw / 4;
            fab.draw(canvas, x - dx, y);
            if (i != 0) {
                fab.draw(canvas, x + dx, y);
            }
        }
    }

    @Override
    public void getBox(RectF rect) {
        int bw = fab1.getWidth();
        int width = bw * level;
        float halfWidth = width / 2; // fab1.getWidth() / 2;
        float halfHeight = fab1.getHeight() / 2;
        rect.left = x - halfWidth;
        rect.right = x + halfWidth;
        rect.top = y - halfHeight;
        rect.bottom = y + halfHeight;
    }

    @Override
    public void recycle() {

    }
}
