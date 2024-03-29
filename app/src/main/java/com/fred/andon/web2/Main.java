package com.fred.andon.web2;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;

public class Main extends Activity {

	Thread nwT;
	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogcatHelper.getInstance(this).start();
		AndonConfig.loadSavingData(this);

		setContentView(R.layout.main);  // This needs to be done before
										// trying to findViewById
										// I imagine it sets up where to look
		//
		Resources res = getResources();
		WebView wv = (WebView) findViewById(R.id.my_webview);
		WebSettings webSettings = wv.getSettings();
		//
		wv.setVisibility(View.INVISIBLE);
		
		webSettings.setDatabaseEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportMultipleWindows(false);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);

		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 直接加载URL，显示页面。
				view.loadUrl(url);
				return true;
				
//				Resources res = getResources();
//				if (Uri.parse(url).getHost().contains(res.getString(R.string.host)) ||
//						Uri.parse(url).getScheme().contains("file")) {
//					// This is my web site, so do not override; let my WebView
//					// load the page
//					return false;
//				}
//				// Otherwise, the link is not for a page on my site, so launch
//				// another Activity that handles URLs
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				startActivity(intent);
//				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				WebView wv = (WebView) findViewById(R.id.my_webview);
				ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
				progressBar.setVisibility(View.GONE);
				wv.setVisibility(View.VISIBLE);
			}
		});

		// 仅用于调试设置，长按弹设置首页地址对话框。
		wv.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				SetMainUrlDialog dialog = new SetMainUrlDialog();
				dialog.setOnTextEnteredListener(new SetMainUrlDialog.TextEnteredListener() {

					@Override
					public void onTextEntered(String url) {
						AndonConfig.saveMainUrl(Main.this, url);
						System.exit(0);
					}
				});
				dialog.show(getFragmentManager(), "missiles");
				return false;
			}
		});

//		wv.loadUrl(res.getString(R.string.url));
		wv.loadUrl(AndonConfig.getMainUrl());
		
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		WebView wv = (WebView) findViewById(R.id.my_webview);
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
			wv.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}


	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}