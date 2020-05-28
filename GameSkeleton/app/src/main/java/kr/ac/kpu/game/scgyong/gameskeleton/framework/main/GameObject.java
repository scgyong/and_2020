package kr.ac.kpu.game.scgyong.gameskeleton.framework.main;

import android.graphics.Canvas;

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
}
