package com.example.teachassist;

import android.os.Bundle;
import android.app.Activity;
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
				    Intent browser = new Intent(MainActivity.this, Browser.class);
				    startActivity(browser);
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
		else if (menuItem.getItemId() == R.id.menu_exit) {
			exit();
			return true;
		}
	    return false;
	}   
    
    private void openAbout() {
    	Intent about = new Intent(MainActivity.this, About.class);
		startActivity(about);
    }
    
    private void exit() {
    	Intent exit = new Intent(Intent.ACTION_MAIN);
    	exit.addCategory(Intent.CATEGORY_HOME);
    	exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(exit);
    }
}
