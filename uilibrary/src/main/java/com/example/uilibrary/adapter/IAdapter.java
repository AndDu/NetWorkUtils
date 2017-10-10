package com.example.uilibrary.adapter;

import android.view.View;

import java.util.List;

public interface IAdapter<M> {
	
	void remove(M t);
	
	void remove(int index);
	
	void add(M t);
	
	void addAllLoad(List<M> t);
	
	void notifyDataChanged();
	
	void addAll(List<M> t);
	
	void clearList();
	
	List<M> getList();
	
	Object getAdapter();
	
	void setOnChildViewClickListener(ChildViewClickListener childViewClickListener);
	
	interface ChildViewClickListener{
	
		void setOnChildViewClickListener(View v, int position);
		
	}
	
	

}
