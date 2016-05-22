package com.akaydin.berkin.carmonitor;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{

    private TextView speedValue;
    private ImageView speedImage;
    private TextView rpmValue;
    private ImageView rpmImage;
    private TextView secondLabel;
    private TextView engineTemperatureValue;
    private ImageView engineTempImage;
    private TextView fuelAmountValue;
    private ImageView fuelImage;
    private TextView co2Value;
    private ImageView co2Image;
    private TextView torqueValue;
    private ImageView torqueImage;
    private TextView coolantValue;
    private ImageView coolantImage;
    private TextView estimatedRoad;
    private TextView kilometerPerLiterValue;
    private ImageView kilometerPerLiterImage;

    private TextView secondInfo;

    private Button startButton;
    private int index = 0;
    private Handler mHandler;
    private Intent intent;
    private double value;
    private int clicked = 0;
    private long repeatBySecond;

    private Sensor rpm = new RPM();
    private Sensor engineTemperature = new EngineTemperature();
    private Sensor speed = new Speed();
    private Sensor co2 = new CO2Emission();
    private Sensor coolantTemperature = new CoolantTemperature();
    private Sensor fuelConsumption = new FuelCons();
    private Sensor torque = new Torque();
    private Sensor kilometerPerLiter = new KilometerPerLiter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedValue = (TextView) findViewById(R.id.speed_value);
        rpmValue = (TextView) findViewById(R.id.rpm_value);
        secondLabel = (TextView) findViewById(R.id.second_label);
        fuelAmountValue = (TextView) findViewById(R.id.fuel_amount_value);
        engineTemperatureValue = (TextView) findViewById(R.id.engine_temperature_value);
        startButton = (Button) findViewById(R.id.second_button);
        secondInfo = (TextView) findViewById(R.id.secondInfo);
        speedImage = (ImageView) findViewById(R.id.speedImageView);
        rpmImage =(ImageView) findViewById(R.id.rpmImageView);
        engineTempImage = (ImageView) findViewById(R.id.engineTempImageView);
        fuelImage = (ImageView) findViewById(R.id.fuelImageView);
        co2Value = (TextView) findViewById(R.id.co2_value);
        co2Image = (ImageView) findViewById(R.id.co2ImageView);
        torqueValue = (TextView) findViewById(R.id.torque_value);
        torqueImage = (ImageView) findViewById(R.id.torqueImageView);
        coolantValue = (TextView) findViewById(R.id.coolant_value);
        coolantImage = (ImageView) findViewById(R.id.coolantImageView);
        estimatedRoad = (TextView)findViewById(R.id.estimatedRoad_value);
        kilometerPerLiterValue =(TextView)findViewById(R.id.kilometerPerLiter_value);
        kilometerPerLiterImage =(ImageView) findViewById(R.id.kilometerPerLiterImage);
        startButton.setText("START");

        intent = getIntent();

        buttonClicked();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(intent != null){
            value = intent.getIntExtra("value",0);
            secondInfo.setText((value / 1000) + " secs");

            mHandler = new Handler();
            if(intent.getExtras()!=null){
                repeatBySecond = (long)intent.getExtras().getDouble("valueOperations");
                mHandler.postDelayed(mRunnable, repeatBySecond);
            }
        }
    }

    public void classOperations(Bundle input, int select){
        rpm.setSensorID(0);
        rpm.setSensorName("rpm");
        engineTemperature.setSensorID(1);
        engineTemperature.setSensorName("engine temperature");
        speed.setSensorID(2);
        speed.setSensorName("speed");
        co2.setSensorID(3);
        co2.setSensorName("co2");
        coolantTemperature.setSensorID(4);
        coolantTemperature.setSensorName("coolant temperature");
        fuelConsumption.setSensorID(5);
        fuelConsumption.setSensorName("fuel");
        torque.setSensorID(6);
        torque.setSensorName("torque");
        kilometerPerLiter.setSensorID(7);
        kilometerPerLiter.setSensorName("kilometer per liter");


        ((RPM)rpm).setRpm(input.getStringArray("rpm")[select]);
        ((EngineTemperature)engineTemperature).setEngineTemperature(input.getStringArray("engineTemp")[select]);
        ((Speed)speed).setSpeed(input.getStringArray("speed")[select]);
        ((FuelCons)fuelConsumption).setFuelLevel(input.getStringArray("fuel")[select]);
        ((CO2Emission)co2).setCo2Value(input.getStringArray("co2")[select]);
        ((CoolantTemperature)coolantTemperature).setCoolantTemperature(input.getStringArray("coolantTemp")[select]);
        ((Torque)torque).setTorque(input.getStringArray("torque")[select]);
        ((KilometerPerLiter)kilometerPerLiter).setKilometerPerLiter(input.getStringArray("fuelperkm")[select]);

        secondInfo.setText(input.getDouble("valueOperations") / 1000 + " secs");
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Bundle input = intent.getExtras();
            if (input != null && clicked % 2 != 0 && input.getBoolean("flag")){
                startButton.setText("PAUSE");
                classOperations(input,index);
                displayOperations();
                index++;
            }
            else{
                startButton.setText("START");
            }
            mHandler.postDelayed(mRunnable, repeatBySecond);
        }
    };

    public void buttonClicked(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked++;
            }
        });
    }

    public void normalValues(String value, String sensorName){
        double doubleValue = 0;
        if(value.contains(",")){
            String [] valueCharacters = null;
            valueCharacters = value.split(",");
            doubleValue = Double.parseDouble(valueCharacters[0]);
        }
        else{
            doubleValue = Double.parseDouble(value);
        }

        double normalRPM = 1000;
        double normalEngineTemp = 170;
        double normalSpeed = 90;
        double normalCo2 = 80;
        double normalTorque = 180;
        double normalCoolantTemp = 130;
        double normalFuel = 20;
        double normalKilometerPerLiter = 8;
        switch (sensorName){
            case "rpm":
                if(doubleValue > normalRPM)
                    rpmImage.setImageResource(R.drawable.warning);
                else
                    rpmImage.setImageResource(R.drawable.normal);
                break;
            case "engine temperature":
                if(doubleValue > normalEngineTemp)
                    engineTempImage.setImageResource(R.drawable.warning);
                else
                    engineTempImage.setImageResource(R.drawable.normal);
                break;

            case "speed":
                if(doubleValue > normalSpeed)
                    speedImage.setImageResource(R.drawable.warning);
                else
                    speedImage.setImageResource(R.drawable.normal);
                break;

            case "co2":
                if(doubleValue > normalCo2)
                    co2Image.setImageResource(R.drawable.warning);
                else
                    co2Image.setImageResource(R.drawable.normal);
                break;

            case "coolant temperature":
                if(doubleValue > normalCoolantTemp)
                    coolantImage.setImageResource(R.drawable.warning);
                else
                    coolantImage.setImageResource(R.drawable.normal);
                break;

            case "torque":
                if(doubleValue > normalTorque)
                    torqueImage.setImageResource(R.drawable.warning);
                else
                    torqueImage.setImageResource(R.drawable.normal);
                break;

            case "fuel":
                if(doubleValue < normalFuel)
                    fuelImage.setImageResource(R.drawable.warning);
                else
                    fuelImage.setImageResource(R.drawable.normal);
                break;

            case "kilometer per liter":
                if(doubleValue < normalKilometerPerLiter)
                    kilometerPerLiterImage.setImageResource(R.drawable.warning);
                else
                    kilometerPerLiterImage.setImageResource(R.drawable.normal);
                break;
        }
    }

    public void displayOperations() {
        secondLabel.setText(index+"");  //SECOND PRINTED

        rpmValue.setText(((RPM)rpm).getRpm());                                                //RPM PRINTED
        normalValues(((RPM)rpm).getRpm(),rpm.getSensorName());

        engineTemperatureValue.setText(((EngineTemperature)engineTemperature).getEngineTemperature() + " \u00b0C");  //ENGINE TEMPERATURE
        normalValues(((EngineTemperature)engineTemperature).getEngineTemperature(),engineTemperature.getSensorName());

        speedValue.setText(((Speed)speed).getSpeed() + " KM/h");
        normalValues(((Speed)speed).getSpeed(),speed.getSensorName());

        fuelAmountValue.setText(((FuelCons)fuelConsumption).getFuelLevel() + " L");
        String km =((FuelCons) fuelConsumption).estimatedRoad(((FuelCons)fuelConsumption).getFuelLevel(),((KilometerPerLiter)kilometerPerLiter).getKilometerPerLiter());
        estimatedRoad.setText(km);

        co2Value.setText(((CO2Emission)co2).getCo2Value() + " m\u00b3");
        normalValues(((CO2Emission)co2).getCo2Value(),co2.getSensorName());

        torqueValue.setText(((Torque)torque).getTorque());
        normalValues(((Torque)torque).getTorque(),torque.getSensorName());

        coolantValue.setText(((CoolantTemperature)coolantTemperature).getCoolantTemperature()+ " \u00b0C");
        normalValues(((CoolantTemperature)coolantTemperature).getCoolantTemperature(),coolantTemperature.getSensorName());

        kilometerPerLiterValue.setText(((KilometerPerLiter)kilometerPerLiter).getKilometerPerLiter() + " KM");
        normalValues(((KilometerPerLiter)kilometerPerLiter).getKilometerPerLiter(),kilometerPerLiter.getSensorName());
    }

    //Menünün içeriği burada ayarlanır.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.load_button:
                intent = new Intent(this, Operations.class);
                intent.putExtra("value",value);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                System.exit(0);
                return true;
            case R.id.map_api:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Ayarlar vb. tarzı menü bu fonksiyonla yaratılıp XML e bağlanır
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
}