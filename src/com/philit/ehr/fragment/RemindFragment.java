package com.philit.ehr.fragment;

import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.philit.ehr.R;
import com.philit.ehr.superclock.Alarm;
import com.philit.ehr.superclock.Alarms;
import com.philit.ehr.superclock.DigitalClock;
import com.philit.ehr.superclock.SetAlarm;
import com.philit.ehr.superclock.ToastMaster;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class RemindFragment extends Fragment implements OnItemClickListener{

	private int mPos = -1;
	private View mainView;
	private TextView showMenuTv;
	private ImageView right_angle;
	static final String PREFERENCES = "AlarmClock";

    /** This must be false for production.  If true, turns on logging,
        test code, etc. */
    public static final boolean DEBUG = false;

    private SharedPreferences mPrefs;
    private LayoutInflater mFactory;
    private ListView mAlarmsList;
    private Cursor mCursor;
    //新增
    private ProgressDialog myDialog = null;
    private ImageButton back;
    private int type;
	
    public RemindFragment(int type){
    	this.type = type;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.alarm_clock, null);
		showMenuTv = (TextView) mainView.findViewById(R.id.showMenuTv);
		right_angle = (ImageView) mainView.findViewById(R.id.right_angle);
		if (type == 1) {
			showMenuTv.setText(R.string.medication_reminder);			
			right_angle.setImageResource(R.drawable.medcine_right_angle);
		}else {
			showMenuTv.setText(R.string.flowup_reminder);		
			right_angle.setImageResource(R.drawable.fllowup_right_angle);
		}
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getActivity()).getSlidingMenu().showMenu();
			}
		});
		
		return mainView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
	
	private void updateIndicatorAndAlarm(boolean enabled, ImageView bar,
            Alarm alarm) {
        bar.setImageResource(enabled ? R.drawable.ic_indicator_on
                : R.drawable.ic_indicator_off);
        //修改
        Alarms.enableAlarm(getActivity(), alarm.id, enabled);
        if (enabled) {
            SetAlarm.popAlarmSetToast(getActivity(), alarm.hour, alarm.minutes, alarm.daysOfWeek);
        }
    }

    private class AlarmTimeAdapter extends CursorAdapter {
    	//新增
    	private Context context;
        public AlarmTimeAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View ret = mFactory.inflate(R.layout.alarm_time, parent, false);
            DigitalClock digitalClock = (DigitalClock) ret.findViewById(R.id.digitalClock);
            digitalClock.setLive(false);
            return ret;
        }

        public void bindView(View view, Context context, Cursor cursor) {
            final Alarm alarm = new Alarm(cursor);
            
            View indicator = view.findViewById(R.id.indicator);

            // Set the initial resource for the bar image.
            final ImageView barOnOff = (ImageView) indicator.findViewById(R.id.bar_onoff);
            barOnOff.setImageResource(alarm.enabled ? R.drawable.ic_indicator_on : R.drawable.ic_indicator_off);
            
            // Set the initial state of the clock "checkbox"
            final CheckBox clockOnOff =(CheckBox) indicator.findViewById(R.id.clock_onoff);
            clockOnOff.setBackgroundResource(alarm.enabled?R.drawable.ic_clock_alarm_on:R.drawable.ic_clock_alarm_off);
            clockOnOff.setChecked(alarm.enabled);

            //getActivity().context=context;
            // Clicking outside the "checkbox" should also change the state.
            indicator.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        clockOnOff.toggle();
                        updateIndicatorAndAlarm(clockOnOff.isChecked(),barOnOff, alarm);
                    }
            });

            DigitalClock digitalClock =(DigitalClock) view.findViewById(R.id.digitalClock);

            // set the alarm text
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, alarm.hour);
            c.set(Calendar.MINUTE, alarm.minutes);
            digitalClock.updateTime(c);
            digitalClock.setTypeface(Typeface.DEFAULT);

            // Set the repeat text or leave it blank if it does not repeat.
            TextView daysOfWeekView = (TextView) digitalClock.findViewById(R.id.daysOfWeek);
            final String daysOfWeekStr = alarm.daysOfWeek.toString((Context)(getActivity()), false);
            if (daysOfWeekStr != null && daysOfWeekStr.length() != 0) {
                daysOfWeekView.setText(daysOfWeekStr);
                daysOfWeekView.setVisibility(View.VISIBLE);
            } else {
                daysOfWeekView.setVisibility(View.GONE);
            }

            // Display the label
            TextView labelView = (TextView) view.findViewById(R.id.label);
            if (alarm.label != null && alarm.label.length() != 0) {
                labelView.setText(alarm.label);
                labelView.setVisibility(View.VISIBLE);
            } else {
                labelView.setVisibility(View.GONE);
            }
        }
    };

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
    	//type = 1;
        
        mFactory = LayoutInflater.from(getActivity());
        mPrefs = getActivity().getSharedPreferences(PREFERENCES, 0);
        mCursor = Alarms.getAlarmsCursor(getActivity().getContentResolver(),"type=?", new String[]{type+""});
        
        mAlarmsList = (ListView)mainView. findViewById(R.id.alarms_list);
        AlarmTimeAdapter adapter = new AlarmTimeAdapter(getActivity(), mCursor);
        mAlarmsList.setAdapter(adapter);
        mAlarmsList.setVerticalScrollBarEnabled(true);
        mAlarmsList.setOnItemClickListener(this);
        mAlarmsList.setOnCreateContextMenuListener(getActivity());
        /*back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new BackBtnClickListener(getActivity(),R.anim.push_right_in,R.anim.push_right_out));*/
        
        View addAlarm = mainView.findViewById(R.id.add_alarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addNewAlarm();
                }
            });
        
        addAlarm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    v.setSelected(hasFocus);
                }
        });
		super.onActivityCreated(savedInstanceState);
	}

    private void addNewAlarm() {
    	Intent intent = new Intent(getActivity(), SetAlarm.class);
    	intent.putExtra(Alarms.ALARM_TYPE, type);
        startActivity(intent);
    }


    @Override
	public void onDestroyView() {
		super.onDestroyView();
		ToastMaster.cancelToast();
        mCursor.deactivate();	
    }

	public void onItemClick(AdapterView parent, View v, int pos, long id) {
        Intent intent = new Intent(getActivity(), SetAlarm.class);
        intent.putExtra(Alarms.ALARM_ID, (int) id);
        intent.putExtra(Alarms.ALARM_TYPE, type);
        startActivity(intent);
    }
}
