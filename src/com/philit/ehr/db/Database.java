package com.philit.ehr.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.philit.ehr.EHRApp;

public class Database {

	private static final String TAG = "Database";
	private DatabaseHelper databaseHelper;
	private Dao<PeriodicalData, Integer> periodicalDao;
	private Dao<ArticleData, Integer> articleDao;
	private Dao<ImageData, Integer> imageDao;

	/**
	 * 程序启动后马上初始化databaseHelper
	 */
	public void setApplication(EHRApp application) {
		databaseHelper = OpenHelperManager.getHelper(application,
				DatabaseHelper.class);
	}

	public Dao<PeriodicalData, Integer> getPeriodicalDao() {
		if (periodicalDao == null) {
			try {
				periodicalDao = databaseHelper.getDao(PeriodicalData.class);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return periodicalDao;
	}
	
	public Dao<ArticleData, Integer> getArticleDao() {
		if (articleDao == null) {
			try {
				articleDao = databaseHelper.getDao(ArticleData.class);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return articleDao;
	}
	
	public Dao<ImageData, Integer> getImageDao() {
		if (imageDao == null) {
			try {
				imageDao = databaseHelper.getDao(ImageData.class);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return imageDao;
	}
	
	/*
	 * Periodical start
	 */
	
	/**
	 * 插入Periodical记录
	 * @param periodicalData
	 */
	public void createPeriodical(PeriodicalData periodicalData) {
		try {
			getPeriodicalDao().createIfNotExists(periodicalData);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <createPeriodical> : " + e.getMessage());
		}
	}
	
	/**
	 * 返回所有期刊
	 * @return
	 */
	public List<PeriodicalData> getPeriodicalList() {
		List<PeriodicalData> periodicalDatas = new ArrayList<PeriodicalData>();
		try {
			periodicalDatas = getPeriodicalDao()
					.queryBuilder()
					.orderBy(PeriodicalData.COLUMN_NAME_PERIODICAL_ID, false)
					.query();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getPeriodicalDatas> : " + e.getMessage());
		}
		return periodicalDatas;
	}
	
	/**
	 * 
	 * @param periodicalList
	 */
	public void refreshPeriodicalList(List<PeriodicalData> periodicalList) {
		try {
			for (PeriodicalData periodical : periodicalList) {
				this.getPeriodicalDao().refresh(periodical);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <refreshPeriodicalList> : " + e.getMessage());
		}
	}
	
	/*
	 * ArticleData start
	 */
	/**
	 * 创建文章
	 * @param articleData
	 */
	public void createArticle(ArticleData articleData) {
		try {
			getArticleDao().create(articleData);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <createArticle> : " + e.getMessage());
		}
	}

	/**
	 * 返回期刊文章列表
	 * @param periodicalData 期刊对象
	 */
	public List<ArticleData> getArticleList(int periodicalDataId){
		List<ArticleData> articleDatas = new ArrayList<ArticleData>();
		try {
			articleDatas = getArticleDao().queryForEq(ArticleData.COLUMN_NAME_PERIODICAL_ID, periodicalDataId);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getArticelList> : " + e.getMessage());
		}
		return articleDatas;
	}
	
	/**
	 * 返回期刊文章列表
	 * @param periodicalData 期刊对象
	 */
	public List<ArticleData> getArticleList(PeriodicalData periodicalData){
		List<ArticleData> articleDatas = new ArrayList<ArticleData>();
		try {
			articleDatas = getArticleDao().queryForEq(ArticleData.COLUMN_NAME_PERIODICAL_ID, periodicalData.getPeriodical_id());
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getArticelList> : " + e.getMessage());
		}
		return articleDatas;
	}
	
	/**
	 * 得到某一篇文章
	 * @param articleId
	 * @return
	 */
	public ArticleData getArticleById(int articleId) {
		ArticleData articleData = null;
		try {
			List<ArticleData> articleDatas = getArticleDao().queryBuilder()
											.where()
											.eq(ArticleData.COLUMN_NAME_ARTICLE_ID, articleId)
											.query();
			if (articleDatas.size() > 0) {
				articleData = articleDatas.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getArticleById> : " + e.getMessage());
		}
		return articleData;
	}
	
	/**
	 * 更新文章
	 * @param articleData
	 */
	public void updateArticle(ArticleData articleData) {
		try {
			getArticleDao().update(articleData);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <updateArticle> : " + e.getMessage());
		}
	}
	
	/*
	 * ImageData start
	 */
	/**
	 * 创建图片
	 * @param imageData
	 */
	public void createImage(ImageData imageData) {
		try {
			getImageDao().create(imageData);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <createImage> : " + e.getMessage());
		}
	}

	
	/**
	 * 获取文章图片列表
	 * @param articleData
	 * @param thumb
	 * @return
	 */
	public List<ImageData> getImageListByArticle(ArticleData articleData, int thumb){
		List<ImageData> imageDatas = new ArrayList<ImageData>();
		try {
			imageDatas = getImageDao().queryBuilder()
					.where()
					.eq(ArticleData.COLUMN_NAME_ARTICLE_ID, articleData.getArticle_id())
					.and()
					.eq(ImageData.COLUMN_NAME_THUMB, thumb)
					.query();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getImageListByArticle> : " + e.getMessage());
		}
		return imageDatas;
	}
	
	/**
	 * 得到某一张图片
	 * @param reomteId
	 * @return
	 */
	public ImageData getImageByRemoteId(int remoteId) {
		ImageData imageData = null;
		try {
			List<ImageData> imageDatas= getImageDao().queryForEq(ImageData.COLUMN_NAME_REMOTE_ID, remoteId);
			if (imageDatas.size() > 0) {
				imageData = imageDatas.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <getImageByRemoteId> : " + e.getMessage());
		}
		return imageData;
	}
	
	/**
	 * 更新文章
	 * @param imageData
	 */
	public void updateImage(ImageData imageData) {
		try {
			getImageDao().update(imageData);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL fail <updateImage> : " + e.getMessage());
		}
	}

	// *************************
	// AppSettings singleton (begin)
	// *************************

	private static Database mInstance;

	private Database() {

	}

	public static Database getInstance() {
		if (mInstance == null) {
			synchronized (Database.class) {
				if (mInstance == null) {
					mInstance = new Database();
				}
			}
		}
		return mInstance;
	}

	// *************************
	// AppSettings singleton (end)
	// *************************

}
