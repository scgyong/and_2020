package kr.ac.kpu.game.scgyong.gameskeleton.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.view.GameView;
import kr.ac.kpu.game.scgyong.gameskeleton.game.scene.FirstScene;

public class GameActivity extends AppCompatActivity {

    private static final long BACKKEY_INTERVAL_MSEC = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        new FirstScene().run();

    }

    private long lastBackPressedOn;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastBackPressedOn;
        if (elapsed <= BACKKEY_INTERVAL_MSEC) {
            finish();
            return;
        }
        Log.d("BackKey", "elapsed="+elapsed);
        Toast.makeText(this,
                "Press Back key twice quickly to exit",
                Toast.LENGTH_SHORT)
                .show();
        lastBackPressedOn = now;
    }
}
