package kr.ac.kpu.game.scgyong.blocksamplee.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.BoxCollidable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.GameObject;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.Positionable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.iface.Recyclable;
import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;
import kr.ac.kpu.game.scgyong.blocksamplee.game.world.World;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class Enemy implements GameObject, BoxCollidable, Recyclable, Positionable {
    private static final String TAG = Enemy.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private final LifeGauge lifeGauge = new LifeGauge();
    private FrameAnimationBitmap fab;
    private float x, y, dx, dy;
    private int height;
    private int life;
    private int maxLife;

    private static int[] RES_IDS = {
            R.mipmap.enemy_01, R.mipmap.enemy_02, R.mipmap.enemy_03, R.mipmap.enemy_04,
            R.mipmap.enemy_05, R.mipmap.enemy_06, R.mipmap.enemy_07, R.mipmap.enemy_08,
            R.mipmap.enemy_09, R.mipmap.enemy_10, R.mipmap.enemy_11, R.mipmap.enemy_12,
            R.mipmap.enemy_13, R.mipmap.enemy_14, R.mipmap.enemy_15, R.mipmap.enemy_16,
            R.mipmap.enemy_17, R.mipmap.enemy_18, R.mipmap.enemy_19, R.mipmap.enemy_20,
    };

    class LifeGauge {
        private static final float HEIGHT = 10.0f;
        private static final float BORDER_WIDTH = 2.0f;
        private final Paint bgPaint = new Paint();
        private final Paint fgPaint = new Paint();
        float lifeWidth;
        LifeGauge() {
            bgPaint.setColor(Color.BLACK);
            fgPaint.setColor(Color.WHITE);
        }
        void reset(int width) {
            lifeWidth = width - 2 * BORDER_WIDTH;
        }
        void draw(Canvas canvas, float x, float y, int halfSize) {
            canvas.drawRect(x - halfSize, y + halfSize, x + halfSize, y + halfSize + HEIGHT, bgPaint);
            float gx = x - halfSize + BORDER_WIDTH;
            canvas.drawRect(gx, y + halfSize + BORDER_WIDTH, gx + lifeWidth, y + halfSize + HEIGHT - BORDER_WIDTH, fgPaint);
        }
    }

    private Enemy() {
        Log.v(TAG, "new Enemy() " + this);
    }
    public static Enemy get(int x, int level, int speed) {
        GameWorld gw = GameWorld.get();
        if (level >= RES_IDS.length) {
            level = RES_IDS.length - 1;
        }
        int resId = RES_IDS[level];
        Enemy enemy = (Enemy) gw.getRecyclePool().get(Enemy.class);
        if (enemy == null) {
            enemy = new Enemy();
            enemy.fab = new FrameAnimationBitmap(resId, FRAMES_PER_SECOND, 0);
        } else {
            enemy.fab.setBitmapResource(resId);
        }
        enemy.height = enemy.fab.getHeight();
        enemy.x = x;
        enemy.y = -enemy.height;
        enemy.dx = 0;
        enemy.dy = speed;
        enemy.maxLife = (level + 1) * 100;
        enemy.life = enemy.maxLife;
        enemy.lifeGauge.reset(enemy.height);

        return enemy;
    }

    @Override
    public void recycle() {

    }

    public void update() {
        GameWorld gw = GameWorld.get();
        y += dy * gw.getTimeDiffInSecond();;
        if (y > gw.getBottom() + height) {
            gw.removeObject(this);
        }
//        fab.update();
    }

    public void decreaseLife(int amount) {
        World gw = World.get();
        this.life -= amount;
        if (this.life <= 0) {
            gw.removeObject(this);
            gw.addScore(this.maxLife);
            return;
        }
        lifeGauge.lifeWidth = (height - 2 * LifeGauge.BORDER_WIDTH) * life / maxLife;;
//        int alpha = 255 - (maxLife - life) * 200 / maxLife;
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
        int halfSize = height / 2;
        lifeGauge.draw(canvas, x, y, halfSize);
    }

    @Override
    public void getBox(RectF rect) {
        float halfWidth = fab.getWidth() / 2;
        float halfHeight = fab.getHeight() / 2;
        rect.left = x - halfWidth;
        rect.right = x + halfWidth;
        rect.top = y - halfHeight;
        rect.bottom = y + halfHeight;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
