package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.GameWorld;

public class Plane implements GameObject {
    private static final float ANGLE_PER_SECOND = 90;
    private static final String TAG = Plane.class.getSimpleName();
    private static final long BULLET_FIRE_INTERVAL_NSEC = 100000000;
    private static final int INITIAL_BULLET_POWER = 100;
    private static Bitmap image;
    private static int radius;
    private long lastFire;
    private int size;
    private float x, y;
    private int power;
//    private Matrix matrix;

    public Plane() {
        GameWorld gw = GameWorld.get();
        if (image == null) {
            image = BitmapFactory.decodeResource(gw.getResources(), R.mipmap.plane_240);
            radius = image.getWidth() / 2;
        }
        this.x = 0;
        this.y = 0;
        this.power = INITIAL_BULLET_POWER;

        placePlane();

        this.lastFire = gw.getCurrentTimeNanos();
    }

    public void placePlane() {
        GameWorld gw = GameWorld.get();
        if (!gw.sizeDetermined()) {
            return;
        }
        this.x = (gw.getRight() + gw.getLeft()) / 2;
        this.y = gw.getBottom() - radius;
    }

    public void fire() {
//        float angle = MatrixHelper.getAngle(matrix);
        Bullet bullet = Bullet.get(x, y - radius, power);
        GameWorld.get().add(GameWorld.Layer.missile, bullet);
    }
    @Override
    public void update() {
        GameWorld gw = GameWorld.get();
        long now = gw.getCurrentTimeNanos();
        if (now - lastFire > BULLET_FIRE_INTERVAL_NSEC) {
            fire();
            lastFire = now;
        }
//        float angle = ANGLE_PER_SECOND * GameWorld.get().getTimeDiffInSecond();
//        matrix.postRotate(angle, x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - radius, y - radius, null);
//        canvas.drawText(debug, 20, 20, paint);
    }
//    String debug = "";
//    Paint paint = new Paint();

    public void head(PointF pt) {
        GameWorld gw = GameWorld.get();
        x = pt.x;
        int left = gw.getLeft();
        if (x < left) {
            x = left;
        }

        int right = gw.getRight();
        if (x > right) {
            x = right;
        }
    }
}
