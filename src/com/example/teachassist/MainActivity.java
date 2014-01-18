package com.example.teachassist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText username, password;
	Button save;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		username = (EditText) findViewById(R.id.etUsername);
		username.setInputType(InputType.TYPE_CLASS_TEXT);
		String usernameString = username.getText().toString();
		
		password = (EditText) findViewById(R.id.etPassword);
		password.setInputType(InputType.TYPE_CLASS_TEXT);
		String passwordString = password.getText().toString();
		
		save = (Button) findViewById(R.id.bSave);
		
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				
				Intent i = new Intent(MainActivity.this, Browser.class);
				startActivity(i);
			}
		});
				
	}

}
