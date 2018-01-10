package br.uff.faleniteroi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import br.uff.faleniteroi.entity.Intervention;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InterventionActivity extends Activity implements OnMarkerClickListener {

	private GoogleMap googleMap;
	private double latitude = -22.8975554;
	private double longitude = -43.0995201;
	private Date currentDate = new Date();
	private AlertDialog detailDialog;
	private ArrayList<Intervention> interventions;
	private View detailView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intervention);
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		googleMap = mapFragment.getMap();	
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 11));
        googleMap.setOnMarkerClickListener(this);
        
        detailView = getLayoutInflater().inflate(R.layout.view_intervention_detail, null);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Detalhes da intervenção");
		builder.setView(detailView);
        builder.setPositiveButton(R.string.ok, null);
        detailDialog = builder.create();

		currentDate = new Date();
		interventions = new ArrayList<Intervention>();
		
		interventions.add(new Intervention("Obra", "Rua Lopes trovão, 143", currentDate, -22.904889, -43.109997));
		interventions.add(new Intervention("Evento", "Rua Marechal Deodoro, 187", new GregorianCalendar(2014, 5, 28).getTime(), -22.889678, -43.119858));
		interventions.add(new Intervention("Obra", "Rua Doutor Armando Lopes, 26", new GregorianCalendar(2014, 6, 1).getTime(), -22.928556, -43.095411)); 
		interventions.add(new Intervention("Obra", "Rua Alberto Lemos, 2", new GregorianCalendar(2014, 6, 13).getTime(), -22.909472, -43.067536)); 
		
		Iterator<Intervention> i = interventions.iterator();
		
		while (i.hasNext()) {
			
			Intervention intervention = i.next();
			
			MarkerOptions marker = new MarkerOptions().position(new LatLng(intervention.getLatitude(), intervention.getLongitude())).title(intervention.getAddress());

			if (intervention.getDate().compareTo(currentDate) <= 0) 
			{
				marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_red));
			} 
			else
			{
				marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_blue));
			}

			googleMap.addMarker(marker);
		}
	}
	
	public boolean onMarkerClick(final Marker marker) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Iterator<Intervention> i = interventions.iterator();
		
		while (i.hasNext()) {
			Intervention intervention = i.next();

			if (intervention.comparePosition(marker.getPosition().latitude, marker.getPosition().longitude)) {

        		((TextView) detailView.findViewById(R.id.tvAddress)).setText(intervention.getAddress());
        		((TextView) detailView.findViewById(R.id.tvDate)).setText(dateFormat.format(intervention.getDate()));
        		((TextView) detailView.findViewById(R.id.tvDescription)).setText(intervention.getDesription());
        		
        		if (intervention.getDate().compareTo(currentDate) == 0) 
        		{
        			((TextView) detailView.findViewById(R.id.tvDate)).setText("Hoje");
        		}
        		
                detailDialog.show();
				
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
