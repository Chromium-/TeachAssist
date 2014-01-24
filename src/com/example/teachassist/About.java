package com.example.teachassist;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends Activity {

	TextView git, request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		git = (TextView)findViewById(R.id.git);
		git.setClickable(true);
		git.setMovementMethod(LinkMovementMethod.getInstance());
		String gitLink = "<a href='https://github.com/Chromium-/TeachAssist'> Source Code</a>";
		git.setText(Html.fromHtml(gitLink));
		
		request = (TextView)findViewById(R.id.request);
		request.setClickable(true);
		request.setMovementMethod(LinkMovementMethod.getInstance());
		String emailLink = "<a href='mailto:priyesh.96@hotmail.com'> Make a feature request or send a bug report</a>";
		request.setText(Html.fromHtml(emailLink));
	}

}
