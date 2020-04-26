package kr.ac.kpu.game.scgyong.smoothingpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

class PathView extends View {
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
