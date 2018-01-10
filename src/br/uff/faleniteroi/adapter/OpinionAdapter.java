package br.uff.faleniteroi.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.uff.faleniteroi.R;
import br.uff.faleniteroi.entity.Opinion;

public class OpinionAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
    private ArrayList<Opinion> itens;
    
    public OpinionAdapter(Context context, ArrayList<Opinion> itens)
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

		Opinion item = itens.get(pos);

        view = mInflater.inflate(R.layout.listview_item_opinion, null);

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        
        TextView tvOpinionStatus = (TextView) view.findViewById(R.id.tvOpinionStatus);
        tvOpinionStatus.setText(item.isFinished() ? "Resposta recebida" : "Aguardando resposta");
        tvOpinionStatus.setTextColor(item.isFinished() ? Color.parseColor("#289F00") : Color.parseColor("#E17600"));

        ((TextView) view.findViewById(R.id.tvOpinionService)).setText(item.getService());
        ((TextView) view.findViewById(R.id.tvOpinionDay)).setText(dayFormat.format(item.getDate()));
        ((TextView) view.findViewById(R.id.tvOpinionMonth)).setText(monthFormat.format(item.getDate()));
        //((TextView) view.findViewById(R.id.tvOpinionStatus)).setText(item.isFinished() ? "Resposta recebida" : "Aguardando resposta");
        ((TextView) view.findViewById(R.id.tvOpinionProtocol)).setText("Protocolo: " + item.getProtocol());
        ((TextView) view.findViewById(R.id.tvOpinionType)).setText(item.getType() == 0 ? "Crítica" : "Sugestão");
        
        return view;
	}

}
