package br.uff.faleniteroi.adapter;

import java.util.ArrayList;

import br.uff.faleniteroi.R;
import br.uff.faleniteroi.entity.MainNav;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainNavAdapter extends BaseAdapter {

	private int mSelectedItem = -1;
	private LayoutInflater mInflater;
    private ArrayList<MainNav> itens;
    
    public MainNavAdapter(Context context, ArrayList<MainNav> itens)
    {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }

	public int getmSelectedItem() {
		return mSelectedItem;
	}

	public void setmSelectedItem(int mSelectedItem) {
		this.mSelectedItem = mSelectedItem;
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

        MainNav item = itens.get(pos);

        view = mInflater.inflate(R.layout.listview_item_nav, null);
        view.setBackgroundColor(Color.parseColor("#DFF8FF"));
        
        ImageView icon = (ImageView) view.findViewById(R.id.ivIcon);
        
        icon.setBackgroundColor(Color.parseColor("#00BFFF"));
        icon.setImageResource(item.getIcon());

        ((TextView) view.findViewById(R.id.tvName)).setText(item.getName());
        ((TextView) view.findViewById(R.id.tvDescription)).setText(item.getDescription());
        
        if (pos == this.mSelectedItem)
        {
        	view.setBackgroundColor(Color.parseColor("#CCE1E8"));
        	icon.setBackgroundColor(Color.parseColor("#00455B"));
        }
        
        return view;
	}

}
