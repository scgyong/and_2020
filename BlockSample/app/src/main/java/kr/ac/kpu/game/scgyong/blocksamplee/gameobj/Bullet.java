package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.FrameAnimationBitmap;
import kr.ac.kpu.game.scgyong.blocksamplee.util.MatrixHelper;

public class Bullet implements GameObject {
    private static final String TAG = Bullet.class.getSimpleName();
    public static final int SPEED = 500;
    private final FrameAnimationBitmap fab;
    private final Matrix matrix;
    private final float angle;
    private float x, y;

    public Bullet(View view, float x, float y, float angle, int radius) {
        fab = FrameAnimationBitmap.load(view.getResources(), R.mipmap.bullet, 1, 1);
        this.matrix = new Matrix();
        matrix.preTranslate(x, y);
        this.x = x;
        this.y = y;
        this.angle = (float) (angle + Math.PI / 2);
        matrix.postRotate(-(float) (angle * 180 / Math.PI), x, y);

        advance(radius);
    }

    private void advance(float amount) {
        float dx = (float) (Math.cos(angle) * amount);
        float dy = -(float) (Math.sin(angle) * amount);
        matrix.postTranslate(dx, dy);
        x += dx;
        y += dy;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        float timeDiffInSecond = gw.getTimeDiffInSecond();
        float amount = SPEED * timeDiffInSecond;
        advance(amount);
        boolean toBeDeleted = false;
        if (x > gw.getRight() || x < gw.getLeft()) {
            toBeDeleted = true;
        }
        if (y > gw.getBottom() || y < gw.getTop()) {
            toBeDeleted = true;
        }
        if (toBeDeleted) {
            gw.removeObject(this);
        }
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, matrix);
    }
}
