package kr.ac.kpu.game.scgyong.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeInterpolator;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;
    private TextView countTextView;
    private SeekBar seekBar;
    private Spinner spinner;

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


        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                // 안드로이드 values 폴더에 arrays에 셋팅된 List를 Adapter에 셋팅 해준다.
                // getResources() 메서드는 리소스 사용에 관한 메서드로 Activity 상속시
                // 사용할 수 있다.
                (String[]) getResources().getStringArray(R.array.interpolators));
        // Spinner 클릭시 DropDown 모양을 설정 할 수 있다.
        stringArrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어답터를 연결 시켜 준다.
        spinner.setAdapter(stringArrayAdapter);
    }

    private void showCount() {
        countTextView.setText("Count: " + pathView.getCount());
    }

    public void onBtnReset(View view) {
        pathView.clear();
    }

    public void onBtnStart(View view) {
        int pos = spinner.getSelectedItemPosition();
        TimeInterpolator interpolator;
        if (pos == 1) {
            interpolator = new AccelerateInterpolator();
        } else if (pos == 2) {
            interpolator = new DecelerateInterpolator();
        } else if (pos == 3) {
            interpolator = new AccelerateDecelerateInterpolator();
        } else if (pos == 4) {
            interpolator = new AnticipateInterpolator();
        } else if (pos == 5) {
            interpolator = new AnticipateOvershootInterpolator();
        } else {
            interpolator = new LinearInterpolator();
        }
        int value = seekBar.getProgress();
        pathView.start(1000 - value, interpolator);
    }
}
