package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;

public class Ball implements GameObject {
    private static Bitmap image;
    private static int radius;
    private int x, y, dx, dy;
    public Ball(View view, int x, int y, int dx, int dy) {
        if (image == null) {
            image = BitmapFactory.decodeResource(view.getResources(), R.mipmap.soccer_ball_240);
            radius = image.getWidth() / 2;
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        x += dx;
        if (dx > 0 && x > gw.getRight() - radius || dx < 0 && x < gw.getLeft() + radius) {
            dx *= -1;
        }
        y += dy;
        if (dy > 0 && y > gw.getBottom() - radius || dy < 0 && y < gw.getTop() + radius) {
            dy *= -1;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - radius, y - radius, null);
    }
}
