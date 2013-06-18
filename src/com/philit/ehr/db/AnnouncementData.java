package com.philit.ehr.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "AR_Announcement")
public class AnnouncementData implements Serializable {

	private static final long serialVersionUID = 8356064483073516356L;

	public static final String COLUMN_NAME_ANNOUNCEMENTID = "AnnouncementID";
	public static final String COLUMN_NAME_A_TITLE= "A_Title";
	public static final String COLUMN_NAME_A_CONNECT = "A_Content";
	public static final String COLUMN_NAME_A_DATETIME= "A_DateTime";
	public static final String COLUMN_NAME_A_RESPONSIBILITYUSERID = "A_ResponsibilityUserID";
	public static final String COLUMN_NAME_A_TYPE = "A_Type";
	
	@DatabaseField(columnName = COLUMN_NAME_ANNOUNCEMENTID, id = true)
	private int AnnouncementID;

	@DatabaseField(columnName = COLUMN_NAME_A_TITLE)
	private String A_Title;

	@DatabaseField(columnName = COLUMN_NAME_A_CONNECT)
	private String A_Content;
	
	@DatabaseField(columnName = COLUMN_NAME_A_DATETIME)
	private long A_DateTime;
	
	@DatabaseField(columnName = COLUMN_NAME_A_RESPONSIBILITYUSERID)
	private int A_ResponsibilityUserID;
	
	@DatabaseField(columnName = COLUMN_NAME_A_TYPE)
	private int A_Type;
	
	public AnnouncementData() {

	}

	public AnnouncementData(int announcementID, String a_Title,
			String a_Content, long a_DateTime, int a_ResponsibilityUserID,
			int a_Type) {
		super();
		AnnouncementID = announcementID;
		A_Title = a_Title;
		A_Content = a_Content;
		A_DateTime = a_DateTime;
		A_ResponsibilityUserID = a_ResponsibilityUserID;
		A_Type = a_Type;
	}

	@Override
	public String toString() {
		return "AnnouncementData [AnnouncementID=" + AnnouncementID
				+ ", A_Title=" + A_Title + ", A_Content=" + A_Content
				+ ", A_DateTime=" + A_DateTime + ", A_ResponsibilityUserID="
				+ A_ResponsibilityUserID + ", A_Type=" + A_Type + "]";
	}

	public int getAnnouncementID() {
		return AnnouncementID;
	}

	public void setAnnouncementID(int announcementID) {
		AnnouncementID = announcementID;
	}

	public String getA_Title() {
		return A_Title;
	}

	public void setA_Title(String a_Title) {
		A_Title = a_Title;
	}

	public String getA_Content() {
		return A_Content;
	}

	public void setA_Content(String a_Content) {
		A_Content = a_Content;
	}

	public long getA_DateTime() {
		return A_DateTime;
	}

	public void setA_DateTime(long a_DateTime) {
		A_DateTime = a_DateTime;
	}

	public int getA_ResponsibilityUserID() {
		return A_ResponsibilityUserID;
	}

	public void setA_ResponsibilityUserID(int a_ResponsibilityUserID) {
		A_ResponsibilityUserID = a_ResponsibilityUserID;
	}

	public int getA_Type() {
		return A_Type;
	}

	public void setA_Type(int a_Type) {
		A_Type = a_Type;
	}
}
