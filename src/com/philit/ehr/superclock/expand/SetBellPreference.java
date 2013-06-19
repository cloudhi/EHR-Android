package com.philit.ehr.superclock.expand;

import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;

import com.philit.ehr.R;
import com.philit.ehr.superclock.Alarm;
import com.philit.ehr.superclock.Alarms;

public class SetBellPreference extends ListPreference{
	private Uri mAlert;
	private PreferenceActivity preferenceActivity;
	private int mId;
	private int ringType=1;//修改，默认为默认提示音，0代表静音
	private Context context;//修改
	
	public SetBellPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;//修改
		this.preferenceActivity=(PreferenceActivity)context;
        String[] values = context.getResources().getStringArray(R.array.choose_bell);
        mId=preferenceActivity.getIntent().getIntExtra(Alarms.ALARM_ID, -1);
        setEntries(values);
        setEntryValues(values);
        setSummary(R.string.notification_default_summary);//初始值
	}
	
	
	@Override
    protected void onPrepareDialogBuilder(Builder builder) {
        CharSequence[] entries = getEntries();
        
        builder.setSingleChoiceItems(entries, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0:
						ringType=0;
						break;
					case 1:
						ringType=1;
						break;
					case 2:
						Intent intent2 = new Intent(preferenceActivity, ChooseBellActivity.class);
						intent2.putExtra(Alarms.ALARM_ID, mId);
						intent2.putExtra("TYPE", 2);
						preferenceActivity.startActivity(intent2);
						preferenceActivity.finish();
						break;
					default:
						break;
				}
			}
		});
    }
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			Alarm alarm=com.philit.ehr.superclock.Alarms.getAlarm(preferenceActivity.getContentResolver(),mId);
			if (ringType==0) {
				alarm.silent=true;
				alarm.alert=Uri.parse("silent");
				mAlert=alarm.alert;
				ContentValues values = com.philit.ehr.superclock.Alarms.createContentValues(alarm);
				ContentResolver resolver = preferenceActivity.getContentResolver();
				resolver.update(ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, alarm.id),values, null, null);
				
				setSummary(R.string.silent_alarm_summary);
			}else {
				alarm.silent=false;
				alarm.alert=RingtoneManager.getActualDefaultRingtoneUri(context, Notification.DEFAULT_SOUND);
				mAlert=alarm.alert;
				ContentValues values = com.philit.ehr.superclock.Alarms.createContentValues(alarm);
				ContentResolver resolver = preferenceActivity.getContentResolver();
				resolver.update(ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, alarm.id),values, null, null);
				
				setSummary(R.string.notification_default_summary);
			}
        }
	}
	
	public void setAlert(Uri alert) {
        mAlert = alert;
        if (alert != null) {
            final Ringtone r = RingtoneManager.getRingtone(getContext(), alert);
            if (r != null) {
                setSummary(r.getTitle(getContext()));
            }
        } else {
            setSummary(R.string.silent_alarm_summary);
        }
    }
	
	public Uri getAlert() {
        return mAlert;
    }
}
