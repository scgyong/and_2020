package kr.ac.kpu.game.scgyong.pair;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

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
    private ImageButton lastButton;
    private int flips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
    }

    private void startGame() {
        flips = 0;
        showScore();

        int[] buttonIds = shuffleButtonIds();
        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton btn = findViewById(buttonIds[i]);
            int resId = imageResIds[i / 2];
            btn.setTag(resId);
            btn.setImageResource(R.mipmap.card_blue_back);
            //btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            btn.setEnabled(true);
        }

        lastButton = null;
    }

    private void showScore() {
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.flipsScoreFormat), flips);
        scoreTextView.setText(text);
    }

    private int[] shuffleButtonIds() {
        int[] buttonIds = Arrays.copyOf(BUTTON_IDS, BUTTON_IDS.length);
        Random rand = new Random();
        for (int i = 0; i < buttonIds.length; i++) {
            int r = rand.nextInt(buttonIds.length);
            int temp = buttonIds[i];
            buttonIds[i] = buttonIds[r];
            buttonIds[r] = temp;
        }
        return buttonIds;
    }

    public void onBtnCard(View view) {
        Log.d(TAG, "Button ID = " + view.getId());
        ImageButton btn = (ImageButton) view;
        int resId = (int)btn.getTag();
        btn.setImageResource(resId);
        btn.setEnabled(false);

        if (lastButton == null) {
            lastButton = btn;
            return;
        }

        if ((int)lastButton.getTag() == (int)btn.getTag()) {
            lastButton = null;
            return;
        }

        lastButton.setImageResource(R.mipmap.card_blue_back);
        lastButton.setEnabled(true);

        lastButton = btn;

        flips++;
        showScore();
    }

    public void onBtnRestart(View view) {
        Log.v(TAG, "onBtnRestart");
        new AlertDialog.Builder(this)
                .setTitle(R.string.restartTitle)
                .setMessage(R.string.restartMessage)
                .setNegativeButton(R.string.restartNo, null)
                .setPositiveButton(R.string.restartYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                })
                .create()
                .show();
    }
}
