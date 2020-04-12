package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.Ball;
import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.Plane;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Paint mainPaint;
    private Rect rect;
    private boolean movesBall;
    private ArrayList<GameObject> objects;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    private void initResources() {
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();

        objects = new ArrayList<>();
        objects.add(new Ball(this, 10, 10, 1, 1));
        objects.add(new Ball(this, 1000, 10, -2, 3));
        objects.add(new Plane(this, 500, 500, 0, 0));
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

        for (GameObject o : objects) {
            o.draw(canvas);
        }
    }

    public void update() {
        if (movesBall) {
            for (GameObject o : objects) {
                o.update();
            }
        }
    }

    public void doAction() {
        movesBall = !movesBall;
    }
}
