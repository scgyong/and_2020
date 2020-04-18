package kr.ac.kpu.game.scgyong.blocksamplee.util;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class MatrixHelper {
    private static final String TAG = MatrixHelper.class.getSimpleName();

    public static float getAngle(Matrix matrix) {
        float[] v = new float[9];
        matrix.getValues(v);
// translation is simple
        float tx = v[Matrix.MTRANS_X];
        float ty = v[Matrix.MTRANS_Y];

// calculate real scale
        float scalex = v[Matrix.MSCALE_X];
        float skewy = v[Matrix.MSKEW_Y];
        float rScale = (float) Math.sqrt(scalex * scalex + skewy * skewy);

// calculate the degree of rotation
        float angle = (float) Math.atan2(v[Matrix.MSKEW_X], v[Matrix.MSCALE_X]);

        Log.d(TAG, "Angle = " + (angle * 180 / Math.PI));

        return angle;
    }

    public static void getTranslation(Matrix matrix, PointF translatoin) {
        float[] v = new float[9];
        matrix.getValues(v);
// translation is simple
        translatoin.x = v[Matrix.MTRANS_X];
        translatoin.y = v[Matrix.MTRANS_Y];
    }
}
