package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class Ball implements GameObject {
    private static final String TAG = Ball.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private final FrameAnimationBitmap fab;
    private final int halfImageWidth;
    private float x, y, dx, dy;
    public Ball(View view, int x, int y, int dx, int dy) {
        fab = new FrameAnimationBitmap(R.mipmap.fireball_128_24f, FRAMES_PER_SECOND, 0);
        this.halfImageWidth = fab.getHeight() / 4;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        long nanos = gw.getTimeDiffNanos();
        x += dx * nanos / 1000000000;
//        Log.d(TAG, "dx=" + dx + " nanos=" + nanos + " x=" + x);
        if (dx > 0 && x > gw.getRight() - halfImageWidth || dx < 0 && x < gw.getLeft() + halfImageWidth) {
            dx *= -1;
        }
        y += dy * nanos / 1000000000;
        if (dy > 0 && y > gw.getBottom() - halfImageWidth || dy < 0 && y < gw.getTop() + halfImageWidth) {
            dy *= -1;
        }
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }
}
