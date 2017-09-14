package com.example.uilibrary;

import android.view.View;


public abstract class BaseHolder<M>  implements IBaseHolder<M>{
	
	
	protected int position;
	
	public BaseHolder(View contentView){
		if(contentView!=null){
			initView(contentView);
		}
	}
	
	@Override
	public void setPosition(int position) {
		this.position=position;
	}
	
	
	
}
