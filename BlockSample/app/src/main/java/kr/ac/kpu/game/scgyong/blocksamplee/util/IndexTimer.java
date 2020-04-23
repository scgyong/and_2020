package kr.ac.kpu.game.scgyong.blocksamplee.util;

import kr.ac.kpu.game.scgyong.blocksamplee.game.framework.GameWorld;

public class IndexTimer {
    private final int count;
    private final int fps;
    private long time;

    public IndexTimer(int count, int framesPerSecond) {
        this.count = count;
        this.fps = framesPerSecond;
        this.time = GameWorld.get().getCurrentTimeNanos();
    }

    public int getIndex() {
        int index = getRawIndex();
        return index % count;
    }
    public boolean done() {
        int index = getRawIndex();
        return index >= count;
    }

    public int getRawIndex() {
        long elapsed = GameWorld.get().getCurrentTimeNanos() - this.time;
        return (int) (((elapsed * fps + 500) / 1000000000));
    }

    public void reset() {
        this.time = GameWorld.get().getCurrentTimeNanos();
    }
}
