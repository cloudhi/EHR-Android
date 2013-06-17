package com.philit.ehr.define;

import com.philit.ehr.EHRApp;


public class Define {

    public static final float DENSITY = EHRApp.getInstance().getResources().getDisplayMetrics().density;
    public static final int widthPx = EHRApp.getInstance().getResources().getDisplayMetrics().widthPixels;
    public static final int heightPx = EHRApp.getInstance().getResources().getDisplayMetrics().heightPixels;
    
    public static final int UNDEFINE = -1;
    public static final String PERSISTENCE_NAME = "application_appstate";
    
    public static final String[] INTERNAL_STORAGE_PATHS = new String[]{"/mnt/", "/emmc/"};
    public static final String RESOURCE_PATH = "EHR/resource/"; 
    public static final String IMAGE_PATH = "EHR/resource/image"; 
    public static final String DOCUMENT_PATH = "EHR/resource/document/"; 
    public static final String TEMPFILE_PATH = "EHR/temp/";      
    public static final String LOG_PATH = "EHR/log/";
    public static final String CACHE_PATH = "EHR/cache/";
    
    public static final String RESOURCE_PATH_NOSD = "resource/"; 
    public static final String IMAGE_PATH_NOSD = "resource/image";   
    public static final String DOCUMENT_PATH_NOSD = "resource/document";   
    public static final String TEMPFILE_PATH_NOSD = "temp/";
    public static final String LOG_PATH_NOSD = "log/";
    
    public static final String FILE_EXTENSION_JPG = ".jpg";
    public static final String FILE_EXTENSION_MP4 = ".m4a";
    
    public static final String ONLINE_SERVER = "http://app.neweekly.com.cn:51001";
    public static final String DEBUG_SERVER = "http://newweekly.EHR.com:51000";
    
    public static final String client_id = "f2e616785571fa0521d185c5fdge4ne068f6";
    public static final String client_secret = "20807e83fd64d0evddvde79cnd4a88a22";
    
    public static final String SINA_APP_KEY = "2219502618";
    public static final String SINA_APP_SECRET = "c9d9715176a6bdb020c17f2810c460e8";
    
    public static final boolean DEBUG = true;
    
}
