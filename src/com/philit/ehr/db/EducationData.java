package com.philit.ehr.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.philit.ehr.R.string;

@DatabaseTable(tableName = "education_Activity")
public class EducationData implements Serializable {

	private static final long serialVersionUID = 8356035483073516356L;

	public static final String COLUMN_NAME_ACTIVITYID = "ActivityID";
	public static final String COLUMN_NAME_A_DATETIME= "A_DateTime";
	public static final String COLUMN_NAME_A_LOCATION= "A_Location";
	public static final String COLUMN_NAME_A_FORM = "A_Form";
	public static final String COLUMN_NAME_A_OBJECT = "A_Object";
	public static final String COLUMN_NAME_A_CROWD = "A_Crowd";
	public static final String COLUMN_NAME_A_DURATION = "A_Duration";
	public static final String COLUMN_NAME_A_ORGANIZERS = "A_Organizers";
	public static final String COLUMN_NAME_A_PARTNERS = "A_Partners";
	public static final String COLUMN_NAME_A_MISSIONARY = "A_Missionary";
	public static final String COLUMN_NAME_A_NUMBER = "A_Number";
	public static final String COLUMN_NAME_A_THEME = "A_Theme";
	
	@DatabaseField(columnName = COLUMN_NAME_ACTIVITYID, id = true)
	private int ActivityID;
	
	@DatabaseField(columnName = COLUMN_NAME_A_DATETIME)
	private long A_DateTime;

	@DatabaseField(columnName = COLUMN_NAME_A_LOCATION)
	private String A_Location;

	@DatabaseField(columnName = COLUMN_NAME_A_FORM)
	private String A_Form;
	
	@DatabaseField(columnName = COLUMN_NAME_A_OBJECT)
	private String A_Object;
	
	@DatabaseField(columnName = COLUMN_NAME_A_CROWD)
	private String A_Crowd;
	
	@DatabaseField(columnName = COLUMN_NAME_A_DURATION)
	private int A_Duration;
	
	@DatabaseField(columnName = COLUMN_NAME_A_ORGANIZERS)
	private String A_Organizers;

	@DatabaseField(columnName = COLUMN_NAME_A_PARTNERS)
	private String A_Partners;
	
	@DatabaseField(columnName = COLUMN_NAME_A_MISSIONARY)
	private String A_Missionary;
	
	@DatabaseField(columnName = COLUMN_NAME_A_NUMBER)
	private int A_Number;
	
	@DatabaseField(columnName = COLUMN_NAME_A_THEME)
	private String A_Theme;
	
	public EducationData() {

	}

	public EducationData(int activityID, long a_DateTime, String a_Location,
			String a_Form, String a_Object, String a_Crowd, int a_Duration,
			String a_Organizers, String a_Partners, String a_Missionary,
			int a_Number, String a_Theme) {
		super();
		ActivityID = activityID;
		A_DateTime = a_DateTime;
		A_Location = a_Location;
		A_Form = a_Form;
		A_Object = a_Object;
		A_Crowd = a_Crowd;
		A_Duration = a_Duration;
		A_Organizers = a_Organizers;
		A_Partners = a_Partners;
		A_Missionary = a_Missionary;
		A_Number = a_Number;
		A_Theme = a_Theme;
	}

	@Override
	public String toString() {
		return "EducationData [ActivityID=" + ActivityID + ", A_DateTime="
				+ A_DateTime + ", A_Location=" + A_Location + ", A_Form="
				+ A_Form + ", A_Object=" + A_Object + ", A_Crowd=" + A_Crowd
				+ ", A_Duration=" + A_Duration + ", A_Organizers="
				+ A_Organizers + ", A_Partners=" + A_Partners
				+ ", A_Missionary=" + A_Missionary + ", A_Number=" + A_Number
				+ ", A_Theme=" + A_Theme + "]";
	}

	public int getActivityID() {
		return ActivityID;
	}

	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}

	public long getA_DateTime() {
		return A_DateTime;
	}

	public void setA_DateTime(long a_DateTime) {
		A_DateTime = a_DateTime;
	}

	public String getA_Location() {
		return A_Location;
	}

	public void setA_Location(String a_Location) {
		A_Location = a_Location;
	}

	public String getA_Form() {
		return A_Form;
	}

	public void setA_Form(String a_Form) {
		A_Form = a_Form;
	}

	public String getA_Object() {
		return A_Object;
	}

	public void setA_Object(String a_Object) {
		A_Object = a_Object;
	}

	public String getA_Crowd() {
		return A_Crowd;
	}

	public void setA_Crowd(String a_Crowd) {
		A_Crowd = a_Crowd;
	}

	public int getA_Duration() {
		return A_Duration;
	}

	public void setA_Duration(int a_Duration) {
		A_Duration = a_Duration;
	}

	public String getA_Organizers() {
		return A_Organizers;
	}

	public void setA_Organizers(String a_Organizers) {
		A_Organizers = a_Organizers;
	}

	public String getA_Partners() {
		return A_Partners;
	}

	public void setA_Partners(String a_Partners) {
		A_Partners = a_Partners;
	}

	public String getA_Missionary() {
		return A_Missionary;
	}

	public void setA_Missionary(String a_Missionary) {
		A_Missionary = a_Missionary;
	}

	public int getA_Number() {
		return A_Number;
	}

	public void setA_Number(int a_Number) {
		A_Number = a_Number;
	}

	public String getA_Theme() {
		return A_Theme;
	}

	public void setA_Theme(String a_Theme) {
		A_Theme = a_Theme;
	}
}
