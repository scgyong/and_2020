package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.bg.ImageScrollBackground;

public class HorzScrollBackground extends ImageScrollBackground {
    public HorzScrollBackground(int resId) {
        super(resId, Orientation.horizontal, -300);
    }
}
