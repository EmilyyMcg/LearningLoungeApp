package com.example.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class movement extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepDetector;
    private float distanceCovered = 0;  // Distance in meters
    private int stepCount = 0;
    private float stepLength = 0.762f; // Average step length in meters (can be adjusted)
    private boolean isRunning = false;

    private TextView distanceText;
    private Button startButton;

    private static final int ACTIVITY_RECOGNITION_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        ImageView backButton = findViewById(R.id.Back); // Use ImageView if it's an ImageView
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open = new Intent(movement.this, GameCentre.class);
                startActivity(open);
            }
        });

        distanceText = findViewById(R.id.distance_text);
        startButton = findViewById(R.id.start_button);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // === Runtime permission request for ACTIVITY_RECOGNITION ===
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, ACTIVITY_RECOGNITION_PERMISSION_CODE);
            }
        }

        // Check sensor availability
        if (stepDetector == null) {
            Toast.makeText(this, "Step detector sensor not available!", Toast.LENGTH_LONG).show();
            startButton.setEnabled(false);
            return;
        }

        startButton.setOnClickListener(view -> startRun());
    }

    private void startRun() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        stepCount = 0;
        distanceCovered = 0;
        distanceText.setText("Distance: 0 meters");
        sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_UI);

        // Change button text
        startButton.setText("Running...");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
            // Each step covers approximately 'stepLength' meters
            distanceCovered = stepCount * stepLength;

            distanceText.setText("Distance: " + (int) distanceCovered + " meters");

            // Check if the user has covered 1 kilometer (1000 meters)
            if (distanceCovered >= 1000) {
                finishRun();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    private void finishRun() {
        // Stop sensor tracking
        sensorManager.unregisterListener(this);

        // Alert user that they have completed the run
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500); // Vibrate for 500 milliseconds
        }

        Toast.makeText(this, "You have completed the run!", Toast.LENGTH_LONG).show();

        // Reset the button to allow for another run
        startButton.setText("Start New Run");
        isRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRunning) {
            // Unregister sensor listener if the activity goes into the background
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRunning) {
            // Register sensor listener when the activity is resumed
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTIVITY_RECOGNITION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Step detection may not work.", Toast.LENGTH_LONG).show();
                startButton.setEnabled(false);
            }
        }
    }
}

