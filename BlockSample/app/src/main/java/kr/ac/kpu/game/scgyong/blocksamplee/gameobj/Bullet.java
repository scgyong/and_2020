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

public class Bullet implements GameObject, BoxCollidable {
    private static final String TAG = Bullet.class.getSimpleName();
    public static final int SPEED = 500;
    private final FrameAnimationBitmap fab;
    private final int power;
    private float x, y;

    public Bullet(float x, float y, int power) {
        GameWorld gw = GameWorld.get();
        fab = FrameAnimationBitmap.load(gw.getResources(), R.mipmap.bullet_hadoken, 1, 6);
        this.x = x;
        this.y = y;
        this.power = power;
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
}
