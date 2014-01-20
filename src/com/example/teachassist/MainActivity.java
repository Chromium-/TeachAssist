package com.example.teachassist;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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

				if (usernameString.length()!=0 && passwordString.length()!=0) {
				Intent i = new Intent(MainActivity.this, Browser.class);
				startActivity(i);
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
		if (menuItem.getItemId() == R.id.menu_about) {
	            openAbout();
	            return true;
	    }
	    return false;
	}
	
    private void openAbout() {
    	Intent j = new Intent(MainActivity.this, About.class);
		startActivity(j);
    }
    
}
