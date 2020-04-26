package kr.ac.kpu.game.scgyong.smoothingpath;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

class PathView extends View {
    public Bitmap bitmap;
    private int halfWidth, halfHeight;
    private float xFighter, yFighter;
    private float angle;

    public void start(int msecPerCount, TimeInterpolator interpolator) {
        int count = getCount();
        if (count < 2) return;
        final PathMeasure pm = new PathMeasure(path, false);
        final float length = pm.getLength();
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(count * msecPerCount);
        if (interpolator != null) {
            anim.setInterpolator(interpolator);
        }
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float[] pos = new float[2];
            float[] tan = new float[2];
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = animation.getAnimatedFraction();
                pm.getPosTan(length * progress, pos, tan);
                xFighter = pos[0];
                yFighter = pos[1];
                angle = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
                invalidate();
            }
        });
        anim.start();
    }

    interface Listener {
        public void onClick();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public int getCount() {
        return points.size();
    }

    Listener listener;
    private static final String TAG = PathView.class.getSimpleName();
    public static final int DIRECTION_FACTOR = 6;

    class Point {
        float x, y;
        float dx, dy;

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }

    ArrayList<Point> points = new ArrayList<>();
    Paint paint = new Paint();

    public PathView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLUE);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fight_plane);
        halfWidth = bitmap.getWidth() / 2;
        halfHeight = bitmap.getHeight() / 2;
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void clear() {
        points.clear();
        buildPath();
        invalidate();
    }

    Path path = new Path();

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        canvas.save();
        canvas.rotate(angle, xFighter, yFighter);
        canvas.drawBitmap(bitmap, xFighter - halfWidth, yFighter - halfHeight, null);
        canvas.restore();

//        Path path = new Path();
//
//        if (points.size() > 1){
//            for (int i = points.size() - 2; i < points.size(); i++) {
//                if (i >= 0) {
//                    Point point = points.get(i);
//
//                    if(i == 0){
//                        Point next = points.get(i + 1);
//                        point.dx = ((next.x - point.x) / DIRECTION_FACTOR);
//                        point.dy = ((next.y - point.y) / DIRECTION_FACTOR);
//                    } else if(i == points.size() - 1) {
//                        Point prev = points.get(i - 1);
//                        point.dx = ((point.x - prev.x) / DIRECTION_FACTOR);
//                        point.dy = ((point.y - prev.y) / DIRECTION_FACTOR);
//                    } else {
//                        Point next = points.get(i + 1);
//                        Point prev = points.get(i - 1);
//                        point.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
//                        point.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
//                    }
//                }
//            }
//        }
//
//        boolean first = true;
//        for (int i = 0; i < points.size(); i++) {
//            Point point = points.get(i);
//            if (first) {
//                first = false;
//                path.moveTo(point.x, point.y);
//            } else {
//                Point prev = points.get(i - 1);
//                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
//            }
//        }
//        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                Log.d(TAG, "Cleared");
                points.clear();
                return false;
            }
            Point point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            points.add(point);
            buildPath();
            invalidate();
            Log.d(TAG, "point: " + point + " count: " + points.size());
            if (listener != null) {
                listener.onClick();
            }
            return true;
        }

        return true;
    }

    private void buildPath() {
        path = new Path();
        if (points.size() > 1){
            for (int i = points.size() - 2; i < points.size(); i++) {
                if (i >= 0) {
                    Point point = points.get(i);

                    if(i == 0){
                        Point next = points.get(i + 1);
                        point.dx = ((next.x - point.x) / DIRECTION_FACTOR);
                        point.dy = ((next.y - point.y) / DIRECTION_FACTOR);
                    } else if(i == points.size() - 1) {
                        Point prev = points.get(i - 1);
                        point.dx = ((point.x - prev.x) / DIRECTION_FACTOR);
                        point.dy = ((point.y - prev.y) / DIRECTION_FACTOR);
                    } else {
                        Point next = points.get(i + 1);
                        Point prev = points.get(i - 1);
                        point.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
                        point.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
                    }
                }
            }
        }

        boolean first = true;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else {
                Point prev = points.get(i - 1);
                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
            }
        }
    }

    private void buildPath2() {
        path = new Path();
        int size = points.size();
        if (size > 0) {
            path.moveTo(points.get(0).x, points.get(0).y);
        }
        for (int i = 1; i + 2 < size; i += 2) {
            path.quadTo(
                    points.get(i).x, points.get(i).y,
                    points.get(i + 1).x, points.get(i + 1).y);
        }
        invalidate();
    }
}
