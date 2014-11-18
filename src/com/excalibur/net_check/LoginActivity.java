package com.excalibur.net_check;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.GetChars;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static final String MAIL_KEY = "mail";
	private static final String PASS_KEY = "password";
	private static final String DEFAULT_MAIL = "excalibur";
	private static Context CONTEXT;

	private Button login;
	private EditText mail, password;
	private SharedPreferences prefs;
	private Editor editor;
	private boolean isRegistered = true;
	String userMail, userPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		CONTEXT = this;

	}

	@Override
	protected void onResume() {
		super.onResume();
		login = (Button) findViewById(R.id.login_btn);
		login.setOnClickListener(loginClickListener);
		mail = (EditText) findViewById(R.id.login_mail);
		password = (EditText) findViewById(R.id.login_pass);
		prefs = this.getPreferences(Context.MODE_PRIVATE);
		editor = prefs.edit();
		userMail = prefs.getString(MAIL_KEY, DEFAULT_MAIL);
		if (userMail.equals(DEFAULT_MAIL)) {
			login.setText("Register");
			isRegistered = false;
		}
		userPass = prefs.getString(PASS_KEY, "0");
	}

	private OnClickListener loginClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String typeMail = mail.getText().toString();
			String typePass = password.getText().toString();
			if (!isRegistered) {
				if (Utility.isValidMail(typeMail)
						&& Utility.isSafePass(typePass)) {
					userMail = typeMail;
					userPass = typePass;
					editor.putString(MAIL_KEY, userMail);
					editor.putString(PASS_KEY, userPass);
					editor.commit();
					// go to next screen
					Intent intent = new Intent(getApplicationContext(),
							ProgressActivity.class);
					startActivity(intent);
				} else {
					if (!Utility.isValidMail(typeMail)) {
						Toast.makeText(getApplicationContext(),
								"email not valid", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"password must contain at least 6 characters including at least 1 digit",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				if (typeMail.equals(userMail) && typePass.equals(userPass)) {
					// go to next screen
					Intent intent = new Intent(getApplicationContext(),
							ProgressActivity.class);
					startActivity(intent);
				} else if (!typeMail.equals(userMail)) {
					Toast.makeText(CONTEXT, "email does not match",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(CONTEXT, "password does not match",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
