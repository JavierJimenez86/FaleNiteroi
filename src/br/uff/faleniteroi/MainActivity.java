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
import br.uff.faleniteroi.adapter.MainNavAdapter;
import br.uff.faleniteroi.entity.MainNav;

public class MainActivity extends Activity implements OnItemClickListener {

    private ListView listView;
    private MainNavAdapter navAdapter;
    private ArrayList<MainNav> itens;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    	itens = new ArrayList<MainNav>();

    	itens.add(new MainNav(1, R.drawable.ic_nav_nova_solicitacao, "Nova solicitação", "Relate problemas, irregularidades ou necessidades de serviços públicos"));
    	itens.add(new MainNav(2, R.drawable.ic_nav_minhas_solicitacoes, "Minhas solicitações", "Acompanhe solicitações que você realizou"));
    	itens.add(new MainNav(3, R.drawable.ic_nav_mapa_solicitacoes, "Mapa de solicitações", "Visualize solicitações recentes no mapa"));
    	itens.add(new MainNav(4, R.drawable.ic_nav_intervencoes, "Intervenções", "Notificações sobre interdições causadas por obras e eventos"));
    	itens.add(new MainNav(5, R.drawable.ic_nav_comentarios, "Opine", "Envie sua opnião sobre nossos serviços"));

        listView = (ListView) findViewById(R.id.lvNav);
        listView.setOnItemClickListener(this);
        
        createListViewNav();
    }
    
    @Override
    public void onResume() {
		navAdapter.setmSelectedItem(-1);
		navAdapter.notifyDataSetChanged();
		super.onResume();
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
    
    private void createListViewNav() {
    	navAdapter = new MainNavAdapter(this, itens);
        listView.setAdapter(navAdapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {

		navAdapter.setmSelectedItem(pos);
		navAdapter.notifyDataSetChanged();
		
		MainNav nav = (MainNav) navAdapter.getItem(pos);
		
		switch(nav.getId()) {
		case 1:
			Intent intent = new Intent(this, SelectServiceActivity.class);
			startActivity(intent);
			break;
		case 2: 
			intent = new Intent(this, MyRequestsActivity.class);
			startActivity(intent);
			break;
		case 3: 
			intent = new Intent(this, RequestMapActivity.class);
			startActivity(intent);
			break;
		case 4: 
			intent = new Intent(this, InterventionActivity.class);
			startActivity(intent);
			break;
		case 5: 
			intent = new Intent(this, MyOpinionsActivity.class);
			startActivity(intent);
			break;
		}
	}
}
