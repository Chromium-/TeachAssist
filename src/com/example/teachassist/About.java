package com.example.teachassist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
	double latestVersion, installedVersionValue;
	String installedVersion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);		

		source = (Button) findViewById(R.id.bSource);
		
		source.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Intent downloadFromServer = new Intent();
		        downloadFromServer.setAction(Intent.ACTION_VIEW);
		        downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		        downloadFromServer.setData(Uri.parse("https://github.com/Chromium-/TeachAssist"));
		        startActivity(downloadFromServer);
		    }
		});
		
		contact = (Button) findViewById(R.id.bContact);
		
		contact.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Intent downloadFromServer = new Intent();
		        downloadFromServer.setAction(Intent.ACTION_VIEW);
		        downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		        downloadFromServer.setData(Uri.parse("mailto:priyesh.96@hotmail.com"));
		        startActivity(downloadFromServer);
		    }
		});
		
		
        try {
            PackageInfo appInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            installedVersion = appInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Handle exception
        }   
        
        //Latest version available on my server. Must update this value for each new release
		latestVersion = 0.5;
		
		//Convert string value of installed version to double so that it can be compared with value of latest version		
		installedVersionValue = Double.parseDouble(installedVersion); 
		
		download = (Button) findViewById(R.id.bDownload);
		
		download.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	
		    	if (installedVersionValue<latestVersion) { //If latest version available on server is greater than installed version, download the latest
		    		Intent downloadFromServer = new Intent();
		    		downloadFromServer.setAction(Intent.ACTION_VIEW);
		    		downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		    		downloadFromServer.setData(Uri.parse("http://priyeshserver.tk/Files/TeachAssist/TeachAssist-" + latestVersion + ".apk"));
		    		startActivity(downloadFromServer);
		    	}
		    	else if (installedVersionValue==latestVersion) { //If user clicks the update button while they already have the latest, let them no what's up
				    Toast.makeText(getApplicationContext(), "You are already running the latest version (" + installedVersionValue +")",
				    Toast.LENGTH_LONG).show();	
		    	}
		    }
		}); 
		
	}

}
