package com.akaydin.berkin.carmonitor;

/**
 * Created by berkin on 28.04.2016.
 */
public class CoolantTemperature extends Sensor {

	private int coolantTemperature;

	public int maxTemperature() {
		return 0;
	}

	public int minTemperature() {
		return 0;
	}

	public int getCoolantTemperature() {
		return coolantTemperature;
	}

	public void setCoolantTemperature(int coolantTemperature) {
		this.coolantTemperature = coolantTemperature;
	}
}
