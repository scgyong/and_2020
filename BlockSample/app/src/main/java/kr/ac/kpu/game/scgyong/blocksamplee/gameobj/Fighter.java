package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.FrameAnimationBitmap;

public class Fighter implements GameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 5;
    private final FrameAnimationBitmap fab;
    private final int halfImageWidth;
    private int x, y, dx, dy;
    public Fighter(View view, int x, int y, int dx, int dy) {
        fab = FrameAnimationBitmap.load(view.getResources(), R.mipmap.ryu, FRAMES_PER_SECOND);
        this.halfImageWidth = fab.getHeight() / 2;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        x += dx;
        if (dx > 0 && x > gw.getRight() - halfImageWidth || dx < 0 && x < gw.getLeft() + halfImageWidth) {
            dx *= -1;
        }
        y += dy;
        if (dy > 0 && y > gw.getBottom() - halfImageWidth || dy < 0 && y < gw.getTop() + halfImageWidth) {
            dy *= -1;
        }
        fab.update();
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }
}
