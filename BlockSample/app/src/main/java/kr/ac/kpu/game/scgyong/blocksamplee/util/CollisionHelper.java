package kr.ac.kpu.game.scgyong.blocksamplee.util;

import android.graphics.RectF;

import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.BoxCollidable;

public class CollisionHelper {
    private static RectF rect1 = new RectF();
    private static RectF rect2 = new RectF();
    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        o1.getBox(rect1);
        o2.getBox(rect2);
        if (rect1.left > rect2.right) {
            return false;
        }
        if (rect1.right < rect2.left) {
            return false;
        }
        if (rect1.top > rect2.bottom) {
            return false;
        }
        if (rect1.bottom < rect2.top) {
            return false;
        }
        return true;
    }
}
