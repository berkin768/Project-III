package com.akaydin.berkin.carmonitor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private TextView speedValue;
    private TextView rpmValue;
    private TextView secondLabel;
    private TextView engineTemperatureValue;
    private TextView fuelAmountValue;
    private int select = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedValue = (TextView) findViewById(R.id.speed_value);
        rpmValue = (TextView) findViewById(R.id.rpm_value);
        secondLabel = (TextView) findViewById(R.id.second_label);
        fuelAmountValue = (TextView) findViewById(R.id.fuel_amount_value);
        engineTemperatureValue = (TextView) findViewById(R.id.engine_temperature_value);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.second_button: {
                Intent intent = getIntent();
                onNewIntent(intent);
                Bundle input = intent.getExtras();
                if (input != null){
                    displayOperations(input,select);
                    select++;
                }
                break;
            }
        }
    }

    public void displayOperations(Bundle input,int select){
        boolean flag = input.getBoolean("flag");
        if(flag){
                secondLabel.setText(input.getStringArrayList("second").get(select));
                rpmValue.setText(input.getStringArrayList("rpm").get(select));
                speedValue.setText(input.getStringArrayList("speed").get(select));
                engineTemperatureValue.setText(input.getStringArrayList("engineTemp").get(select));
                fuelAmountValue.setText(input.getStringArrayList("fuel").get(select));
        }
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