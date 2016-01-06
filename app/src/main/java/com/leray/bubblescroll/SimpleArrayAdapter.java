package com.leray.bubblescroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SimpleArrayAdapter extends RecyclerView.Adapter<SimpleArrayAdapter.ViewHolder> implements OnClickListener {

	private Context context;
	private int resource;
	private int textViewResourceId;
	private List list;
	private OnItemClickListener mListener;


	public SimpleArrayAdapter(Context context, int resource,
							  int textViewResourceId, List list) {
		super();
		this.context = context;
		this.resource = resource;
		this.textViewResourceId = textViewResourceId;
		this.list = list;
	}

    public SimpleArrayAdapter(Context context, List list) {
        this(context, android.R.layout.activity_list_item, android.R.id.text1, list);
    }
	
	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		Object item = list.get(position);
		holder.tv.setText(item.toString());
		holder.itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemClick(v, position);
				}
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		View view = LayoutInflater.from(context).inflate(resource, parent, false);
		
		return new ViewHolder(view);
	}

	final class ViewHolder extends RecyclerView.ViewHolder {
		TextView tv;
		
		public ViewHolder(View view) {
			super(view);
			tv = (TextView) view.findViewById(textViewResourceId);
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mListener = listener;
	}
	
	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	@Override
	public void onClick(View v) {
//		mListener.onItemClick(view, position);
	}

}
