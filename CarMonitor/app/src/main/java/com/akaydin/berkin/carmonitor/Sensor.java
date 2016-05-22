package com.akaydin.berkin.carmonitor;

/**
 * Created by berkin on 28.04.2016.
 */
public abstract class Sensor {

	private String sensorName;
	private int sensorID;

	public String getSensorName() {
		return sensorName;
	}

	public int getSensorID() {
		return sensorID;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}

}
