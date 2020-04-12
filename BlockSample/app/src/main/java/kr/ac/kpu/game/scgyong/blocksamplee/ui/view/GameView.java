package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.Ball;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Paint mainPaint;
    private Rect rect;
    private boolean movesBall;
    private Ball ball;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    private void initResources() {
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();
        ball = new Ball(this, 10, 10);
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

        ball.draw(canvas);
    }

    public void update() {
        if (movesBall) {
            ball.update();
        }
    }

    public void doAction() {
        movesBall = !movesBall;
    }
}
