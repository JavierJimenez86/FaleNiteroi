package br.uff.faleniteroi;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.uff.faleniteroi.task.GeocoderTask;
import br.uff.faleniteroi.util.NetworkUtil;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectAddressActivity extends Activity implements OnMapClickListener, GooglePlayServicesClient.ConnectionCallbacks {

	private GoogleMap googleMap;
	private double latitude = -22.8975554;
	private double longitude = -43.0995201;
	private AlertDialog.Builder builder;
	
	private LocationClient mLocationClient;
	private Location mCurrentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		googleMap = mapFragment.getMap();		
		googleMap.setMyLocationEnabled(true);
		
		mLocationClient = new LocationClient(this, this, null);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
		googleMap.setOnMapClickListener(this);
		
		//Cancel Button
		final Button btCancel = (Button) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
    			ServiceFormActivity.latitude = 0;
    			ServiceFormActivity.longitude = 0;
            	
            	finish();
            }
        });
		
		//Confirm Button
		final Button btConfirm = (Button) findViewById(R.id.btConfirm);
		btConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	final TextView tvAddressSelected = (TextView) findViewById(R.id.tvAddressSelected);

            	ServiceFormActivity.etAddress.requestFocus();
            	ServiceFormActivity.etAddress.setText(tvAddressSelected.getText());

            	finish();
            }
        });
	}
	
	@Override
	public void onMapClick(LatLng point) {
		
		final Button btConfirm = (Button) findViewById(R.id.btConfirm);
		btConfirm.setEnabled(false);
		
		if (!NetworkUtil.isConnected(getApplicationContext())) {
			
			builder = new AlertDialog.Builder(this);
			builder.setMessage("Nenhuma conexão com a internet ativa");
            builder.setPositiveButton(R.string.ok, null);
            builder.create();
            builder.show();
            
		} else {
			
			final TextView tvAddressSelected = (TextView) findViewById(R.id.tvAddressSelected);
			tvAddressSelected.setText("Buscando endereço...");
			
			MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("Endereço da solicitação");
			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_red));
			googleMap.clear();
			googleMap.addMarker(marker);
			
			//task
			GeocoderTask geocoderTask = new GeocoderTask(getApplicationContext(), getWindow().getDecorView().getRootView());
			geocoderTask.execute(point);
			
			ServiceFormActivity.latitude = point.latitude;
			ServiceFormActivity.longitude = point.longitude;
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		OptionsMenu menu = new OptionsMenu(item);
		return menu.onOptionsItemSelected(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }
    
    @Override
    protected void onStop() {
        mLocationClient.disconnect();
        super.onStop();
    }

	@Override
	public void onConnected(Bundle arg0) {
		mCurrentLocation = mLocationClient.getLastLocation();
		
		if (mCurrentLocation != null) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 17));
		}
	}

	@Override
	public void onDisconnected() {
		
	}
}
