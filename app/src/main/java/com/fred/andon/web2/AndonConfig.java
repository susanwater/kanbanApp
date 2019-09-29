package com.fred.andon.web2;

import android.app.Activity;
import android.content.SharedPreferences;

public class AndonConfig {

	private static final String PRE_NAME = "PREFERENCES";
	private static final String URL_BAIDU = "http://www.baidu.com";
	private static final String URL_ANDON = "http://192.168.1.10:2015/kanban.aspx";
	
	private static String _mainUrl = URL_ANDON;
	
	public static String getMainUrl() {
		return _mainUrl;
	}
	
	public static void loadSavingData(Activity activity) {
		SharedPreferences preferences = activity.getSharedPreferences(PRE_NAME, Activity.MODE_PRIVATE);
		_mainUrl = preferences.getString("mainAddress", URL_ANDON);
	}
	
	public static void saveMainUrl(Activity activity, String url) {
		SharedPreferences settings = activity.getSharedPreferences(PRE_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("mainAddress", url);
		editor.commit();
	}
	
}
