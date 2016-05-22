package com.akaydin.berkin.carmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mesutsoylu on 01/05/16.
 */
public class Operations extends AppCompatActivity {
	private ImageView images;
	private Button loadButton;
	private ArrayList <String> input = new ArrayList<>();
	private boolean readingFlag = false;
	private Bundle bundle = new Bundle();
	private Intent intent;
	private double mainValue;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);


		images = (ImageView)findViewById(R.id.imageView);
		loadButton = (Button) findViewById(R.id.load_button);

		loadButton();
		if(readingFlag)
			images.setImageResource(R.drawable.ok);
		else{
			images.setImageResource(R.drawable.no);
		}
		intent = getIntent();

		if(intent != null){
			mainValue = intent.getDoubleExtra("value",-1);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this,MainActivity.class).putExtras(bundle);
				startActivity(intent);
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void loadButton(){
		loadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readingFlag = parseCSV(readFile());
				if(readingFlag)
					images.setImageResource(R.drawable.ok);
			}
		});
	}

	public ArrayList readFile() {
		BufferedReader br;
		try {
			String currentLine;

			br = new BufferedReader(
					new InputStreamReader(getAssets().open("input.txt")));
			while ((currentLine = br.readLine()) != null) {
				input.add(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	public boolean parseCSV(ArrayList input){
		String []  rpm = new String [3600];
		String []  speed = new String [3600];
		String []  engineTemp = new String [3600];
		String []  fuel = new String [3600];
		String []  co2 = new String [3600];
		String []  coolantTemp =new String [3600];
		String []  torque = new String [3600];
		String []  fuelPerKM = new String [3600];
		try{
			for (int i = 1; i < input.size(); i++) {
				String [] parsedInput = input.get(i).toString().split(";");
				rpm[i-1] = parsedInput[1];
				speed[i-1] = parsedInput[2];
				engineTemp[i-1] = parsedInput[3];
				fuel[i-1] = parsedInput[4];
				co2[i-1] = parsedInput[5];
				coolantTemp[i-1] = parsedInput[6];
				torque[i-1] = parsedInput[7];
				fuelPerKM[i-1] = parsedInput[8];
			}
			readingFlag = true;
		}
		catch (Exception IO){
			return false;
		}
		bundle.putStringArray("fuelperkm",fuelPerKM);
		bundle.putStringArray("rpm",rpm);
		bundle.putStringArray("speed",speed);
		bundle.putStringArray("engineTemp",engineTemp);
		bundle.putStringArray("fuel",fuel);
		bundle.putStringArray("co2",co2);
		bundle.putStringArray("coolantTemp",coolantTemp);
		bundle.putStringArray("torque",torque);
		bundle.putBoolean("flag",readingFlag);
		bundle.putDouble("valueOperations",mainValue);
		return true;
	}
}