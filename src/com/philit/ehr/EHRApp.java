package com.philit.ehr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.philit.ehr.db.Database;
import com.philit.ehr.define.Define;
import com.philit.ehr.http.client.EHRClient;
import com.philit.ehr.util.FileUtil;


public class EHRApp extends Application {

    public AppState appState;
    
    private String internalStoragePath;
    private String currentDirectory;
    public String resourcePath;
    public String tempDirectory;
    public String sdcardPath;
    public String logPath;
    public String cachePath;
    
    public boolean needUnlock = true;
    
    private static EHRApp instance;
    
    public static EHRApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initialize();
    }

    @Override
    public void onTerminate() {
        persistSave();
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        persistSave();
        super.onLowMemory();
    }
    
    private void initialize() {
        appState = new AppState();
        persistLoad();
        updatePath();
        moveAssetFileToSD();
        
        Database.getInstance().setApplication(this);
        
        EHRClient.getInstance().setContext(this);
    }
    
    // 载入持久化数据
    public void persistLoad() 
    {
        SharedPreferences prefer = getSharedPreferences(Define.PERSISTENCE_NAME, MODE_PRIVATE);
        appState.persistLoad(prefer);
    }
        
    /**
     * 活动的Activity onStop调用
     */
    public void persistSave()
    {
        SharedPreferences prefer = getSharedPreferences(Define.PERSISTENCE_NAME, MODE_PRIVATE);
        appState.persistSave(prefer);
    }
    
    public void persistClear() {
        SharedPreferences prefer = getSharedPreferences(Define.PERSISTENCE_NAME, MODE_PRIVATE);
        appState.clear(prefer);
    }
    
    public static boolean isSDCardMounted()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    
    private void initInternalStoragePath()
    {
        if (isSDCardMounted()) 
        {
            return;
        }
            
        for (String path : Define.INTERNAL_STORAGE_PATHS) 
        {
            if (FileUtil.isFileCanReadAndWrite(path)) 
            {
                internalStoragePath = path;
                return;
            }
            else 
            {
                File f = new File(path);
                if (f.isDirectory()) 
                {
                    for (File file : f.listFiles()) 
                    {
                        if (file != null 
                                && file.isDirectory()
                                && !file.isHidden()
                                && FileUtil.isFileCanReadAndWrite(file
                                        .getPath())) 
                        {
                            internalStoragePath = file.getPath();
                            if (!internalStoragePath.endsWith(File.separator)) {
                                internalStoragePath += File.separator;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private void moveAssetFileToSD() {
        File file = new File(cachePath + "sina_weibo.png");
        if (!file.exists()) {
            try {
                InputStream is = getResources().getAssets().open("image/sina_weibo.png");
                FileUtil.writeToFile(file.getAbsolutePath(), is);
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public void updatePath()
    {
        initInternalStoragePath();
        this.currentDirectory = this.getFilesDir().getAbsolutePath().concat(File.separator);
        this.resourcePath = this.currentDirectory + Define.RESOURCE_PATH_NOSD;
        this.tempDirectory = this.currentDirectory + Define.TEMPFILE_PATH_NOSD;
        this.logPath = this.currentDirectory + Define.LOG_PATH_NOSD;
        this.cachePath = this.currentDirectory + Define.CACHE_PATH;
        
        String storagePath = null;
        if (isSDCardMounted())
        {
            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            this.sdcardPath = storagePath.concat(File.separator);
        }
        else if (null != internalStoragePath)
        {
            storagePath = internalStoragePath;
        }

        if (null != storagePath)
        {
            this.resourcePath = storagePath.concat(File.separator) + Define.RESOURCE_PATH;
            this.tempDirectory = storagePath.concat(File.separator) + Define.TEMPFILE_PATH;
            this.logPath = storagePath.concat(File.separator) + Define.LOG_PATH;
            this.cachePath = storagePath.concat(File.separator) + Define.CACHE_PATH;
        }
        
        File resourceDir = new File(resourcePath);
        File tempDir = new File(tempDirectory);
        File logDir = new File(logPath);
        File cacheDir = new File(cachePath);
        if (!resourceDir.exists()) {
            resourceDir.mkdirs();
        }
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }
    
    public boolean checkNetState() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkWifiState() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnectedOrConnecting()) {
            return true;
        }
        else {
            return false;
        }
    }
    
}
