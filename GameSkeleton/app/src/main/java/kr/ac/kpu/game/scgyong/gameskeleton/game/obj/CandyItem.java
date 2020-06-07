package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.iface.Recyclable;

public class CandyItem extends BitmapObject implements Recyclable {
    private static final int[] RES_IDS = {
            R.mipmap.cookie_item_001,
            R.mipmap.cookie_item_002,
            R.mipmap.cookie_item_003,
            R.mipmap.cookie_item_004,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    public CandyItem(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
    }

    @Override
    public void recycle() {
    }
}
