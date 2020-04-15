package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.FrameAnimationBitmap;

public class Fighter implements GameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 10;
    private static final int SHOOT_FRAME_COUNT = 5;
    private final FrameAnimationBitmap fabIdle;
    private final FrameAnimationBitmap fabShoot;
    private final int shootOffset;
    private int x, y, dx, dy;

    public Fighter(View view, int x, int y, int dx, int dy) {
        fabIdle = FrameAnimationBitmap.load(view.getResources(), R.mipmap.ryu, FRAMES_PER_SECOND, 0);
        fabShoot = FrameAnimationBitmap.load(view.getResources(), R.mipmap.ryu_1, FRAMES_PER_SECOND, SHOOT_FRAME_COUNT);
        this.shootOffset = fabIdle.getHeight() * 32 / 100;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    private enum State {
        idle, shoot
    }
    private State state = State.idle;
    public void update() {
        if (state == State.idle) {
            fabIdle.update();
        } else {
            boolean done = fabShoot.update();
            if (done) {
                state = State.idle;
                fabIdle.reset();
                addFireball();
            }
        }
    }

    private void addFireball() {
        int height = fabIdle.getHeight();
        int fx = x + height * 80 / 100;
        int fy = y - height * 10 / 100;
        int speed = height / 20;
        GameWorld gw = GameWorld.get();
        FireBall fb = new FireBall(gw.getView(), fx, fy, speed, 0);
        gw.add(GameWorld.Layer.missile, fb);
    }

    public void draw(Canvas canvas) {
        if (state == State.idle) {
            fabIdle.draw(canvas, x, y);
        } else {
            fabShoot.draw(canvas, x + shootOffset, y);
        }
    }

    public void shoot() {
        Log.d(TAG, "shoot()");
        if (state == State.idle) {
            Log.d(TAG, "changing state to shoot");
            state = State.shoot;
            fabShoot.reset();
        }
    }
}
