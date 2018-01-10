package br.uff.faleniteroi.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.uff.faleniteroi.R;
import br.uff.faleniteroi.entity.Comment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
    private ArrayList<Comment> itens;
    
    public CommentAdapter(Context context, ArrayList<Comment> itens)
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

		Comment item = itens.get(pos);

        view = mInflater.inflate(R.layout.listview_item_comment, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM");

        ((TextView) view.findViewById(R.id.tvCommentDate)).setText(dateFormat.format(item.getDate()));
        ((TextView) view.findViewById(R.id.tvComment)).setText(item.getComment());
        ((TextView) view.findViewById(R.id.tvCommentUserName)).setText(item.getUserName());

        return view;
	}

}
