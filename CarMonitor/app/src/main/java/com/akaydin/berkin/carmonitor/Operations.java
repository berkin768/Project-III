package com.akaydin.berkin.carmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
	final int celsius2Fahrenheit(int celsiusInput) {
		return (int) (celsiusInput * 1.8) + 32;
	}
	final	int km2Mile(int meterInput) {
		return (int) (meterInput * 0.6214);
	}

	private TextView textView;
	private ImageView images;
	private Button loadButton;
	private ArrayList <String> input = new ArrayList<>();
	private static boolean readingFlag = false;
	private static Bundle bundle = new Bundle();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		textView = (TextView) findViewById(R.id.mainTextView);
		images = (ImageView)findViewById(R.id.imageView);
		loadButton = (Button) findViewById(R.id.load_button);

		loadButton();
		if(readingFlag)
			images.setImageResource(R.drawable.ok);
		else{
			images.setImageResource(R.drawable.no);
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
		ArrayList second = new ArrayList();
		ArrayList rpm = new ArrayList();
		ArrayList speed = new ArrayList();
		ArrayList engineTemp = new ArrayList();
		ArrayList fuel = new ArrayList();
		try{
			for (int i = 1; i < input.size(); i++) {
				String [] parsedInput = input.get(i).toString().split(";");
				second.add(parsedInput[0]);
				rpm.add(parsedInput[1]);
				speed.add(parsedInput[2]);
				engineTemp.add(parsedInput[3]);
				fuel.add(parsedInput[4]);
			}
			readingFlag = true;
		}
		catch (Exception IO){
			return false;
		}
		bundle.putStringArrayList("second",second);
		bundle.putStringArrayList("rpm",rpm);
		bundle.putStringArrayList("speed",speed);
		bundle.putStringArrayList("engineTemp",engineTemp);
		bundle.putStringArrayList("fuel",fuel);
		bundle.putBoolean("flag",readingFlag);
		return true;
	}

	static void write2File(String fileName, String inputData) {
		try {
			File file = new File(fileName);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(inputData);
			bw.close();
		} catch (IOException e) {
			if (IntSettings.debugMode) {
				e.printStackTrace();
			}
		}
	}
}