package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class FireBall implements GameObject {
    private static final String TAG = FireBall.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 10;
    private static final int FLY_FRAME_COUNT = 6;
    private final FrameAnimationBitmap fabStart;
    private final FrameAnimationBitmap fabFly;
    private int x, y, dx, dy;
    public FireBall(View view, int x, int y, int dx, int dy) {
        fabStart = new FrameAnimationBitmap(R.mipmap.hadoken1, FRAMES_PER_SECOND, 0);
        fabFly = new FrameAnimationBitmap(R.mipmap.hadoken2, FRAMES_PER_SECOND, FLY_FRAME_COUNT);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    private enum State {
        start, fly
    }
    private State state = State.start;
    public void update() {
        x += dx;
        GameWorld gw = GameWorld.get();
        if (x > gw.getRight()) {
            gw.removeObject(this);
            return;
        }
        if (state == State.start) {
            boolean done = fabStart.done();
            if (done) {
                state = State.fly;
                fabFly.reset();
            }
        } else {
//            fabFly.update();
        }
    }

    public void draw(Canvas canvas) {
        if (state == State.start) {
            fabStart.draw(canvas, x, y);
        } else {
            fabFly.draw(canvas, x, y);
        }
    }

    public void shoot() {
        Log.d(TAG, "fly()");
        if (state == State.start) {
            Log.d(TAG, "changing state to fly");
            state = State.fly;
            fabFly.reset();
        }
    }
}
