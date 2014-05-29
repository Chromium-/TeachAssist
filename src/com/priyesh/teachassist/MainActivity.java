/*   
 * Teach Assist Android App
 * Copyright (C) 2014 Priyesh Patel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.priyesh.teachassist;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.priyesh.teachassist.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText username, password;
	Button login;

	CheckBox rememberCheckBox;
	SharedPreferences loginCredentials;
	SharedPreferences.Editor loginCredentialsEditor;
	Boolean remember;

	public static String usernameString;
	public static String passwordString;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView)this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
		.addTestDevice("2797F5D9304B6B3A15771A0519A4F687")  // HTC Desire
		.build();
		adView.loadAd(adRequest);		

		final boolean browser = true;
		// True will open Browser Activity
		// False will open Parser Activity

		username = (EditText) findViewById(R.id.etUsername);
		username.setInputType(InputType.TYPE_CLASS_TEXT);

		password = (EditText) findViewById(R.id.etPassword);
		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

		rememberCheckBox = (CheckBox)findViewById(R.id.cbRemember);
		loginCredentials = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginCredentialsEditor = loginCredentials.edit();

		remember = loginCredentials.getBoolean("remember", false);
		if (remember == true) {
			username.setText(loginCredentials.getString("username", ""));
			password.setText(loginCredentials.getString("password", ""));
			rememberCheckBox.setChecked(true);
		}	    

		login = (Button) findViewById(R.id.bLogin);

		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){

				usernameString = username.getText().toString();
				passwordString = password.getText().toString();

				if (rememberCheckBox.isChecked()) {
					loginCredentialsEditor.putBoolean("remember", true);
					loginCredentialsEditor.putString("username", usernameString);
					loginCredentialsEditor.putString("password", passwordString);
					loginCredentialsEditor.commit();
				} 
				else {
					loginCredentialsEditor.clear();
					loginCredentialsEditor.commit();
				}

				if (usernameString.length()!=0 && passwordString.length()!=0) {
					if (browser){
						Intent browser = new Intent(MainActivity.this, Browser.class);
						startActivity(browser);
					}
					else {
						Intent parser = new Intent(MainActivity.this, Parser.class);
						startActivity(parser);
					}
				}
				else {
					Toast.makeText(getApplicationContext(), "Please enter a username and password",
							Toast.LENGTH_SHORT).show();					
				}
			}
		});				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == R.id.menu_about) { //about button in actionbar
			openAbout();
			return true;
		}
		else if (menuItem.getItemId() == R.id.menu_exit) { //exit button in actionbar
			exit();
			return true;
		}
		return false;
	}   

	private void openAbout() { //open about section
		Intent about = new Intent(MainActivity.this, About.class);
		startActivity(about);
	}

	private void exit() { //exit app
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("Are you sure you want to exit?")
		.setTitle ("Confirm exit")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent exit = new Intent(Intent.ACTION_MAIN);
				exit.addCategory(Intent.CATEGORY_HOME);
				exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exit);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog updateAlert = builder.create();
		updateAlert.show();		

	}
}
