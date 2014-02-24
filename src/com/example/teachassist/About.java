package com.example.teachassist;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About extends Activity {

	Button source, contact, download;
	String installedVersion; //version num taken from manifest. can only be declared as string
	double latestVersion, installedVersionValue; //cast the string above into a double	
	String latestOnServerString;
	double latestOnServerValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);		

		source = (Button) findViewById(R.id.bSource);
		
		source.setOnClickListener(new OnClickListener() { //open git repo of this app
		    public void onClick(View v) {
		        Intent downloadFromServer = new Intent();
		        downloadFromServer.setAction(Intent.ACTION_VIEW);
		        downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		        downloadFromServer.setData(Uri.parse("https://github.com/Chromium-/TeachAssist"));
		        startActivity(downloadFromServer);
		    }
		});
		
		contact = (Button) findViewById(R.id.bContact);
		
		contact.setOnClickListener(new OnClickListener() { //open email intent
		    public void onClick(View v) {
		        Intent downloadFromServer = new Intent();
		        downloadFromServer.setAction(Intent.ACTION_VIEW);
		        downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		        downloadFromServer.setData(Uri.parse("mailto:priyesh.96@hotmail.com"));
		        startActivity(downloadFromServer);
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
			
		download = (Button) findViewById(R.id.bDownload);
			
		download.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	
		    	final AsyncTask<Object,Object,String> task = new AsyncTask<Object,Object,String>() {
		    		   protected String doInBackground(Object... o) {
		    		        try {
		    		            URL site = new URL("http://priyeshserver.tk/Files/TeachAssist/latest.txt");
		    		            Scanner s = new Scanner(site.openStream());
		    		            return s.nextLine();
		    		        }
		    		        catch(MalformedURLException e) { 
		    		            throw new RuntimeException("Incorrect URL", e);
		    		        }
		    		        catch(IOException e) {
		    		            throw new RuntimeException("Can't fetch file content from url", e);
		    		        }   
		    		   }

		    		   protected void onPostExecute(String latestOnServerString) {
		    		       
		   			    	latestOnServerValue = Double.parseDouble(latestOnServerString);
					    	
					    	if (installedVersionValue<latestOnServerValue) { //If latest version available on server is higher than installed version
				    			   
					    		AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
				    			   builder.setMessage("Version " + latestOnServerValue + " was found on the server.\n\nWould you like to install it?")
				    			   	  .setTitle ("Update available")
				    			      .setCancelable(false)
				    			      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				    			          public void onClick(DialogInterface dialog, int id) {
									    	Intent downloadFromServer = new Intent();
									    	downloadFromServer.setAction(Intent.ACTION_VIEW);
									    	downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
									    	downloadFromServer.setData(Uri.parse("http://priyeshserver.tk/Files/TeachAssist/TeachAssist-" + latestOnServerValue + ".apk"));
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
					    	
					    	else if (installedVersionValue==latestOnServerValue) { //If user clicks the update button while they already have the latest, let them know what's up
							    Toast.makeText(getApplicationContext(), "No update found.\nYou are on the latest version: (" + installedVersionValue + ")",
							    Toast.LENGTH_LONG).show();	
					    	}					    	
		    		  }		    		   
		    	};
		        task.execute();			    		
		    }	    
		}); 					
	}
}
