package kr.ac.kpu.game.scgyong.blocksamplee.gameobj;

import android.util.Log;

import java.util.Random;

public class EnemyGenerator {
    public static final long INITIAL_GENERATE_INTERVAL = 5000000000l;
    public static final long MINIMUM_GENERATE_INTERVAL = 1000000000;
    private static final int MAX_LEVEL = 20;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private static final int MAX_SPEED = 2000;
    private final Random rand;
    private long time;
    private long genarationInterval;
    private int wave;

    public EnemyGenerator() {
        this.time = GameWorld.get().getCurrentTimeNanos();
        genarationInterval = INITIAL_GENERATE_INTERVAL;
        rand = new Random();
        wave = 0;
    }

    public void update() {
        GameWorld gw = GameWorld.get();
        long now = gw.getCurrentTimeNanos();
        long elapsed = now - time;
        if (elapsed > genarationInterval) {
            genarateWave();
            this.time = now;
        }
    }

    private void genarateWave() {
        String lev = "/";
        for (int i = 0; i < 5; i++) {
            lev += generateEnemy(120 + i * 200) + "/";
        }
        wave++;
        genarationInterval *= 0.995;
        if (genarationInterval < MINIMUM_GENERATE_INTERVAL) {
            genarationInterval = MINIMUM_GENERATE_INTERVAL;
        }
        if (wave % 20 == 0) {
            Log.d(TAG, "Wave " + wave + " Generated: " + lev + " Interval=" + (genarationInterval / 1000000000.));
        }
    }

    private int generateEnemy(int x) {
        int speed = 500 + 10 * wave;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        int level = (wave - 10) / 10;
        int r = rand.nextInt(20);
        if (r >= 19) {
            level += 2;
        } else if (r >= 16) {
            level += 1;
        } else if (r <= 1) {
            level -= 3;
        } else if (r <= 4) {
            level -= 2;
        } else if (r <= 7) {
            level -= 1;
        }

        if (level < 0) {
            level = 0;
        }
        if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }

        Enemy e = Enemy.get(x, level, speed);
        GameWorld gw = GameWorld.get();
        gw.add(GameWorld.Layer.enemy, e);
        return level;
    }
}
