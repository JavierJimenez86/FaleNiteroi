package br.uff.faleniteroi;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import br.uff.faleniteroi.entity.Request;
import br.uff.faleniteroi.sqlite.RequestDAO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RequestMapActivity extends Activity implements OnMarkerClickListener {

	private GoogleMap googleMap;
	private double latitude = -22.8975554;
	private double longitude = -43.0995201;
	private ArrayList<Request> requests;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_map);

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		googleMap = mapFragment.getMap();	
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 11));
        googleMap.setOnMarkerClickListener(this);
        
        RequestDAO requestDAO = new RequestDAO(this);
        requests = requestDAO.listLastWithLatLon();
        
		Iterator<Request> i = requests.iterator();
		
		while (i.hasNext()) {
			
			Request request = i.next();
			
			MarkerOptions marker = new MarkerOptions().position(new LatLng(request.getLatitude(), request.getLongitude())).title(request.getService());

			if (request.isFinished()) 
			{
				marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_green));
			} 
			else
			{
				marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_orange));
			}

			googleMap.addMarker(marker);
		}
        
	}
	
	public boolean onMarkerClick(final Marker marker) {

		Iterator<Request> i = requests.iterator();
		
		while (i.hasNext()) {
			
			Request request = i.next();

			if (request.comparePosition(marker.getPosition().latitude, marker.getPosition().longitude)) {

				Intent intent = new Intent(this, RequestDetailActivity.class);
				intent.putExtra("HiddenProtocol", true); 
				intent.putExtra("Request", request);  
				startActivity(intent);
				
				break;
			}
		}
		
		return true;
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
}
