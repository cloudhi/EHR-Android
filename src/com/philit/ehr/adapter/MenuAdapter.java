package com.philit.ehr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.MenuData;

public class MenuAdapter extends ArrayAdapter<MenuData> {
	List<MenuData> datas;
	private LayoutInflater layoutInflater;
	private int currentPosition;
	
	public MenuAdapter(Context context, int layoutResourceId, List<MenuData> datas) {
		super(context, layoutResourceId, datas);
		this.datas = datas;
		this.currentPosition = 0;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public MenuAdapter(Context context, int layoutResourceId, List<MenuData> datas, int currentPosition) {
		super(context, layoutResourceId, datas);
		this.datas = datas;
		this.currentPosition = currentPosition;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return datas.size();
	}
	/**
	* 获取某一位置的数据
	*/
	@Override
	public MenuData getItem(int position) {
		return datas.get(position);
	}
	
	/**
	* 获取唯一标识
	*/
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	* android绘制每一列的时候，都会调用这个方法
	*/
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MenuDataTag menuDataTag = null;
		if (convertView == null) {
			menuDataTag = new MenuDataTag();
			// 获取组件布局
			convertView = layoutInflater.inflate(R.layout.item_menu_view, null);
			menuDataTag.arrow_Ib = (ImageButton) convertView.findViewById(R.id.arrow);
			menuDataTag.titleTv = (TextView) convertView.findViewById(R.id.title);
			// 这里要注意，是使用的tag来存储数据的。
			convertView.setTag(menuDataTag);
		} else {
			menuDataTag = (MenuDataTag) convertView.getTag();
		}
		// 绑定数据、事件触发
		try {
			MenuData menuData = datas.get(position);
			convertView.setBackgroundColor(menuData.getColor());
			if (currentPosition == position) {
				menuDataTag.arrow_Ib.setImageResource(R.drawable.arrow_right);
			}
			menuDataTag.titleTv.setText(menuData.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	public int getCurrentPeriodicalId() {
		return currentPosition;
	}

	public void setCurrentPeriodicalId(int currentPeriodicalId) {
		this.currentPosition = currentPeriodicalId;
	}

	class MenuDataTag{
		private ImageButton arrow_Ib;
		private TextView titleTv;
	}
}
