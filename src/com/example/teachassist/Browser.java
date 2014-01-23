package com.example.teachassist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
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
            WindowManager.LayoutParams loadingLayout = loading.getWindow().getAttributes();  
            
            @Override
            public void onLoadResource (WebView view, String url) {
            	loadingLayout.dimAmount=0.99f;
            	loading.getWindow().setAttributes(loadingLayout);
            	loading.setMessage("Loading...");
            	loading.setCanceledOnTouchOutside(false);
            	loading.setCancelable(false);
            	loading.show();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.browsermenu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == R.id.menu_refresh) {
	         refresh();
	         return true;
	    }
	    return false;
	}
	
    private void refresh() {
    	web.reload();
    }
}
