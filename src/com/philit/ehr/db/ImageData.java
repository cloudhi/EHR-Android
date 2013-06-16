package com.philit.ehr.db;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.philit.ehr.define.Define;

@DatabaseTable(tableName = "images")
public class ImageData implements Serializable, Parcelable {

	private static final long serialVersionUID = 8356064483073516356L;

	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_REMOTE_ID = "remote_id";
	public static final String COLUMN_NAME_THUMB = "thumb";
	public static final String COLUMN_NAME_LOCAL_FILE_NAME = "local_file_name";
	public static final String COLUMN_NAME_ARTICLE_ID = "article_id";
	public static final int THUMB_0 = 0, THUMB_1 = 1;
	
	@DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
	private int id;
	
	@DatabaseField(columnName = COLUMN_NAME_REMOTE_ID)
	private int remoteId;

	@DatabaseField(columnName = COLUMN_NAME_THUMB)
	private int thumb; //默认为0，如果是缩略图则置为1

	@DatabaseField(columnName = COLUMN_NAME_LOCAL_FILE_NAME)
	private String localFileName;
	
	@DatabaseField(foreignColumnName = ArticleData.COLUMN_NAME_ARTICLE_ID, foreign = true, foreignAutoRefresh = true, columnName = COLUMN_NAME_ARTICLE_ID)
	private ArticleData articleData;

	public ImageData() {

	}

	public ImageData(int id, int remoteId, int thumb, String localFileName, ArticleData articleData) {
		super();
		this.id = id;
		this.remoteId = remoteId;
		this.thumb = thumb;
		this.localFileName = localFileName;
		this.articleData = articleData;
		if (this.localFileName == "") {
			generateLocalFilename();
		}
	}

	@Override
	public String toString() {
		return "ImageData [id=" + id + ", remoteId=" + remoteId + ", thumb="
				+ thumb + ", localFileName=" + localFileName + ", articleData="
				+ articleData + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(int remoteId) {
		this.remoteId = remoteId;
	}

	public ArticleData getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleData articleData) {
		this.articleData = articleData;
	}

	public int getThumb() {
		return thumb;
	}

	public void setThumb(int thumb) {
		this.thumb = thumb;
	}

	public String getLocalFileName() {
		return localFileName;
	}

	public void setLocalFileName(String localFileName) {
		this.localFileName = localFileName;
	}
	
	public void generateLocalFilename() {
		this.localFileName = UUID.randomUUID().toString() + ".jpg";
	}
	
	//获取图片本地路径
	public String getLocalPath() {
		String SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator;
		return SDCardRoot + Define.RESOURCE_PATH + localFileName;
	}
	
	/*
	 * Parcelable methods
	 */

	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(remoteId);
		dest.writeInt(thumb);
		dest.writeString(localFileName);
		dest.writeSerializable(articleData);
	}
	
	public static final Parcelable.Creator<ImageData> CREATOR = new Parcelable.Creator<ImageData>() {

		@Override
		public ImageData createFromParcel(Parcel source) {
			return new ImageData(source);
		}

		@Override
		public ImageData[] newArray(int size) {
			return new ImageData[size];
		}

	};

	private ImageData(Parcel in) {
		super();
		id = in.readInt();
		remoteId = in.readInt();
		thumb = in.readInt();
		localFileName = in.readString();
		articleData = (ArticleData) in.readSerializable();
	}

}
