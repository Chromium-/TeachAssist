package com.example.teachassist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

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
        web.getSettings().setSaveFormData(false);
        web.getSettings().setSavePassword(false);
        web.getSettings().setUserAgentString("Mozilla/5.0 " +
                "(Windows NT 6.2; " +
                "WOW64) AppleWebKit/537.31 " +
                "(KHTML, like Gecko) Chrome/20 " +
                "Safari/537.31");

        web.clearCache(true);
        web.clearHistory();
        
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        
        web.loadUrl("http://ta.yrdsb.ca/yrdsb/");
        
        web.setWebViewClient(new WebViewClient() {
        	
            ProgressDialog loading = new ProgressDialog(Browser.this);

            @Override
            public void onLoadResource (WebView view, String url) {
                //show progress bar
            	loading.setMessage("Loading...");
            	loading.show();
            	loading.setCanceledOnTouchOutside(false);
            	loading.setCancelable(false);
            }
            
            public void onPageFinished(WebView view, String url) {
            	
              String username=MainActivity.usernameString;
              String password=MainActivity.passwordString;
               
              view.loadUrl("javascript:document.getElementsByName('username')[0].value = '"+username+"'");
              view.loadUrl("javascript:document.getElementsByName('password')[0].value = '"+password+"'");
              view.loadUrl("javascript:document.createElement('form').submit.apply( document.getElementById('loginForm') );");
              
              if (loading.isShowing()) {
            	  loading.dismiss();
              }
              
            }
         });

	}

}
