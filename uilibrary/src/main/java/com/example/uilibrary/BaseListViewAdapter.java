package com.example.uilibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListViewAdapter<M, V extends BaseHolder<M>> extends
		android.widget.BaseAdapter implements IAdapter<M> {

	protected List<M> list = new ArrayList<M>();
	protected Context context;
	private LayoutInflater from;
	protected ChildViewClickListener childViewClickListener;

	public BaseListViewAdapter(Context context) {
		super();
		this.context = context;
		from = LayoutInflater.from(context);
	}

	public void add(M mode) {
		list.add(mode);
		notifyDataSetChanged();
	}

	public void remove(M mode) {
		list.remove(mode);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		list.remove(index);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public void addAllLoad(List<M> t) {
		// TODO Auto-generated method stub
		if (t != null) {
			list.addAll(t);
			notifyDataSetChanged();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		// TODO Auto-generated method stub

		V mHolder = null;

		if (con == null) {
			con = from.inflate(getLayout(), arg2, false);
			mHolder = getViewHolder(con);
			con.setTag(mHolder);
		} else {
			mHolder = (V) con.getTag();
		}

		mHolder.setPosition(position);
		mHolder.initData(list.get(position), position, con);

		return con;
	}

	public abstract V getViewHolder(View contentView);

	@Override
	public void notifyDataChanged() {
		notifyDataSetChanged();
	}

	@Override
	public void addAll(List<M> t) {
		// TODO Auto-generated method stub
		if (t != null) {
			list.clear();
			list.addAll(t);
			notifyDataSetChanged();
		}
	}

	@Override
	public void clearList() {
		// TODO Auto-generated method stub
		list.clear();
		notifyDataSetChanged();

	}

	@Override
	public List<M> getList() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public BaseListViewAdapter<M, ?> getAdapter() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setOnChildViewClickListener(
			ChildViewClickListener childViewClickListener) {
		// TODO Auto-generated method stub
		this.childViewClickListener = childViewClickListener;
	}

	public abstract int getLayout();

}
