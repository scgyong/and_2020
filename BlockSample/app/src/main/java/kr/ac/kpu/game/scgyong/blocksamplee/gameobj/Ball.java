package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;

public class Ball {
    private static Bitmap image;
    private final int dx;
    private final int dy;
    private int x, y;
    public Ball(View view, int x, int y, int dx, int dy) {
        if (image == null) {
            image = BitmapFactory.decodeResource(view.getResources(), R.mipmap.soccer_ball_240);
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
