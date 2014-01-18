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
	Button login;
	
	public static String usernameString;
	public static String passwordString;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		username = (EditText) findViewById(R.id.etUsername);
		username.setInputType(InputType.TYPE_CLASS_TEXT);
		
		password = (EditText) findViewById(R.id.etPassword);
		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		login = (Button) findViewById(R.id.bLogin);
		
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				usernameString = username.getText().toString();
				passwordString = password.getText().toString();

				Intent i = new Intent(MainActivity.this, Browser.class);
				startActivity(i);
			}
		});
				
	}

}
