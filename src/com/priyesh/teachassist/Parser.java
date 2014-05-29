/*   
 * Teach Assist Android App
 * Copyright (C) 2014 Priyesh Patel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.priyesh.teachassist;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Parser extends Activity {

	ArrayList<Double> grades;
	ArrayList<String> courses;
	TextView gradesTV, coursesTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parser);
		
		new MyTask().execute();	
	}

	private class MyTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String html ="";
			try {
				Connection.Response loginForm;
				loginForm = Jsoup.connect("https://ta.yrdsb.ca/yrdsb/")
						.method(Connection.Method.GET)
						.execute();

				// Login to page using user/pass entered in MainActivity
				Document document = Jsoup.connect("https://ta.yrdsb.ca/yrdsb/")
						.data("cookieexists", "false")
						.data("username", MainActivity.usernameString)
						.data("password", MainActivity.passwordString)
						.data("submit", "Login")
						.cookies(loginForm.cookies())
						.post();

				// Convert document into string for easier processing
				html = document.toString();
			    
				Document doc = Jsoup.parse(html);
				//for (int x = 0; x < doc.select("[width=85%], [border=0], [cellspacing=0], [cellpadding=5]").size(); x++){
				System.out.println(doc.select("[width=85%], [border=0], [cellspacing=0], [cellpadding=5]").select("tr").size());
			   // System.out.println(doc.select("[width=85%], [border=0], [cellspacing=0], [cellpadding=5]").select("tr").get(5));
				//}
			    
				// Prepare array to store grades
			    grades = new ArrayList<>();
			    courses = new ArrayList<>();
			    
			    // Regex to search html string for grades, then add to array
			    Pattern p = Pattern.compile("current mark\\s?=\\s?(\\d+\\.?\\d*)");
			    Matcher m = p.matcher(html);
			    while (m.find()) {
			    	grades.add(new Double(m.group(1)));
			    }
			    
			    Pattern p1 = Pattern.compile("([a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{1})");
			    Matcher m1 = p1.matcher(html);
			    while (m1.find()) {
			    	courses.add(new String(m1.group(1)));
			    }
			    
			} catch (IOException e) {
				e.printStackTrace();
			}
			return html;   
		} 

		@Override
		protected void onPostExecute(String result) {        
			
			gradesTV = (TextView)findViewById(R.id.grades);
			coursesTV = (TextView)findViewById(R.id.courses);
			
			// Create single string containing all grades with newline breaks
			StringBuilder builder = new StringBuilder();
			for (Double number : grades) {
			   builder.append(number + "%\n");
			}

			StringBuilder builder1 = new StringBuilder();
			for (String course : courses) {
			   builder.append(course + "\n");
			}
			
			gradesTV.setText(builder.toString());
			coursesTV.setText(builder1.toString());

		}
	}
	
}
