package com.akaydin.berkin.carmonitor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by mesutsoylu on 01/05/16.
 */
public class Logger {
	Queue lastLogs = new LinkedList();
	String logFile = "log.ms";

	void add(int input) {
		add(String.valueOf(input));
	}

	void add(String input) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = dateFormat.format(date);

		lastLogs.add(now + "\t" + input);
		write2File();
	}

	void write2File() {
		String data2Send = "";
		if (lastLogs.size() > 50) {
			while (!lastLogs.isEmpty()) {
				data2Send += lastLogs.remove() + "/n";
			}
			Operations.write2File(logFile, data2Send);
		}
	}
}
