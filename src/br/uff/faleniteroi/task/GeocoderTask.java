package br.uff.faleniteroi.task;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.uff.faleniteroi.R;

import com.google.android.gms.maps.model.LatLng;

public class GeocoderTask extends AsyncTask<LatLng, Integer, String> {

	private Context context;
	private View rootView;
	
    public GeocoderTask (Context context, View rootView) {
    	this.context = context;
    	this.rootView = rootView;
    }
	
	@Override
	protected String doInBackground(LatLng... params) {
		
		LatLng point = params[0]; 
		
		try {
			Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
			
			if (addresses.isEmpty()) {
				return null; //return "Nenhum endereço encontrado";
		    }

	        if (addresses.size() > 0) {
	        	//addresses.get(0).getSubAdminArea() 
	        	//tvAddressSelected.setText(addresses.get(0).getThoroughfare() + ", " + addresses.get(0).getSubThoroughfare() + ", " + addresses.get(0).getSubLocality());
	        	
	        	if (addresses.get(0).getThoroughfare()  == null || addresses.get(0).getSubThoroughfare() == null || addresses.get(0).getSubLocality() == null) {
	        		return null;
	        	}

	        	return addresses.get(0).getThoroughfare() + ", " + addresses.get(0).getSubThoroughfare() + ", " + addresses.get(0).getSubLocality();
	        }
			
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(String result) {
        
		final Button btConfirm = (Button) rootView.findViewById(R.id.btConfirm);
		final TextView tvAddressSelected = (TextView) rootView.findViewById(R.id.tvAddressSelected);
		
		if (result == null) {
			
			btConfirm.setEnabled(false);
            tvAddressSelected.setText("Não foi possível obter o endereço. Tente novamente ou digite o endereço.");

		} else {
			
			btConfirm.setEnabled(true);
			tvAddressSelected.setText(result);
		}
    }

}
