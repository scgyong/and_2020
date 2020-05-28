package kr.ac.kpu.game.scgyong.gameskeleton.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.view.GameView;
import kr.ac.kpu.game.scgyong.gameskeleton.game.scene.FirstScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        new FirstScene().run();
    }
}
