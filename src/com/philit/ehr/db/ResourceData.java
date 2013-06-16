package com.philit.ehr.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "resources")
public class ResourceData implements Parcelable {

	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_TYPE = "type";
	public static final String COLUMN_NAME_REMOTE_ID = "remote_id";
	public static final String COLUMN_NAME_REMOTE_URL = "remote_url";
	public static final String COLUMN_NAME_LOCAL_FILE_NAME = "local_file_name";
	public static final String COLUMN_NAME_PERIODICAL_ID = "periodical_id";
	public static final String COLUMN_NAME_ARTICLE_ID = "article_id";
	
	public static final int TYPE_VIDEO = 0;
	public static final int TYPE_IMAGE = 1;

	@DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
	private int id;

	@DatabaseField(columnName = COLUMN_NAME_TYPE)
	private int type;
	
	@DatabaseField(columnName = COLUMN_NAME_REMOTE_ID)
	private long remote_id;

	@DatabaseField(columnName = COLUMN_NAME_REMOTE_URL)
	private String remote_url;

	@DatabaseField(columnName = COLUMN_NAME_LOCAL_FILE_NAME)
	private String local_file_name;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COLUMN_NAME_PERIODICAL_ID)
	private PeriodicalData periodicalData;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COLUMN_NAME_ARTICLE_ID)
	private ArticleData articleData;

	public ResourceData() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
