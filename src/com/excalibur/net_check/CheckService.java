package com.excalibur.net_check;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CheckService extends IntentService {

	private float lastCheckTime;
	Calendar calendar = Calendar.getInstance();

	private static final long CHECK_INTERVAL = 5000;
	long curTime;
	// GMT time server
	public static final String TIME_SERVER = "time-a.nist.gov";

	public CheckService() {
		super(null);
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		// Gets data from the incoming Intent
		String dataString = workIntent.getDataString();
		// Do work here, based on the contents of dataString
		while (true) {
			try {
				// update every 5 seconds
				Thread.sleep(CHECK_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!isNetworkAvailable()) {
				continue;
			}

			curTime = getCurrentNetworkTime();
			SimpleDateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
			calendar.setTimeInMillis(curTime);
			Log.d("cal", df.format(calendar.getTime()));

		}
	}

	/**
	 * check if net connection is on
	 * 
	 * @return true if available
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static long getCurrentNetworkTime() {
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress;
		long returnTime = 0;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);

			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnTime;
	}

}
