package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.scgyong.blocksamplee.res.sound.SoundEffects;

public class Fighter implements GameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 10;
    private static final int SHOOT_FRAME_COUNT = 5;
    private final FrameAnimationBitmap fabIdle;
    private final FrameAnimationBitmap fabShoot;
//    private final MediaPlayer mediaPlayer;
    private final int shootOffset;
    private final ObjectAnimator objectAnimator;
    private int x, y, dx, dy;
    private Interpolator interpolator = new DecelerateInterpolator();

    public void setScale(float scale) {
//        Log.d(TAG, "Scale = " + scale);
        this.scale = scale;
    }

    //    private Matrix matrix;
    private float scale = 1.0f;


    public Fighter(View view, int x, int y, int dx, int dy) {
        fabIdle = new FrameAnimationBitmap(R.mipmap.ryu, FRAMES_PER_SECOND, 0);
        fabShoot = new FrameAnimationBitmap(R.mipmap.ryu_1, 5, SHOOT_FRAME_COUNT);
        this.shootOffset = fabIdle.getHeight() * 32 / 100;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        objectAnimator = ObjectAnimator.ofFloat(this, "scale", 1.0f, 2.0f);
        objectAnimator.setInterpolator(interpolator);
//        this.mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.hadouken);
    }

    private enum State {
        idle, shoot
    }
    private State state = State.idle;
    public void update() {
        if (state == State.idle) {
        } else {
            boolean done = fabShoot.done();
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
            canvas.save();
            canvas.scale(scale, scale, x + shootOffset, y);
            fabShoot.draw(canvas, x + shootOffset, y);
            canvas.restore();
        }
    }

    public void shoot() {
//        Log.d(TAG, "shoot()");
        if (state == State.idle) {
//            Log.d(TAG, "changing state to shoot");
            objectAnimator.setDuration(1000);
            objectAnimator.start();
            state = State.shoot;
            fabShoot.reset();
//            kr.ac.kpu.game.scgyong.blocksamplee.res.sound.SoundEffects s;.
//            mediaPlayer.seekTo(0);
//            mediaPlayer.start();
            SoundEffects.get().play(R.raw.hadouken);
        }
    }
}
