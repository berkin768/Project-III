package com.akaydin.berkin.carmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

	private SeekBar seekBar;
	private TextView seekNumber;
	private static int value = 500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekNumber = (TextView)findViewById(R.id.seeknumber_text);
		setSeekbarOperations();
	}


	public void setSeekbarOperations(){
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int seekValue = seekBar.getProgress();
				if(seekValue ==0){
					seekNumber.setText("0.25 SEC");
					value = 250;
				}
				else if(seekValue == 1){
					seekNumber.setText("0.50 SEC");
					value = 500;
				}
				else{
					seekNumber.setText("1 SEC");
					value = 1000;
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent Intent = new Intent(this,MainActivity.class);
				Intent.putExtra("value",value);
				startActivity(Intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
