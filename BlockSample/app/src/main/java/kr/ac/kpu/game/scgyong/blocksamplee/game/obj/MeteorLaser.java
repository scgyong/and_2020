package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.BoxCollidable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.Recyclable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.World;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class MeteorLaser implements GameObject, Recyclable {
    private static final String TAG = MeteorLaser.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private static final int SPEED = 10000;
    private static final int REPEAT = 3;
    public static final int FRAME_COUNT = 1;
    public static final long METEOR_CREATION_INTERVAL = 3_000_000_000L;
    private FrameAnimationBitmap fab;
    private float x, y, dy;
    private int height;
    private int count;
    private long meteorTime;

    private MeteorLaser() {
        Log.v(TAG, "new MeteorLaser() " + this);
    }
    public static MeteorLaser get(float x) {
        GameWorld gw = GameWorld.get();
        MeteorLaser enemy = (MeteorLaser) gw.getRecyclePool().get(MeteorLaser.class);
        if (enemy == null) {
            enemy = new MeteorLaser();
            enemy.fab = new FrameAnimationBitmap(R.mipmap.laser_big, FRAMES_PER_SECOND, FRAME_COUNT);
            enemy.height = enemy.fab.getHeight();
            enemy.dy = -SPEED;
        }
        enemy.count = REPEAT;
        enemy.x = x;
        enemy.y = gw.getBottom() * 2;

        return enemy;
    }

    @Override
    public void recycle() {

    }

    public void update() {
        World gw = World.get();
        if (count <= 0) {
            if (gw.getCurrentTimeNanos() > meteorTime) {
                gw.removeObject(this);
                gw.createMeteor(x);
            }
            return;
        }
        y += dy * gw.getTimeDiffInSecond();;
        if (y < -height) {
            y = gw.getBottom() * 2;
            count--;
            if (count == 0) {
                meteorTime = gw.getCurrentTimeNanos() + METEOR_CREATION_INTERVAL; //;
            }
        }
//        fab.update();
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }

//    @Override
//    public void getBox(RectF rect) {
//        float halfWidth = fab.getWidth() / 2;
//        float halfHeight = fab.getHeight() / 2;
//        rect.left = x - halfWidth;
//        rect.right = x + halfWidth;
//        rect.top = y - halfHeight;
//        rect.bottom = y + halfHeight;
//    }
}
