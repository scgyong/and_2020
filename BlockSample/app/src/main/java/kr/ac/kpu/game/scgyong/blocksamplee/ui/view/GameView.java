package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.Ball;
import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.Plane;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Paint mainPaint;
    private Rect rect;
    private GameWorld gameWorld;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    private void initResources() {
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();
        gameWorld = GameWorld.get();
        gameWorld.init(this);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int pl = getPaddingLeft();
        int pt = getPaddingTop();
        int pr = getPaddingRight();
        int pb = getPaddingBottom();
//        int w = getWidth();
//        int h = getHeight();
        rect.left = pl;
        rect.top = pt;
        rect.right = w - pr;
        rect.bottom = h - pb;
        Log.d(TAG, "Rect: " + rect);
        gameWorld.onSize(rect);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(rect, mainPaint);

        gameWorld.draw(canvas);
    }

    public void update() {
        gameWorld.update();
    }
}
