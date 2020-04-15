package kr.ac.kpu.game.scgyong.blocksamplee.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;

public class FrameAnimationBitmap {

    private static final String TAG = FrameAnimationBitmap.class.getSimpleName();
    private Bitmap bitmap;
    private int height;
    private int frames;
    private long time;
    private int fps;
    private int index;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();


    public static FrameAnimationBitmap load(Resources res, int resId, int framesPerSecond) {
        FrameAnimationBitmap fab;
        FrameAnimationBitmap stored = map.get(resId);
        if (stored == null) {
            Log.d(TAG, "Loading from Resource: " + resId);
            fab = new FrameAnimationBitmap(res, resId);
            map.put(resId, fab);
        } else {
            fab = new FrameAnimationBitmap(stored);
            Log.d(TAG, "Copy from Existing: " + resId + " - " + fab);
        }
        fab.fps = framesPerSecond;
        fab.time = System.currentTimeMillis();
        fab.index = 0;
        fab.srcRect.top = 0;
        fab.srcRect.bottom = fab.height;
        return fab;
    }

    public void update() {
        long elapsed = System.currentTimeMillis() - this.time;
        index = (int) (((elapsed * fps + 500) / 1000 % frames));
    }

    public void draw(Canvas canvas, int x, int y) {
        srcRect.left = height * index;
        srcRect.right = srcRect.left + height;

        int halfSize = height / 2;
        dstRect.left = x - halfSize;
        dstRect.top = y - halfSize;
        dstRect.right = x + halfSize;
        dstRect.bottom = y + halfSize;
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    private static HashMap<Integer, FrameAnimationBitmap> map = new HashMap<>();
    private FrameAnimationBitmap(Resources res, int resId) {
        bitmap = BitmapFactory.decodeResource(res, resId);
        height = bitmap.getHeight();
        int width = bitmap.getWidth();
        frames = width / height;
    }
    private FrameAnimationBitmap(FrameAnimationBitmap stored) {
        bitmap = stored.bitmap;
        height = stored.height;
        frames = stored.frames;
    }

    public int getHeight() {
        return height;
    }
}
