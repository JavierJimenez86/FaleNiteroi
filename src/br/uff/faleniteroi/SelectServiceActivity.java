package br.uff.faleniteroi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.uff.faleniteroi.R;
import br.uff.faleniteroi.adapter.ServiceAdapter;
import br.uff.faleniteroi.entity.Service;

public class SelectServiceActivity extends Activity implements OnItemClickListener {

    private ListView listView;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> itens;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_service);

    	itens = new ArrayList<Service>();
    	
    	itens.add(new Service(1, R.drawable.ic_iluminacao, "Ilumina��o", "Relatar problemas com a ilumina��o p�blica, como luzes apagadas"));
    	itens.add(new Service(2, R.drawable.ic_sinalizacao, "Sinaliza��o de tr�nsito", "Relatar problemas em sem�foros, placas e faixas"));
    	itens.add(new Service(3, R.drawable.ic_buracos, "Buracos na via", "Relatar buracos e irregularidades em via p�blica"));
    	itens.add(new Service(4, R.drawable.ic_calcadas, "Cal�ada irregular", "Relatar irregularidades e obst�culos em cal�adas"));
		itens.add(new Service(5, R.drawable.ic_arborizacao, "Arboriza��o", "Comunicar necessidade de poda de �rvores em locais p�blicos"));
		itens.add(new Service(6, R.drawable.ic_limpeza, "Limpeza Urbana", "Comunicar necessidade de limpeza em locais p�blicos"));
		itens.add(new Service(7, R.drawable.ic_estacionamento, "Estacionamento irregular", "Relatar ve�culos estacionados em lugares pro�bidos"));
		itens.add(new Service(8, R.drawable.ic_mobiliario, "Mobili�rio Urbano", "Reladar danos em mobili�rio p�blico, como pontos de �nibus danificados"));
		itens.add(new Service(9, R.drawable.ic_outros, "Outros servi�os", ""));
		
        listView = (ListView) findViewById(R.id.lvServices);
        listView.setOnItemClickListener(this);
        
        createListViewServices();
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
	
    private void createListViewServices() {
    	serviceAdapter = new ServiceAdapter(this, itens);
        listView.setAdapter(serviceAdapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {

		Service service = (Service) serviceAdapter.getItem(pos);
		
		Intent intent = new Intent(this, ServiceFormActivity.class);
		intent.putExtra("Service", service);  
		startActivity(intent);
	}
}
