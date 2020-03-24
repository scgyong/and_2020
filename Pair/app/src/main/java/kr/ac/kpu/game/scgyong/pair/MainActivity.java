package kr.ac.kpu.game.scgyong.pair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int[] BUTTON_IDS = {
            R.id.b_00, R.id.b_01, R.id.b_02, R.id.b_03,
            R.id.b_10, R.id.b_11, R.id.b_12, R.id.b_13,
            R.id.b_20, R.id.b_21, R.id.b_22, R.id.b_23,
            R.id.b_30, R.id.b_31, R.id.b_32, R.id.b_33,
    };
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnCard(View view) {
        Log.d(TAG, "Button ID = " + view.getId());
    }
}
