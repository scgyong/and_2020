package kr.ac.kpu.game.scgyong.blocksamplee.util;

import android.util.Log;

public class IndexTimer {
    private final int count;
    private final int fps;
    private final long time;

    public IndexTimer(int count, int framesPerSecond) {
        this.count = count;
        this.fps = framesPerSecond;
        this.time = System.currentTimeMillis();
    }

    public int getIndex() {
        long elapsed = System.currentTimeMillis() - this.time;
        int index = (int) (((elapsed * fps) / 1000 % count));
        Log.d("IndexTimer", "e*f=" + (elapsed * fps) + " /1000=" + ((elapsed * fps) / 1000));
        return index;
    }
}
