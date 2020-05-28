package kr.ac.kpu.game.scgyong.gameskeleton.framework.main;

import android.graphics.Canvas;

import kr.ac.kpu.game.scgyong.gameskeleton.framework.iface.Touchable;

public class GameObject {
    protected float x, y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() { return 0; }
    public void update() {}
    public void draw(Canvas canvas) {}

    public void captureTouch() {
        if (!(this instanceof Touchable)) {
            return;
        }
        GameScene.getTop().getGameWorld().captureTouch((Touchable) this);
    }
    public void releaseTouch() {
        GameScene.getTop().getGameWorld().releaseTouch();
    }
}
