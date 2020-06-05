package kr.ac.kpu.game.scgyong.gyrosample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final double NS2S = 1.0 / 1_000_000_000.0;
    private static final double RAD_TO_DEGREE = 180.0 / Math.PI;
    private TextView xAxisTextView, yAxisTextView, zAxisTextView;
    private double roll, pitch, yaw;
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private double timestamp;

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
            accumulateSensorValues(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void accumulateSensorValues(SensorEvent event) {
        if (timestamp == 0) {
            timestamp = event.timestamp;
            return;
        }
        double dt = (event.timestamp - timestamp) * NS2S;
        roll += event.values[0] * dt;
        pitch += event.values[1] * dt;
        yaw += event.values[2] * dt;


        showRotationValues();

        timestamp = event.timestamp;
    }

    private void showRotationValues() {
        xAxisTextView.setText("X: " + fmt(roll));
        yAxisTextView.setText("Y: " + fmt(pitch));
        zAxisTextView.setText("Z: " + fmt(yaw));
    }

    private String fmt(double value) {
        return String.format("%.3f", value * RAD_TO_DEGREE);
    }

    private void connectUiParts() {
        xAxisTextView = findViewById(R.id.xAxisTextView);
        yAxisTextView = findViewById(R.id.yAxisTextView);
        zAxisTextView = findViewById(R.id.zAxisTextView);
    }

    public void onBtnReset(View view) {
        roll = 0;
        pitch = 0;
        yaw = 0;
        showRotationValues();
    }
}
