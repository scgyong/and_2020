package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.IndexTimer;

public class Ball implements GameObject {
    private static final String TAG = Ball.class.getSimpleName();
    private static Bitmap image;
    private static int halfImageWidth;
    private final IndexTimer indexTimer;
    private int x, y, dx, dy;
    public Ball(View view, int x, int y, int dx, int dy) {
        if (image == null) {
            image = BitmapFactory.decodeResource(view.getResources(), R.mipmap.soccer_ball_240);
            halfImageWidth = image.getWidth() / 2;
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.indexTimer = new IndexTimer(8, 6);
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
        int index = indexTimer.getIndex();
//        Log.d(TAG, "Index = " + index);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - halfImageWidth, y - halfImageWidth, null);
    }
}
