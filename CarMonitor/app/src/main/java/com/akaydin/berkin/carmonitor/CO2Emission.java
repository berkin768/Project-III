package com.akaydin.berkin.carmonitor;

/**
 * Created by berkin on 28.04.2016.
 */
public class CO2Emission extends Sensor {
	private int co2Value;

	public boolean isNormal(int value) {
		return true;
	}

	public int co2Emission() {
		return 0;
	}

	public int getCo2Value() {
		return co2Value;
	}

	public void setCo2Value(int co2Value) {
		this.co2Value = co2Value;
	}
}