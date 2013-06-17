package com.philit.ehr.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "education_Document")
public class DocumentData implements Serializable {

	private static final long serialVersionUID = 8356064483073516356L;

	public static final String COLUMN_NAME_DOCUMENTID = "DocumentID";
	public static final String COLUMN_NAME_D_NAME= "D_Name";
	public static final String COLUMN_NAME_D_URL = "D_Url";
	public static final String COLUMN_NAME_D_USERD = "D_UserID";
	public static final String COLUMN_NAME_D_DATETIME= "D_DateTime";

	@DatabaseField(columnName = COLUMN_NAME_DOCUMENTID, id = true)
	private int DocumentID;

	@DatabaseField(columnName = COLUMN_NAME_D_NAME)
	private String D_Name;

	@DatabaseField(columnName = COLUMN_NAME_D_URL)
	private String D_Url;
	
	@DatabaseField(columnName = COLUMN_NAME_D_USERD)
	private int D_UserID;
	
	@DatabaseField(columnName = COLUMN_NAME_D_DATETIME)
	private String D_DateTime;
	
	public DocumentData() {

	}

	public DocumentData(int documentID, String d_Name, String d_Url,
			int d_UserID, String d_DateTime) {
		super();
		DocumentID = documentID;
		D_Name = d_Name;
		D_Url = d_Url;
		D_UserID = d_UserID;
		D_DateTime = d_DateTime;
	}

	public int getDocumentID() {
		return DocumentID;
	}

	public void setDocumentID(int documentID) {
		DocumentID = documentID;
	}

	public String getD_Name() {
		return D_Name;
	}

	public void setD_Name(String d_Name) {
		D_Name = d_Name;
	}

	public String getD_Url() {
		return D_Url;
	}

	public void setD_Url(String d_Url) {
		D_Url = d_Url;
	}

	public int getD_UserID() {
		return D_UserID;
	}

	public void setD_UserID(int d_UserID) {
		D_UserID = d_UserID;
	}

	public String getD_DateTime() {
		return D_DateTime;
	}

	public void setD_DateTime(String d_DateTime) {
		D_DateTime = d_DateTime;
	}
}
