package br.uff.faleniteroi.adapter;

import java.util.ArrayList;

import br.uff.faleniteroi.R;
import br.uff.faleniteroi.entity.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceSpinnerAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
    private ArrayList<Service> itens;
    
    public ServiceSpinnerAdapter(Context context, ArrayList<Service> itens)
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
	//Sem implementação
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {

        Service item = itens.get(pos);

        view = mInflater.inflate(R.layout.spinner_item_service, null);

        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        
        if (item.getIcon() == 0) {
        	ivIcon.setVisibility(View.GONE);
        } else {
        	ivIcon.setImageResource(item.getIcon());
        }
        
        ((TextView) view.findViewById(R.id.tvName)).setText(item.getName());
        //((TextView) view.findViewById(R.id.tvDescription)).setText(item.getDescription());
        //((ImageView) view.findViewById(R.id.ivIcon)).setImageResource(item.getIcon());
 
        return view;
	}

}
