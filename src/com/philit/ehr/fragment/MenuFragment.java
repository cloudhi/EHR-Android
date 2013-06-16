package com.philit.ehr.fragment;

import java.util.ArrayList;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.adapter.MenuAdapter;
import com.philit.ehr.db.MenuData;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class MenuFragment extends ListFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<MenuData> datas = new ArrayList<MenuData>();
		datas.add(new MenuData(getResources().getColor(R.color.bg_myrecord), getString(R.string.myrecord)));
		datas.add(new MenuData(getResources().getColor(R.color.bg_medication_reminder), getString(R.string.medication_reminder)));
		datas.add(new MenuData(getResources().getColor(R.color.bg_flowup_reminder), getString(R.string.flowup_reminder)));
		datas.add(new MenuData(getResources().getColor(R.color.bg_health_knowledge), getString(R.string.health_knowledge)));
		datas.add(new MenuData(getResources().getColor(R.color.bg_view_announcement), getString(R.string.view_announcement)));
		datas.add(new MenuData(getResources().getColor(R.color.bg_view_health_education), getString(R.string.view_health_education)));
		
		MenuAdapter adapter = new MenuAdapter(getActivity(), 0, datas);
		
		setListAdapter(adapter);
		
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), arg2 + "", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
