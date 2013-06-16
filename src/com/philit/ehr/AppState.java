package com.philit.ehr;


import com.philit.ehr.define.Define;

import android.content.SharedPreferences;

public class AppState {

    public int currentUserId;
    public final String CURRENT_USER_ID = "current_user_id";
	public int currentPublishId;
	public final String CURRENT_PUBLISHID = "current_publish_id";
	public boolean addUserGuide;
	public final String ADD_USER_GUIDE = "add_user_guide";
	public long checkUpdateTime;
	public final String CHECK_UPDATE_TIME = "check_update_time";
	public String contactEmail;
	public final String CONTACT_EMAIL = "contact_email";
	
	public void persistLoad(SharedPreferences prefers)
	{
	    currentUserId = prefers.getInt(CURRENT_USER_ID, Define.UNDEFINE);
	    currentPublishId= prefers.getInt(CURRENT_PUBLISHID, -1);
	    addUserGuide = prefers.getBoolean(ADD_USER_GUIDE, false);
	    checkUpdateTime = prefers.getLong(CHECK_UPDATE_TIME, Define.UNDEFINE);
	    contactEmail = prefers.getString(CONTACT_EMAIL, null);
	}
	
	public void persistSave(SharedPreferences prefers)
	{
		SharedPreferences.Editor editor = prefers.edit();
		editor.putInt(CURRENT_USER_ID, currentUserId);
		editor.putInt(CURRENT_PUBLISHID, currentPublishId);
		editor.putBoolean(ADD_USER_GUIDE, addUserGuide);
		editor.putLong(CHECK_UPDATE_TIME, checkUpdateTime);
		editor.putString(CONTACT_EMAIL, contactEmail);
		editor.commit();
	}
	
	public void clear(SharedPreferences prefers) {
	    SharedPreferences.Editor editor = prefers.edit();
	    editor.remove(CURRENT_USER_ID);
	    editor.remove(CURRENT_PUBLISHID);
	    editor.remove(ADD_USER_GUIDE);
	    editor.remove(CHECK_UPDATE_TIME);
	    editor.remove(CONTACT_EMAIL);
        editor.commit();
	}
}
