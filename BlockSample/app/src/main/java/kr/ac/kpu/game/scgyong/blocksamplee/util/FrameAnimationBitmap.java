package kr.ac.kpu.game.scgyong.blocksamplee.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.HashMap;

import kr.ac.kpu.game.scgyong.blocksamplee.gameobj.GameWorld;

public class FrameAnimationBitmap {

    private static final String TAG = FrameAnimationBitmap.class.getSimpleName();
    private Bitmap bitmap;
    private int height;
    private int frameWidth;
    private int frames;
    private long time;
    private int fps;
    private int index;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();


    public static FrameAnimationBitmap load(Resources res, int resId, int framesPerSecond, int frameCount) {
        FrameAnimationBitmap fab;
        FrameAnimationBitmap stored = map.get(resId);
        if (stored == null) {
            Log.d(TAG, "Loading from Resource: " + resId);
            fab = new FrameAnimationBitmap(res, resId, frameCount);
            map.put(resId, fab);
        } else {
            fab = new FrameAnimationBitmap(stored);
            Log.d(TAG, "Copy from Existing: " + resId + " - " + fab);
        }
        fab.fps = framesPerSecond;
        fab.time = GameWorld.get().getCurrentTimeNanos();
        fab.index = 0;
        fab.srcRect.top = 0;
        fab.srcRect.bottom = fab.height;
        return fab;
    }
    public void setFrameCount(int count) {
        frames = count;
    }

    public boolean update() {
        long elapsed = GameWorld.get().getCurrentTimeNanos() - this.time;
        int total = (int) ((elapsed * fps + 500000000) / 1000000000);
        index = total % frames;
        return total >= frames;
    }

    public void draw(Canvas canvas, float x, float y) {
        draw(canvas, x, y, null);
    }
    public void draw(Canvas canvas, float x, float y, Paint paint) {
        srcRect.left = frameWidth * index;
        srcRect.right = srcRect.left + frameWidth;

        int halfWidth = frameWidth / 2;
        int halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
    }

    private static HashMap<Integer, FrameAnimationBitmap> map = new HashMap<>();
    private FrameAnimationBitmap(Resources res, int resId, int frameCount) {
        bitmap = BitmapFactory.decodeResource(res, resId);
        height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (frameCount == 0) {
            frames = width / height;
            frameWidth = height;
        } else {
            frames = frameCount;
            frameWidth = width / frameCount;
        }
    }
    private FrameAnimationBitmap(FrameAnimationBitmap stored) {
        bitmap = stored.bitmap;
        height = stored.height;
        frames = stored.frames;
        frameWidth = stored.frameWidth;
    }

    public int getHeight() {
        return height;
    }

    public void reset() {
        time = GameWorld.get().getCurrentTimeNanos();
    }

    public void draw(Canvas canvas, Matrix matrix) {
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
