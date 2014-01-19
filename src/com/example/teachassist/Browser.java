package com.example.teachassist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Browser extends Activity{

    public WebView web;

	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		
        web = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.getSettings().setUserAgentString("Mozilla/5.0 " +
                "(Windows NT 6.2; " +
                "WOW64) AppleWebKit/537.31 " +
                "(KHTML, like Gecko) Chrome/20 " +
                "Safari/537.31");
        web.clearCache(true);
        web.clearFormData();
        web.clearHistory();                     
        
        web.loadUrl("http://ta.yrdsb.ca/yrdsb/");
        
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
               String username=MainActivity.usernameString;
               String password=MainActivity.passwordString;
               
               view.loadUrl("javascript:document.getElementsByName('username')[0].value = '"+username+"'");
               view.loadUrl("javascript:document.getElementsByName('password')[0].value = '"+password+"'");

               view.loadUrl("javascript:(function(){document.forms['loginForm'].submit();})");	   
            }
         });

	}

}
