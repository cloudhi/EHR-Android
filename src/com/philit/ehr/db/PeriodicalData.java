package com.philit.ehr.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "periodical")
public class PeriodicalData implements Serializable, Parcelable {

	private static final long serialVersionUID = 8356064483073516356L;

	public static final String COLUMN_NAME_PUBLISH_ID = "publish_id";
	public static final String COLUMN_NAME_PERIODICAL_DESC = "periodical_desc";
	public static final String COLUMN_NAME_PERIODICAL_PUBLISH_TIME = "periodical_publish_time";
	public static final String COLUMN_NAME_EXTRA = "extra";
	public static final String COLUMN_NAME_PERIODICAL_SUBJECT = "periodical_subject";
	public static final String COLUMN_NAME_PERIODICAL_ID = "periodical_id";

	@DatabaseField(columnName = COLUMN_NAME_PUBLISH_ID, id = true)
	private int publish_id;

	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_DESC)
	private String periodical_desc;

	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_PUBLISH_TIME)
	private long periodical_publish_time;
	
	@DatabaseField(columnName = COLUMN_NAME_EXTRA)
	private String extra;
	
	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_SUBJECT)
	private String periodical_subject;
	
	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_ID)
	private int periodical_id;
	
	public PeriodicalData() {

	}

	public PeriodicalData(int publish_id, String periodical_desc,
			long periodical_publish_time, String extra,
			String periodical_subject, int periodical_id) {
		super();
		this.publish_id = publish_id;
		this.periodical_desc = periodical_desc;
		this.periodical_publish_time = periodical_publish_time;
		this.extra = extra;
		this.periodical_subject = periodical_subject;
		this.periodical_id = periodical_id;
	}
	
	@Override
	public String toString() {
		return "PeriodicalData [publish_id=" + publish_id
				+ ", periodical_desc=" + periodical_desc
				+ ", periodical_publish_time=" + periodical_publish_time
				+ ", extra=" + extra + ", periodical_subject="
				+ periodical_subject + ", periodical_id=" + periodical_id + "]";
	}
	
	public int getPublish_id() {
		return publish_id;
	}

	public void setPublish_id(int publish_id) {
		this.publish_id = publish_id;
	}

	public String getPeriodical_desc() {
		return periodical_desc;
	}

	public void setPeriodical_desc(String periodical_desc) {
		this.periodical_desc = periodical_desc;
	}

	public long getPeriodical_publish_time() {
		return periodical_publish_time;
	}

	public void setPeriodical_publish_time(long periodical_publish_time) {
		this.periodical_publish_time = periodical_publish_time;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getPeriodical_subject() {
		return periodical_subject;
	}

	public void setPeriodical_subject(String periodical_subject) {
		this.periodical_subject = periodical_subject;
	}

	public int getPeriodical_id() {
		return periodical_id;
	}

	public void setPeriodical_id(int periodical_id) {
		this.periodical_id = periodical_id;
	}

	/**
	 * Parcelable methods
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(publish_id);
		dest.writeString(periodical_desc);
		dest.writeLong(periodical_publish_time);
		dest.writeString(extra);
		dest.writeString(periodical_subject);
		dest.writeInt(periodical_id);
	}
	
	public static final Parcelable.Creator<PeriodicalData> CREATOR = new Parcelable.Creator<PeriodicalData>() {

		@Override
		public PeriodicalData createFromParcel(Parcel source) {
			return new PeriodicalData(source);
		}

		@Override
		public PeriodicalData[] newArray(int size) {
			return new PeriodicalData[size];
		}

	};

	private PeriodicalData(Parcel in) {
		super();
		publish_id = in.readInt();
		periodical_desc = in.readString();
		periodical_publish_time = in.readLong();
		extra = in.readString();
		periodical_subject = in.readString();
		periodical_id = in.readInt();
	}
}
