package kr.ac.kpu.game.scgyong.gyrosample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView xAxisTextView, yAxisTextView, zAxisTextView;
    private SensorManager sensorManager;
    private Sensor gyroSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectUiParts();
        connectSensor();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(gyroListener);
        super.onPause();
    }

    private void connectSensor() {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            xAxisTextView.setText("X: " + fmt(event.values[0]));
            yAxisTextView.setText("Y: " + fmt(event.values[1]));
            zAxisTextView.setText("Z: " + fmt(event.values[2]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private String fmt(float value) {
        return String.format("%.5f", value);
    }

    private void connectUiParts() {
        xAxisTextView = findViewById(R.id.xAxisTextView);
        yAxisTextView = findViewById(R.id.yAxisTextView);
        zAxisTextView = findViewById(R.id.zAxisTextView);
    }
}
