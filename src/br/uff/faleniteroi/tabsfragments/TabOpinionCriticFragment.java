package br.uff.faleniteroi.tabsfragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.uff.faleniteroi.R;
import br.uff.faleniteroi.adapter.OpinionAdapter;
import br.uff.faleniteroi.entity.Opinion;
import br.uff.faleniteroi.sqlite.OpinionDAO;

public class TabOpinionCriticFragment extends Fragment implements OnItemClickListener {
	
    private ListView listView;
    private OpinionAdapter opinionAdapter;
    private ArrayList<Opinion> itens;
    
    private AlertDialog.Builder builder;
    private AlertDialog responseDialog;
    private View responseView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      
    	View view = inflater.inflate(R.layout.tab_fragment_opinion_critic, container, false);
    	
    	OpinionDAO opinionDAO = new OpinionDAO(view.getContext());
    	itens = opinionDAO.listCritics();
    	
    	TextView tvRequestInfo = (TextView) view.findViewById(R.id.tvOpinionInfo);
    	tvRequestInfo.setVisibility(View.GONE);
    	
    	if (itens.size() == 0) {
    		tvRequestInfo.setVisibility(View.VISIBLE);
    		tvRequestInfo.setText("Nenhuma crítica enviada");
    	}

        listView = (ListView) view.findViewById(R.id.lvOpinions);
        listView.setOnItemClickListener(this);

        opinionAdapter = new OpinionAdapter(view.getContext(), itens);
        listView.setAdapter(opinionAdapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
        
        responseView = inflater.inflate(R.layout.view_opinion_detail, null);
		builder = new AlertDialog.Builder(container.getContext());
		builder.setView(responseView);
        builder.setTitle("Detalhes da opinião");
        builder.setPositiveButton("Ok", null);
		responseDialog = builder.create();
    	
    	return view;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		Opinion opinion = (Opinion) opinionAdapter.getItem(pos);
		
		((TextView) responseView.findViewById(R.id.tvComment)).setText(opinion.getComment());
		((TextView) responseView.findViewById(R.id.tvResponse)).setText(opinion.isFinished() ? opinion.getResponse() : "Protocolo não finalizado");

		responseDialog.show();
	}
}
