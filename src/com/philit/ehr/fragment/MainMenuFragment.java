package com.philit.ehr.fragment;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainMenuFragment extends Fragment {
	private View mainView;
	private LinearLayout user_info;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.main_menu_frame, null);
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user_info = (LinearLayout) mainView.findViewById(R.id.user_info);
		user_info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity mainActivity = (MainActivity)getActivity();
				mainActivity.switchContent(new LoginFragment());
			}
		});
	}
}
