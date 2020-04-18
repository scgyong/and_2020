package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.View;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.util.MatrixHelper;

public class Plane implements GameObject {
    private static final float ANGLE_PER_SECOND = 90;
    private static final String TAG = Plane.class.getSimpleName();
    private static Bitmap image;
    private static int radius;
    private int size;
    private int x, y;
    private Matrix matrix;

    public Plane(View view, int x, int y) {
        if (image == null) {
            image = BitmapFactory.decodeResource(view.getResources(), R.mipmap.plane_240);
            radius = image.getWidth() / 2;
        }
        this.x = x;
        this.y = y;
        this.matrix = new Matrix();
        matrix.preTranslate(x - radius, y - radius * 6 / 5);

//        paint.setTextSize(30);
    }
    public void fire() {
        float angle = MatrixHelper.getAngle(matrix);
        Bullet bullet = new Bullet(GameWorld.get().getView(), x, y, angle, radius);
        GameWorld.get().add(GameWorld.Layer.missile, bullet);
    }
    @Override
    public void update() {
        float angle = ANGLE_PER_SECOND * GameWorld.get().getTimeDiffInSecond();
        matrix.postRotate(angle, x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, matrix, null);
//        canvas.drawText(debug, 20, 20, paint);
    }
//    String debug = "";
//    Paint paint = new Paint();

    public void head(PointF pt) {

//        double angle = Math.atan2(y - pt.y, x - pt.x) - Math.PI / 2;
//        이거를
//        double angle = Math.atan2(y - pt.y, pt.x - x) + Math.PI / 2;
        double angle = Math.atan2(pt.y - y, pt.x - x) + Math.PI / 2;
//        double angle = Math.atan2(y - pt.y, x - pt.x) - Math.PI / 2;
//        debug = "dx=" + (pt.x ) + " dy=" + (pt.y ) + " angle=" + angle;
//        Log.d(TAG, debug);
        Matrix matrix = new Matrix();
        matrix.preTranslate(x - radius, y - radius * 6 / 5);
        matrix.postRotate((float) (angle * 180 / Math.PI), x, y);

        this.matrix = matrix;
    }
}
