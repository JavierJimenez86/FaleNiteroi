package br.uff.faleniteroi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {
	
	public static final String MY_PREFERENCES = "MyPrefs";
	public static final String PREFERENCES_NAME = "userName";
	public static final String PREFERENCES_EMAIL = "userEmail";

	private SharedPreferences sharedpreferences;
	
	public Preferences(Context context) {
		sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	public void editPreferences(String name, String email) {
		Editor editor = sharedpreferences.edit();
    	editor.putString(PREFERENCES_NAME, name.trim());
    	editor.putString(PREFERENCES_EMAIL, email.trim());
    	editor.commit();	
	}
	
	public boolean isRegistered() {
		String name = sharedpreferences.getString(Preferences.PREFERENCES_NAME, "");
		return name.equals("") ? false : true;
	}
	
	public String getPreferences(String field){
		String restoredText = sharedpreferences.getString(field, "");
		return restoredText;
	}
}
