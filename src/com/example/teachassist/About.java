package com.example.teachassist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class About extends Activity {

	//TextView git, request;
	Button source, contact, download;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
/*		git = (TextView)findViewById(R.id.git);
		git.setClickable(true);
		git.setMovementMethod(LinkMovementMethod.getInstance());
		String gitLink = "<a href='https://github.com/Chromium-/TeachAssist'> Source Code</a>";
		git.setText(Html.fromHtml(gitLink));
		
		request = (TextView)findViewById(R.id.request);
		request.setClickable(true);
		request.setMovementMethod(LinkMovementMethod.getInstance());
		String emailLink = "<a href='mailto:priyesh.96@hotmail.com'> Make a feature request or send a bug report</a>";
		request.setText(Html.fromHtml(emailLink)); */

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
		
		download = (Button) findViewById(R.id.bDownload);
		
		download.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Intent downloadFromServer = new Intent();
		        downloadFromServer.setAction(Intent.ACTION_VIEW);
		        downloadFromServer.addCategory(Intent.CATEGORY_BROWSABLE);
		        downloadFromServer.setData(Uri.parse("http://priyeshserver.tk/Files/TeachAssist"));
		        startActivity(downloadFromServer);
		    }
		});
		
	}

}
