package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.FrameAnimationBitmap;

public class Enemy implements GameObject {
    private static final String TAG = Enemy.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private final FrameAnimationBitmap fab;
    private final int height;
    private float x, y, dx, dy;

    private static int[] RES_IDS = {
            R.mipmap.enemy_01,
            R.mipmap.enemy_02,
            R.mipmap.enemy_03,
            R.mipmap.enemy_04,
            R.mipmap.enemy_05,
            R.mipmap.enemy_06,
            R.mipmap.enemy_07,
            R.mipmap.enemy_08,
            R.mipmap.enemy_09,
            R.mipmap.enemy_10,
            R.mipmap.enemy_11,
            R.mipmap.enemy_12,
            R.mipmap.enemy_13,
            R.mipmap.enemy_14,
            R.mipmap.enemy_15,
            R.mipmap.enemy_16,
            R.mipmap.enemy_17,
            R.mipmap.enemy_18,
            R.mipmap.enemy_19,
    };
    public Enemy(int x, int level, int speed) {
        GameWorld gw = GameWorld.get();
        int resId = RES_IDS[level];
        fab = FrameAnimationBitmap.load(gw.getResources(), resId, FRAMES_PER_SECOND, 0);
        this.height = fab.getHeight();
        this.x = x;
        this.y = -height;
        this.dx = 0;
        this.dy = speed;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        long nanos = gw.getTimeDiffNanos();
        y += dy * gw.getTimeDiffInSecond();;
        if (y > gw.getBottom() + height) {
            gw.removeObject(this);
        }
        fab.update();
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }
}
