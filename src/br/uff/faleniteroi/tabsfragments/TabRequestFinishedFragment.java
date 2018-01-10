package br.uff.faleniteroi.tabsfragments;

import java.util.ArrayList;

import br.uff.faleniteroi.R;
import br.uff.faleniteroi.RequestDetailActivity;
import br.uff.faleniteroi.adapter.RequestClearAdapter;
import br.uff.faleniteroi.entity.Request;
import br.uff.faleniteroi.sqlite.RequestDAO;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TabRequestFinishedFragment extends Fragment implements OnItemClickListener {
	
    private ListView listView;
    private RequestClearAdapter requestAdapter;
    private ArrayList<Request> itens;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.tab_fragment_request_latest, container, false);
    	
    	RequestDAO requestDAO = new RequestDAO(view.getContext());
    	itens = requestDAO.listFinished();
    	
    	TextView tvRequestInfo = (TextView) view.findViewById(R.id.tvRequestInfo);
    	tvRequestInfo.setVisibility(View.GONE);
    	
    	if (itens.size() == 0) {
    		tvRequestInfo.setVisibility(View.VISIBLE);
    		tvRequestInfo.setText("Nenhuma solicitação finalizada");
    	}

        listView = (ListView) view.findViewById(R.id.lvRequests);
        listView.setOnItemClickListener(this);

        requestAdapter = new RequestClearAdapter(view.getContext(), itens);
        listView.setAdapter(requestAdapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
    	
    	return view;
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		Request request = (Request) requestAdapter.getItem(pos);
		
		Intent intent = new Intent(view.getContext(), RequestDetailActivity.class);
		intent.putExtra("Request", request);  
		startActivity(intent);
	}
}
