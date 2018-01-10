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
    	
    	itens.add(new Service(1, R.drawable.ic_iluminacao, "Iluminação", "Relatar problemas com a iluminação pública, como luzes apagadas"));
    	itens.add(new Service(2, R.drawable.ic_sinalizacao, "Sinalização de trânsito", "Relatar problemas em semáforos, placas e faixas"));
    	itens.add(new Service(3, R.drawable.ic_buracos, "Buracos na via", "Relatar buracos e irregularidades em via pública"));
    	itens.add(new Service(4, R.drawable.ic_calcadas, "Calçada irregular", "Relatar irregularidades e obstáculos em calçadas"));
		itens.add(new Service(5, R.drawable.ic_arborizacao, "Arborização", "Comunicar necessidade de poda de árvores em locais públicos"));
		itens.add(new Service(6, R.drawable.ic_limpeza, "Limpeza Urbana", "Comunicar necessidade de limpeza em locais públicos"));
		itens.add(new Service(7, R.drawable.ic_estacionamento, "Estacionamento irregular", "Relatar veículos estacionados em lugares proíbidos"));
		itens.add(new Service(8, R.drawable.ic_mobiliario, "Mobiliário Urbano", "Reladar danos em mobiliário público, como pontos de ônibus danificados"));
		itens.add(new Service(9, R.drawable.ic_outros, "Outros serviços", ""));
		
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
