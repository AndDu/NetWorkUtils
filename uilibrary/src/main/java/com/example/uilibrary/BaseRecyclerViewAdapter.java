package com.example.uilibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yellow on 2017/9/14.
 */

public abstract class BaseRecyclerViewAdapter<M, H extends BaseRecyclerViewHolder<M>> extends RecyclerView.Adapter<H> implements IAdapter<M> {


    protected List<M> list = new ArrayList<>(0);
    protected final LayoutInflater from;
    protected Context context;

    protected ChildViewClickListener childViewClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(from.inflate(getLayut(),parent,false));
    }

    protected abstract H getHolder(View itemView);

    protected abstract int getLayut();

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setPosition(position);
        holder.initData(list.get(position), position, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void remove(M t) {
        if (t != null) {
            list.remove(t);
            notifyDataChanged();
        }
    }

    @Override
    public void remove(int index) {
        if (index > -1 && index < list.size()) {
            list.remove(index);
             notifyDataChanged();
        }
    }

    @Override
    public void add(M t) {
        if (t!=null){
            list.add(t);
        }

    }

    @Override
    public void addAllLoad(List<M> t) {
        if (t != null) {
            list.addAll(t);
            notifyDataChanged();
        }

    }

    @Override
    public void notifyDataChanged() {
        this.notifyDataSetChanged();
    }

    @Override
    public void addAll(List<M> t) {
        if (t != null) {
            list.clear();
            list.addAll(t);
            notifyDataChanged();
        }
    }

    @Override
    public void clearList() {
        list.clear();
        notifyDataChanged();
    }

    @Override
    public List<M> getList() {
        return list;
    }

    @Override
    public BaseRecyclerViewAdapter<M, ?> getAdapter() {
        return this;
    }

    @Override
    public void setOnChildViewClickListener(ChildViewClickListener childViewClickListener) {
        this.childViewClickListener = childViewClickListener;
    }

}
