package com.example.teachassist;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends Activity {

	TextView git;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		git =(TextView)findViewById(R.id.git);
		git.setClickable(true);
		git.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='https://github.com/Chromium-/TeachAssist'> Source Code </a>";
		git.setText(Html.fromHtml(text));
	}



}
