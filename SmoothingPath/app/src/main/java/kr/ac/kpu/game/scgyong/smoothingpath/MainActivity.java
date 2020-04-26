package kr.ac.kpu.game.scgyong.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;
    private TextView countTextView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        countTextView = findViewById(R.id.countTextView);
        pathView = findViewById(R.id.pathView);
        pathView.setListener(new PathView.Listener() {
            @Override
            public void onClick() {
                showCount();
            }
        });
        showCount();
    }

    private void showCount() {
        countTextView.setText("Count: " + pathView.getCount());
    }

    public void onBtnReset(View view) {
        pathView.clear();
    }

    public void onBtnStart(View view) {
        int value = seekBar.getProgress();
        pathView.start(1000 - value);
    }
}
