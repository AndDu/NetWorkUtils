package com.example.uilibrary;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yellow on 2017/9/14.
 */

public abstract class BaseRecyclerViewHolder<M> extends RecyclerView.ViewHolder implements IBaseHolder<M> {

    protected int position;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        if (itemView != null) {
            initView(itemView);
        }
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

}
