package kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.HashMap;

public class SharedBitmap {
    private static final String TAG = SharedBitmap.class.getSimpleName();
    private static Resources resources;
    private static HashMap<Integer, SharedBitmap> map = new HashMap<>();
    Bitmap bitmap;
    int width;
    int height;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void draw(Canvas canvas, float x, float y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
    public void draw(Canvas canvas, float x, float y, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public void draw(Canvas canvas, Matrix matrix, Paint paint) {
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public static void setResources(Resources res) {
        resources = res;
    }
    public static SharedBitmap load(int resId) {
        Log.d(TAG, "Loading " + resId);
        SharedBitmap sb = map.get(resId);
        if (sb != null) {
            return sb;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);
        if (bitmap == null) {
            return null;
        }
        sb = new SharedBitmap();
        sb.bitmap = bitmap;
        sb.width = bitmap.getWidth();
        sb.height = bitmap.getHeight();
        map.put(resId, sb);

        return sb;
    }
    public static void unload(int resId) {
        map.remove(resId);
    }
    public static void unloadAll(int resId) {
        map.clear();
    }

    private SharedBitmap() {}
}
