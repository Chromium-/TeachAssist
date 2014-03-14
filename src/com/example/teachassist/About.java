package com.example.teachassist;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class About extends PreferenceActivity {

	String installedVersion, latestOnServerString;
	double latestVersion, installedVersionValue, latestOnServerValue;
	
	List<Exception> exceptions = new ArrayList<Exception>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about_preference);	

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		Preference source = (Preference) findPreference("source");
		source.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent git = new Intent();
				git.setAction(Intent.ACTION_VIEW);
				git.addCategory(Intent.CATEGORY_BROWSABLE);
				git.setData(Uri.parse("https://github.com/Chromium-/TeachAssist"));
				startActivity(git);
				return true; 
			}
		});

		Preference contact = (Preference) findPreference("contact");
		contact.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent email = new Intent();
				email.setAction(Intent.ACTION_VIEW);
				email.addCategory(Intent.CATEGORY_BROWSABLE);
				email.setData(Uri.parse("mailto:priyesh.96@hotmail.com"));
				startActivity(email);
				return true; 
			}
		});

		try { //Save current version from manifest into variable
			PackageInfo appInfo = getPackageManager().getPackageInfo(getPackageName(), 0); 
			installedVersion = appInfo.versionName; 
		} 
		catch (PackageManager.NameNotFoundException e) {    
		}   

		//Convert string value of installed version to double so that it can be compared with value of latest version		
		installedVersionValue = Double.parseDouble(installedVersion); 
		
		Preference update = (Preference) findPreference("update");
		update.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				
				final AsyncTask<Object,Object,String> task = new AsyncTask<Object,Object,String>() {
					protected String doInBackground(Object... o) {
						String fakeVersion = "-1";
						try {
							URL site = new URL("http://70.49.210.232/Files/TeachAssist/latest.txt");
							Scanner s = new Scanner(site.openStream());
							return s.nextLine();
						}
						catch(IOException e) {							
							exceptions.add(e);
						}
						return fakeVersion; //return -1, which is an invalid version, on IOException. This allows for better handling.
					}					
					
					protected void onPostExecute(String latestOnServerString) {

						latestOnServerValue = Double.parseDouble(latestOnServerString);

						if (installedVersionValue<latestOnServerValue && latestOnServerValue>0 ) { 
							//If latest version available on server is higher than installed version
							AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
							builder.setMessage("Version " + latestOnServerValue + " was found on the server.\n\nWould you like to install it?")
							.setTitle ("Update available")
							.setCancelable(false)
							.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Intent downloadFromServer = new Intent();
									downloadFromServer.setAction(Intent.ACTION_VIEW);
									downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
									downloadFromServer.setData(Uri.parse("http://70.49.210.232/Files/TeachAssist/TeachAssist-" + latestOnServerValue + ".apk"));
									startActivity(downloadFromServer);
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

						else if (installedVersionValue==latestOnServerValue) { 
						//If user clicks the update button while they already have the latest, let them know what's up
							AlertDialog.Builder builder2 = new AlertDialog.Builder(About.this);
							builder2.setMessage("You are already on the latest version:" + installedVersionValue)
							.setTitle ("No update available")
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
							AlertDialog noUpdateAlert = builder2.create();
							noUpdateAlert.show();								
						}
						
						else if (latestOnServerValue==-1) { 
							//-1 was returned. This indicates that the server was offline causing an IOException. Explain this to the user.
							AlertDialog.Builder builder3 = new AlertDialog.Builder(About.this);
							builder3.setMessage("The update server is currently offline. Therefore the app was unable to make contact in order to check for newer versions. Try again later.")
							.setTitle ("Update failed")
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
							AlertDialog noUpdateAlert = builder3.create();
							noUpdateAlert.show();								
						}												
					}		    		   
				};
				task.execute();	
				return true; 
			}
		});			
	}
}
