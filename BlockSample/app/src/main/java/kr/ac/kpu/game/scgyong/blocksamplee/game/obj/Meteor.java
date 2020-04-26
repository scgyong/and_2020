package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Canvas;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class Meteor implements GameObject {
    private static final String TAG = Meteor.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private final int imageRadius;
    private FrameAnimationBitmap fab;
    private int radius;
    private float x, y, dx, dy;
    public static Meteor get(float x, int speed) {
        GameWorld gw = GameWorld.get();
        Meteor meteor = (Meteor) gw.getRecyclePool().get(Meteor.class);
        if (meteor == null) {
            meteor = new Meteor();
        }
        meteor.x = x;
        meteor.dy = speed;

        return meteor;
    }

    private Meteor() {
        this.x = x;
        this.fab = new FrameAnimationBitmap(R.mipmap.fireball_128_24f, FRAMES_PER_SECOND, 0);
        GameWorld gw = GameWorld.get();
        this.imageRadius = gw.getRight() / 6;
        this.radius = imageRadius / 4;
        this.y = -imageRadius;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        y += dy * gw.getTimeDiffInSecond();
        if (y + radius > gw.getBottom()) {
            gw.removeObject(this);
        }
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y, imageRadius);
    }
}
