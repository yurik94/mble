package it.tests.ikevin.mble;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SensorActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    private HM10Activity mainActivity;
    private ProgressBar progressPitch, progressRoll;
    private EditText pitchText, rollText;

    public int pitch, roll;

    public SensorActivity(HM10Activity main)
    {
        mainActivity = main;

        mSensorManager = (SensorManager)mainActivity.getSystemService(mainActivity.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        progressPitch = (ProgressBar)mainActivity.findViewById(R.id.progressPitch);
        progressRoll = (ProgressBar)mainActivity.findViewById(R.id.progressBarRoll);

        pitchText = (EditText)mainActivity.findViewById(R.id.pitch);
        pitchText.setInputType(InputType.TYPE_NULL);
        rollText = (EditText)mainActivity.findViewById(R.id.roll);
        rollText.setInputType(InputType.TYPE_NULL);

    }

    public void onResume() {
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
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

                pitch += (int)Math.toDegrees(orientation[1]);
                roll += (int)Math.toDegrees(orientation[2]);

                pitch/=2; roll/=2;

                progressPitch.setProgress(pitch + 90);
                progressRoll.setProgress(roll + 90);

                pitchText.setText(String.valueOf(pitch));
                rollText.setText(String.valueOf(roll));
            }
        }
    }
}