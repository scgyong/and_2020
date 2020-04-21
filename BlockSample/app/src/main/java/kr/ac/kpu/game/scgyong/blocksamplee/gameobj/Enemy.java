package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.scgyong.blocksamplee.R;
import kr.ac.kpu.game.scgyong.blocksamplee.res.bitmap.FrameAnimationBitmap;

public class Enemy implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Enemy.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 12;
    private FrameAnimationBitmap fab;
    private int height;
    private Paint guageBgPaint;
    private Paint guageFgPaint;
    private int life;
    private float x, y, dx, dy;
    private int maxLife;

    private static int[] RES_IDS = {
            R.mipmap.enemy_01,
            R.mipmap.enemy_02,
            R.mipmap.enemy_03,
            R.mipmap.enemy_04,
            R.mipmap.enemy_05,
            R.mipmap.enemy_06,
            R.mipmap.enemy_07,
            R.mipmap.enemy_08,
            R.mipmap.enemy_09,
            R.mipmap.enemy_10,
            R.mipmap.enemy_11,
            R.mipmap.enemy_12,
            R.mipmap.enemy_13,
            R.mipmap.enemy_14,
            R.mipmap.enemy_15,
            R.mipmap.enemy_16,
            R.mipmap.enemy_17,
            R.mipmap.enemy_18,
            R.mipmap.enemy_19,
    };

    private Enemy() {
        Log.v(TAG, "new Enemy()");
    }
    public static Enemy get(int x, int level, int speed) {
        GameWorld gw = GameWorld.get();
        if (level >= RES_IDS.length) {
            level = RES_IDS.length - 1;
        }
        Enemy enemy = (Enemy) gw.getRecyclePool().get(Enemy.class);
        if (enemy == null) {
            enemy = new Enemy();
        }
        int resId = RES_IDS[level];
        enemy.fab.setBitmapResource(resId);
        enemy.height = enemy.fab.getHeight();
        enemy.x = x;
        enemy.y = -enemy.height;
        enemy.dx = 0;
        enemy.dy = speed;
        enemy.maxLife = (level + 1) * 100;
        enemy.life = enemy.maxLife;
        enemy.guageBgPaint = new Paint();
        enemy.guageBgPaint.setColor(Color.BLACK);
        enemy.guageFgPaint = new Paint();
        enemy.guageFgPaint.setColor(Color.WHITE);

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
        GameWorld gw = GameWorld.get();
        this.life -= amount;
        int alpha = 255 - (maxLife - life) * 200 / maxLife;
        if (this.life <= 0) {
            gw.removeObject(this);
        }
    }

    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
        int halfSize = height / 2;
        canvas.drawRect(x - halfSize, y + halfSize, x + halfSize, y + halfSize + 10, guageBgPaint);
        int width = (height - 4) * life / maxLife;
        float gx = x - halfSize + 2;
        canvas.drawRect(gx, y + halfSize + 2, gx + width, y + halfSize + 8, guageFgPaint);
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
}
