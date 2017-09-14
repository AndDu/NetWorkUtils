package com.example.uilibrary;

import android.view.View;

public interface IBaseHolder<M> {
	
	void setPosition(int position);
	
	void initView(View contentView);
	
	void initData(M mode, int position, View contentView);

}
