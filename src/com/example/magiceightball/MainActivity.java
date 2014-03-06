package com.example.magiceightball;

/**
 * onSensorChanged class
 * getAccelerometer class
 * onAccuracyChanged class
 * @author //http://shaikhhamadali.blogspot.com/2013/10/android-sensors-accelerometer-android.html 
*/

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	//SensorManager lets you access the device's sensors
	//declare Variables
	private SensorManager sensorManager;
	private boolean shake = false;
	private long lastUpdate;
	RelativeLayout eb_Accelerometer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Hide Navigation and make this full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Create instance of Layout
		eb_Accelerometer= (RelativeLayout)findViewById(R.id.eb_Accelerometer);
		eb_Accelerometer.setBackgroundColor(Color.WHITE);
		//create instance of sensor manager and get system service to interact with Sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		lastUpdate = System.currentTimeMillis();
	}
	// called when sensor value have changed
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			getAccelerometer(event);
		}

	}

	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;
		// Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];
		//get acceleration 
		float accelationSquareRoot = (x * x + y * y + z * z)
				/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		//get current time
		long actualTime = System.currentTimeMillis();
		if (accelationSquareRoot >= 2)
		{
			if (actualTime - lastUpdate < 70) {
				return;
			}
			lastUpdate = actualTime;
			
			if (shake) {
				Toast.makeText(this, "I don't think so.", Toast.LENGTH_SHORT)
				.show();
				
			} else {
				Toast.makeText(this, "Definitely!!!", Toast.LENGTH_SHORT)
				.show();
				
			}
			
			shake = !shake;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		// register this class as a listener for the orientation and
		// accelerometer sensors
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}
} 

