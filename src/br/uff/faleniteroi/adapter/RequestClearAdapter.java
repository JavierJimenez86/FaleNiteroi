package br.uff.faleniteroi.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.uff.faleniteroi.R;
import br.uff.faleniteroi.entity.Request;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RequestClearAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
    private ArrayList<Request> itens;
    
    public RequestClearAdapter(Context context, ArrayList<Request> itens)
    {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itens.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return itens.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {

		Request item = itens.get(pos);

        view = mInflater.inflate(R.layout.listview_item_request_clear, null);

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        
        ((TextView) view.findViewById(R.id.tvRequestServiceName)).setText(item.getService());
        ((TextView) view.findViewById(R.id.tvRequestAddress)).setText(item.getAddress());
        ((TextView) view.findViewById(R.id.tvRequestDay)).setText(dayFormat.format(item.getDate()));
        ((TextView) view.findViewById(R.id.tvRequestMonth)).setText(monthFormat.format(item.getDate()));
        ((TextView) view.findViewById(R.id.tvRequestProtocol)).setText("Protocolo: " + item.getProtocol());

        return view;
	}

}
