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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.World;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.scgyong.blocksamplee.res.sound.SoundEffects;
import kr.ac.kpu.game.scgyong.blocksamplee.util.IndexTimer;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private static final int FPS_SECONDS = 10;
    private IndexTimer timer;
    private Paint mainPaint;
    private Rect rect;
    private GameWorld gameWorld;
    private int count;
//    private SurfaceHolder surfaceHolder;
//    private Thread thread;

    public GameView(Context context) {
        super(context);

        initResources();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
//        surfaceHolder = getHolder();
//        surfaceHolder.addCallback(surfaceCallback);
//        thread = new Thread(threadRunnable);

        SharedBitmap.setResources(getResources());
        SoundEffects.get().loadAll(getContext());
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect = new Rect();
        gameWorld = GameWorld.get();
        gameWorld.init(this);

        timer = new IndexTimer(FPS_SECONDS, 1);

        postFrameCallback();
    }

//    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            Log.d(TAG, "Starting the thread");
//            thread.start();
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            Log.d(TAG, "surfaceChanged - fmt:" + format + " w:" + width + " h:" + height);
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.d(TAG, "surfaceDestroyed");
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };

//    private Runnable threadRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Canvas canvas = null;
//            while (true) {
//                try {
//                    Thread.sleep(15);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                canvas = surfaceHolder.lockCanvas();
//                try {
//                    synchronized (surfaceHolder) {
//                        long nanoTime = System.nanoTime();
//                        update(nanoTime);
//                        gameWorld.draw(canvas);
//                    }
//                } finally {
//                    if (canvas == null) {
//                        return;
//                    }
//                    surfaceHolder.unlockCanvasAndPost(canvas);
//                }
//            }
//        }
//    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return GameWorld.get().onTouchEvent(event);
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
            Log.d(TAG, "Frame Count /s = " + ((float)count / FPS_SECONDS) + " Total Objects = " + GameWorld.get().getAllObjectCount());
            count = 0;
            timer.reset();
        }
    }
}
