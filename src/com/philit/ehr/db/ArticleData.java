package com.philit.ehr.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "article")
public class ArticleData implements Serializable, Parcelable {

	private static final long serialVersionUID = 8356064483073516356L;
	
	public static final String COLUMN_NAME_COMMENT_COUNT = "comment_count";
	public static final String COLUMN_NAME_PERIODICAL_ID = "periodical_id";
	public static final String COLUMN_NAME_PERIODICAL_SUBJECT = "periodical_subject";
	public static final String COLUMN_NAME_VERSION = "version";
	public static final String COLUMN_NAME_CREATOR = "creator";
	public static final String COLUMN_NAME_CONTENT = "content";
	public static final String COLUMN_NAME_MODIFIER = "modifier";
	public static final String COLUMN_NAME_ARTICLE_ID = "article_id";
	public static final String COLUMN_NAME_TITLE = "title";
	public static final String COLUMN_NAME_CREATE_TIME = "create_time";
	public static final String COLUMN_NAME_PICTURE_ID = "picture_id";
	public static final String COLUMN_NAME_ARTICLE_DESC = "article_desc";
	public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
	public static final String COLUMN_NAME_ARTICLE_STATUS = "article_status";

	@DatabaseField(columnName = COLUMN_NAME_COMMENT_COUNT)
	private int comment_count;

	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_ID)
	private int periodical_id;

	@DatabaseField(columnName = COLUMN_NAME_PERIODICAL_SUBJECT)
	private String periodical_subject;
	
	@DatabaseField(columnName = COLUMN_NAME_VERSION)
	private int version;
	
	@DatabaseField(columnName = COLUMN_NAME_CREATOR)
	private int creator;
	
	@DatabaseField(columnName = COLUMN_NAME_MODIFIER)
	private int modifier;
	
	@DatabaseField(columnName = COLUMN_NAME_CONTENT)
	private String content;
	
	@DatabaseField(columnName = COLUMN_NAME_ARTICLE_ID, id = true)
	private int article_id;

	@DatabaseField(columnName = COLUMN_NAME_TITLE)
	private String title;

	@DatabaseField(columnName = COLUMN_NAME_CREATE_TIME)
	private long create_time;
	
	@DatabaseField(columnName = COLUMN_NAME_PICTURE_ID)
	private String picture_id;
	
	@DatabaseField(columnName = COLUMN_NAME_ARTICLE_DESC)
	private String article_desc;
	
	@DatabaseField(columnName = COLUMN_NAME_MODIFY_TIME)
	private long modify_time;
	
	@DatabaseField(columnName = COLUMN_NAME_ARTICLE_STATUS)
	private int article_status;
	
	public ArticleData() {

	}
	
	public ArticleData(int comment_count, int periodical_id,
			String periodical_subject, int version, int creator, int modifier,
			String content, int article_id, String title, long create_time,
			String picture_id, String article_desc, long modify_time,
			int article_status) {
		super();
		this.comment_count = comment_count;
		this.periodical_id = periodical_id;
		this.periodical_subject = periodical_subject;
		this.version = version;
		this.creator = creator;
		this.modifier = modifier;
		this.content = content;
		this.article_id = article_id;
		this.title = title;
		this.create_time = create_time;
		this.picture_id = picture_id;
		this.article_desc = article_desc;
		this.modify_time = modify_time;
		this.article_status = article_status;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getPeriodical_id() {
		return periodical_id;
	}

	public void setPeriodical_id(int periodical_id) {
		this.periodical_id = periodical_id;
	}

	public String getPeriodical_subject() {
		return periodical_subject;
	}

	public void setPeriodical_subject(String periodical_subject) {
		this.periodical_subject = periodical_subject;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public String getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}

	public String getArticle_desc() {
		return article_desc;
	}

	public void setArticle_desc(String article_desc) {
		this.article_desc = article_desc;
	}

	public long getModify_time() {
		return modify_time;
	}

	public void setModify_time(long modify_time) {
		this.modify_time = modify_time;
	}

	public int getArticle_status() {
		return article_status;
	}

	public void setArticle_status(int article_status) {
		this.article_status = article_status;
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
		dest.writeInt(comment_count);
		dest.writeInt(periodical_id);
		dest.writeString(periodical_subject);
		dest.writeInt(version);
		dest.writeInt(creator);
		dest.writeInt(modifier);
		dest.writeString(content);
		dest.writeInt(article_id);
		dest.writeString(title);
		dest.writeLong(create_time);
		dest.writeString(picture_id);
		dest.writeString(article_desc);
		dest.writeLong(modify_time);
		dest.writeInt(article_status);
		dest.writeInt(periodical_id);
	}
	
	public static final Parcelable.Creator<ArticleData> CREATOR = new Parcelable.Creator<ArticleData>() {

		@Override
		public ArticleData createFromParcel(Parcel source) {
			return new ArticleData(source);
		}

		@Override
		public ArticleData[] newArray(int size) {
			return new ArticleData[size];
		}

	};

	private ArticleData(Parcel in) {
		super();

		comment_count = in.readInt();
		periodical_id = in.readInt();
		periodical_subject = in.readString();
		version = in.readInt();
		creator = in.readInt();
		modifier = in.readInt();
		content = in.readString();
		article_id = in.readInt();
		title = in.readString();
		create_time = in.readLong();
		picture_id = in.readString();
		article_desc = in.readString();
		modify_time = in.readLong();
		article_status = in.readInt();
		periodical_id = in.readInt();
	}
}
