package com.excalibur.net_check;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Startup extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// start your service here
		context.startService(new Intent(context, CheckService.class));
	}

}