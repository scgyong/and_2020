package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.iface.BoxCollidable;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.iface.Touchable;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.util.CollisionHelper;
import kr.ac.kpu.game.scgyong.gameskeleton.game.scene.SecondScene;

public class Cookie extends AnimObject implements Touchable, BoxCollidable {

    private static final float JUMP_POWER = -1500;
    private static final float GRAVITY_SPEED = 4500;
    private static final String TAG = Cookie.class.getSimpleName();
    private static final float SLIDE_TIME = 1.0f;
    private final FrameAnimationBitmap fabNormal;
    private final FrameAnimationBitmap fabJump;
    private final FrameAnimationBitmap fabDJump;
    private final FrameAnimationBitmap fabSlide;
    private int jumpCount = 10; // 10 if falling
    private float speed;
    private float base;
    private float slideTime;

    public Cookie(float x, float y) {
        super(x, y, -50, -50, R.mipmap.cookie_run, 12, 0);
        base = y;

        fabNormal = fab;
        fabJump = new FrameAnimationBitmap(R.mipmap.cookie_jump, 12, 0);
        fabDJump = new FrameAnimationBitmap(R.mipmap.cookie_djump, 12, 0);
        fabSlide = new FrameAnimationBitmap(R.mipmap.cookie_slide, 12, 2);

        slideTime = -1;
    }

    public enum AnimState {
        normal, jump, djump, slide
    }
    public void setAnimState(AnimState state) {
        if (state == AnimState.normal) {
            fab = fabNormal;
        } else if (state == AnimState.jump){
            fab = fabJump;
        } else if (state == AnimState.djump){
            fab = fabDJump;
        } else {
            fab = fabSlide;
        }
    }

    @Override
    public void update() {
        if (jumpCount > 0) {
            float timeDiffSeconds = GameTimer.getTimeDiffSeconds();
            y += speed * timeDiffSeconds;
            speed += GRAVITY_SPEED * timeDiffSeconds;
//            if (y >= base) {
//                Log.d(TAG, "Jumping Done");
//                jumpCount = 0;
//                speed = 0;
//                setAnimState(AnimState.normal);
//                y = base;
//            }
        }
        SecondScene scene = SecondScene.get();
        float footY = y + height / 2;
        Platform platform = scene.getPlatformAt(x, footY);
        if (platform != null) {

            RectF rect = new RectF();
            platform.getBox(rect);
//            Log.d(TAG, "Platform box = " + rect);
            float ptop = platform.getTop();
            if (jumpCount > 0) {
//                Log.d(TAG, "Platform box = " + rect + " footY = " + footY + " ptop=" + ptop);
                if (speed > 0 && footY >= ptop) {
//                    Log.d(TAG, " Stopping at the platform");
                    y = ptop - height / 2;
                    jumpCount = 0;
                    speed = 0;
                    setAnimState(AnimState.normal);
                }
            } else {
                if (footY < ptop) {
//                    Log.d(TAG, " Start to fall down");
                    jumpCount = 10; // falling down
                    slideTime = -1;
                    setAnimState(AnimState.normal);
                }
            }
        } else {
//            Log.d(TAG, " No platform. Falling down");
            jumpCount = 10;
        }

        if (slideTime >= 0) {
            slideTime += GameTimer.getTimeDiffSeconds();
            if (slideTime > SLIDE_TIME) {
                slideTime = -1;
                setAnimState(AnimState.normal);
            }
        }

        checkItemCollision();
    }

    private void checkItemCollision() {

        ArrayList<GameObject> items = SecondScene.get().getGameWorld().objectsAtLayer(SecondScene.Layer.item.ordinal());
        for (GameObject obj : items) {
            if (!(obj instanceof CandyItem)) {
                continue;
            }
            CandyItem candy = (CandyItem) obj;
            if (CollisionHelper.collides(this, candy)) {
                candy.remove();
                SecondScene.get().addScore(candy.getScore());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        float tx = e.getX();
//        Log.d(TAG, "TouchEvent:" + e.getAction() + " - " + tx + "/" + UiBridge.metrics.center.x);
        if (tx < UiBridge.metrics.center.x) {
            // jump
            if (jumpCount < 2) {
//                Log.d(TAG, "Jumping");
                jumpCount++;
                speed += JUMP_POWER;
                if (speed > JUMP_POWER) {
                    speed = JUMP_POWER;
                }
                setAnimState(jumpCount == 1 ? AnimState.jump : AnimState.djump);
            }
        } else {
            if (jumpCount == 0) {
                slideTime = 0;
                setAnimState(AnimState.slide);
            }
        }
        return false;
    }
    @Override
    public void getBox(RectF rect) {
        int hw = width / 2;
        int hh = height / 2;
        rect.left = x - hw;
        rect.top = y - hh;
        rect.right = x + hw;
        rect.bottom = y + hh;
    }
}
