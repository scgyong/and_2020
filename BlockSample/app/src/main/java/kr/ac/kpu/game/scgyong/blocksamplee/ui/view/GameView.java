package kr.ac.kpu.game.scgyong.blocksamplee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.game.scgyong.blocksamplee.game.world.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.scgyong.blocksamplee.res.sound.SoundEffects;
import kr.ac.kpu.game.scgyong.blocksamplee.util.IndexTimer;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private IndexTimer timer;
    private Paint mainPaint;
    private Rect rect;
    private GameWorld gameWorld;
    private int count;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    private void postFrameCallback() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                update(frameTimeNanos);
                invalidate();
                postFrameCallback();
            }
        });
    }

    private void initResources() {
        SharedBitmap.setResources(getResources());
        SoundEffects.get().loadAll(getContext());
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();
        gameWorld = GameWorld.get();
        gameWorld.init(this);

        timer = new IndexTimer(30, 1);

        postFrameCallback();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    private PointF pt = new PointF();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                GameWorld.get().doAction(GameWorld.Action.fireHadoken, null);
                break;
            case MotionEvent.ACTION_MOVE:
                pt.x = event.getX();
                pt.y = event.getY();
                GameWorld.get().doAction(GameWorld.Action.fireBullet, pt);
        }
        return true;
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

    public void update(long frameTimeNanos) {
        gameWorld.update(frameTimeNanos);
        count++;
        if (timer.done()) {
            Log.d(TAG, "Frame Count = " + count + " Total Objects = " + GameWorld.get().getAllObjectCount());
            count = 0;
            timer.reset();
        }
    }
}
