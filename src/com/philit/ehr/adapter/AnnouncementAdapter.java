package com.philit.ehr.adapter;

import java.util.Date;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.AnnouncementData;

public class AnnouncementAdapter extends ArrayAdapter<AnnouncementData> {
	List<AnnouncementData> datas;
	private LayoutInflater layoutInflater;
	
	public AnnouncementAdapter(Context context, int layoutResourceId, List<AnnouncementData> datas) {
		super(context, layoutResourceId, datas);
		this.datas = datas;
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
	public AnnouncementData getItem(int position) {
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
		
		AnnouncementTag commentTag = null;
		if (convertView == null) {
			commentTag = new AnnouncementTag();
			// 获取组件布局
			convertView = layoutInflater.inflate(R.layout.item_announcement, null);
			commentTag.back_pointIv = (ImageView) convertView.findViewById(R.id.back_point);
			commentTag.month_dayTv = (TextView) convertView.findViewById(R.id.month_dayTv);
			commentTag.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
			// 这里要注意，是使用的tag来存储数据的。
			convertView.setTag(commentTag);
		} else {
			commentTag = (AnnouncementTag) convertView.getTag();
		}
		// 绑定数据、事件触发
		try {
			AnnouncementData announcementData = datas.get(position);
			Date date = new Date(announcementData.getA_DateTime());
			int year = date.getYear() + 1900;
			int month = date.getMonth() + 1;
			int day = date.getDate();
			commentTag.month_dayTv.setText(month + "/" +day);
			commentTag.titleTv.setText(announcementData.getA_Title());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class AnnouncementTag{
		private ImageView back_pointIv;
		private TextView month_dayTv, titleTv;
	}
}
