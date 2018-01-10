package br.uff.faleniteroi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;

public class OptionsMenu {

	private MenuItem item;
	
	public OptionsMenu(MenuItem item) {
		this.item = item;
	}
	
	public boolean onOptionsItemSelected(Activity activity) {
		
		int id = item.getItemId();

		if(id == R.id.action_beginning){
			
			if (activity instanceof MainActivity) {
				return true;
			}
			
			Intent intent = new Intent(activity, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.startActivity(intent);
			return true;
		} 
		
		if (id == R.id.action_settings) {
			
			if (activity instanceof ConfigurationActivity) {
				return true;
			}
			
			Intent intent = new Intent(activity, ConfigurationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
            activity.startActivity(intent);
            return true;
		} 
		
		if (id == R.id.action_about) {
			
	        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setView(activity.getLayoutInflater().inflate(R.layout.view_about, null));
	        builder.setTitle("Sobre");
	        builder.setPositiveButton("Ok", null);
	        builder.create();
	        builder.show();
			return true;
		} 
		
		if (id == R.id.action_exit) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(intent);
			return true;	
		}
		
		if (id == R.id.action_add_request) {
			Intent intent = new Intent(activity, SelectServiceActivity.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			activity.startActivity(intent);
			return true;
		}
		
		if (id == R.id.action_add_opinion) {
			Intent intent = new Intent(activity, OpinionFormActivity.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			activity.startActivity(intent);
			return true;
		}
		
		return false;
	}
	
}
