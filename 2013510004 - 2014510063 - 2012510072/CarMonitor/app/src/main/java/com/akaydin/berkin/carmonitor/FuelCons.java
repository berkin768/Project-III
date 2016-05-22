package com.akaydin.berkin.carmonitor;

/**
 * Created by mesutsoylu on 01/05/16.
 */
public class FuelCons extends Sensor {
    private String fuelLevel;

    public String estimatedRoad(String fuel, String kilometerPerFuel){
        double doubleValue = 0;
        if(fuel.contains(",")){
            String [] valueCharacters = null;
            valueCharacters = fuel.split(",");
            doubleValue = Double.parseDouble(valueCharacters[0]);
        }
        else{
            doubleValue = Double.parseDouble(fuel);
        }

        int currentFuel = (int)doubleValue;
        int kilometerWithOneLiter = (int)Double.parseDouble(kilometerPerFuel);

        int estimatedRoad = currentFuel * kilometerWithOneLiter;

        return Integer.toString(estimatedRoad);
    }

    public String getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
}
