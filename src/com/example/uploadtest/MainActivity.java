package com.example.uploadtest;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	private WebView webview;

	private ValueCallback<Uri> mUploadMessage;  
	private final static int FILECHOOSER_RESULTCODE=1;  

	@Override  
	protected void onActivityResult(int requestCode, int resultCode,  
			Intent intent) {  
		if(requestCode==FILECHOOSER_RESULTCODE)  
		{  
			if (null == mUploadMessage) return;  
			Uri result = intent == null || resultCode != RESULT_OK ? null  
					: intent.getData();  
			mUploadMessage.onReceiveValue(result);  
			mUploadMessage = null;  
		}
	}  

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webview = (WebView) findViewById(R.id.webView1);

		webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://www.script-tutorials.com/demos/199/index.html");
		webview.setWebViewClient(new myWebClient());
		webview.setWebChromeClient(new WebChromeClient() {  
			//The undocumented magic method override  
			//Eclipse will swear at you if you try to put @Override here  
			// For Android 3.0+
			
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {  

				mUploadMessage = uploadMsg;  
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
				i.addCategory(Intent.CATEGORY_OPENABLE);  
				i.setType("*/*");  
				MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);  

			}

			// For Android 3.0+
			public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}

			//For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
				mUploadMessage = uploadMsg;  
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
				i.addCategory(Intent.CATEGORY_OPENABLE);  
				i.setType("*/*");  
				MainActivity.this.startActivityForResult( Intent.createChooser( i, "File Chooser" ), MainActivity.FILECHOOSER_RESULTCODE );

			}

		});
		
		setContentView(webview);
	}
	public class myWebClient extends WebViewClient
	{
	    @Override
	    public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        // TODO Auto-generated method stub
	        super.onPageStarted(view, url, favicon);
	    }

	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        // TODO Auto-generated method stub

	        view.loadUrl(url);
	        return true;

	    }

	    @Override
	    public void onPageFinished(WebView view, String url) {
	        super.onPageFinished(view, url);
	    }
	}

	//flipscreen not loading again
	@Override
	public void onConfigurationChanged(Configuration newConfig){        
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
