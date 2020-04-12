package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.game.scgyong.blocksamplee.R;

public class GameView extends View {
    private Paint mainPaint;
    private Rect rect;
    private Bitmap ballImage;
    private int xBall, yBall;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    private void initResources() {
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();
        Resources res = getResources();
        ballImage = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);

        xBall = 500;
        yBall = 600;
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int pl = getPaddingLeft();
        int pt = getPaddingTop();
        int pr = getPaddingRight();
        int pb = getPaddingBottom();
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        rect.left = pl;
        rect.top = pt;
        rect.right = w - pr;
        rect.bottom = h - pb;

        canvas.drawRect(rect, mainPaint);

        canvas.drawBitmap(ballImage, xBall, yBall, null);
    }
}
