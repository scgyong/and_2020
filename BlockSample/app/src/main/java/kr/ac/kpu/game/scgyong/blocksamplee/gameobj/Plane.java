package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;

public class Plane implements GameObject {
    private static Bitmap image;
    private int x, y, dx, dy;

    public Plane(View view, int x, int y, int dx, int dy) {
        if (image == null) {
            image = BitmapFactory.decodeResource(view.getResources(), R.mipmap.plane_240);
        }
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
    @Override
    public void update() {
        x += dx;
        y += dy;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
