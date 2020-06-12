package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Ball;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.CityBackground;

public class DialogScene extends GameScene {
    private static final String TAG = DialogScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void enter() {
        super.enter();
        setTransparent(true);
        initObjects();

        int mdpi_100 = UiBridge.y(100);
        //ValueAnimator animator =
        ValueAnimator anim = ValueAnimator.ofFloat(UiBridge.metrics.size.y, mdpi_100);
        anim.setDuration(500);
        anim.setInterpolator(new OvershootInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                scrollTo(value);
            }
        });
        anim.start();
    }

    private void scrollTo(float y) {
        int mdpi_100 = UiBridge.y(100);
        ArrayList<GameObject> objs = gameWorld.objectsAtLayer(Layer.ui.ordinal());
        int count = objs.size();
        for (int i = 0; i < count; i++) {
            Button btn = (Button)objs.get(i);
            float diff = y - btn.getY();
            btn.move(0, diff);

            y += mdpi_100;
        }
    }

    private void initObjects() {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;

        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(cx, y, screenWidth, screenHeight, R.mipmap.black_transparent));
//        y += UiBridge.y(100);
        gameWorld.add(Layer.ui.ordinal(), new Button(cx, y, R.mipmap.btn_tutorial, R.mipmap.blue_round_btn, R.mipmap.red_round_btn));
        y += UiBridge.y(100);
        Button button = new Button(cx, y, R.mipmap.btn_start_game, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        button.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                pop();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button);
        y += UiBridge.y(100);
        gameWorld.add(Layer.ui.ordinal(), new Button(cx, y, R.mipmap.btn_highscore, R.mipmap.blue_round_btn, R.mipmap.red_round_btn));
    }
}
