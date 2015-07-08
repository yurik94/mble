package it.tests.ikevin.mble;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SensorActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    private MainActivity mainActivity;
    private ProgressBar progressAzimut, progressPitch, progressRoll;
    private EditText azimutText, pitchText, rollText;

    public SensorActivity(MainActivity main)
    {
        mainActivity = main;

        mSensorManager = (SensorManager)mainActivity.getSystemService(mainActivity.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        progressAzimut = (ProgressBar)mainActivity.findViewById(R.id.progressAzimut);
        progressPitch = (ProgressBar)mainActivity.findViewById(R.id.progressPitch);
        progressRoll = (ProgressBar)mainActivity.findViewById(R.id.progressBarRoll);

        azimutText = (EditText)mainActivity.findViewById(R.id.azimut);
        azimutText.setInputType(InputType.TYPE_NULL);
        pitchText = (EditText)mainActivity.findViewById(R.id.pitch);
        pitchText.setInputType(InputType.TYPE_NULL);
        rollText = (EditText)mainActivity.findViewById(R.id.roll);
        rollText.setInputType(InputType.TYPE_NULL);

    }

    public void onResume() {
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    float[] mGravity;
    float[] mGeomagnetic;

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                int azimut = (int)Math.toDegrees(orientation[0]);
                int pitch = (int)Math.toDegrees(orientation[1]);
                int roll = (int)Math.toDegrees(orientation[2]);
                if (azimut < 0) {
                    azimut += 360;
                }
                roll+=180;
                azimutText.setText(String.valueOf(azimut));
                pitchText.setText(String.valueOf(pitch));
                rollText.setText(String.valueOf(roll));

                progressAzimut.setProgress(azimut);
                progressPitch.setProgress(pitch);
                progressRoll.setProgress(roll);
            }
        }
    }
}