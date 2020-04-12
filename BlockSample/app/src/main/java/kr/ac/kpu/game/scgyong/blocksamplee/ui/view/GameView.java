package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private Paint mainPaint;
    private Rect rect;

    public GameView(Context context) {
        super(context);

        initPaint();
    }

    private void initPaint() {
        mainPaint = new Paint();
        mainPaint.setColor(Color.RED);

        rect = new Rect();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
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
    }
}
