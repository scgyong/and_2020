package kr.ac.kpu.game.scgyong.pair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int[] BUTTON_IDS = {
            R.id.b_00, R.id.b_01, R.id.b_02, R.id.b_03,
            R.id.b_10, R.id.b_11, R.id.b_12, R.id.b_13,
            R.id.b_20, R.id.b_21, R.id.b_22, R.id.b_23,
            R.id.b_30, R.id.b_31, R.id.b_32, R.id.b_33,
    };
    private static final int[] imageResIds = {
            R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h, R.mipmap.card_5s,
            R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd, R.mipmap.card_as,
    };
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
    }

    private void startGame() {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            int resId = imageResIds[i / 2];
            btn.setImageResource(resId);
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    public void onBtnCard(View view) {
        Log.d(TAG, "Button ID = " + view.getId());
        ImageButton btn = (ImageButton) view;
        btn.setImageResource(R.mipmap.card_blue_back);
    }
}
