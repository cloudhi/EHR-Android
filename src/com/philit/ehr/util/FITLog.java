package com.philit.ehr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.philit.ehr.EHRApp;


public class FITLog {

    public static void log(String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append("-");
        builder.append(msg);
        builder.append("\n");
        write(builder.toString());
    }
    
    public static void logRequest(String api, long timeConsume) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append("_Request_");
        builder.append(api);
        builder.append("_");
        builder.append(timeConsume);
        builder.append("\n");
        write(builder.toString());
    }
    
    public static void logAction(String action) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append("_Action_");
        builder.append(action);
        builder.append("\n");
        write(builder.toString());
    }
    
    private static void write(String msg) {
        File logDir = new File(EHRApp.getInstance().logPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (logDir.exists()) {
            File logFile = new File(EHRApp.getInstance().logPath + "cloudnote.log_" 
                    + DateTools.timestampToStr(System.currentTimeMillis(), "yyyy-MM-dd"));
            try {
                FileWriter writer = new FileWriter(logFile, true);
                writer.write(msg);
                writer.close();
            } catch (IOException e) {
            }
        }
    }
    
    public static String getLog() {
        StringBuilder log = new StringBuilder();
        File logDir = new File(EHRApp.getInstance().logPath);
        if (logDir.exists()) {
            File[] logFiles = logDir.listFiles();
            for (File file : logFiles) {
                if (file.getAbsolutePath().equals(EHRApp.getInstance().logPath 
                    + "cloudnote.log_" + DateTools.timestampToStr(System.currentTimeMillis(), "yyyy-MM-dd"))) {
                    continue;
                }
                try {
                    byte[] buf = new byte[1024];
                    int count = 0;
                    FileInputStream is = new FileInputStream(file);
                    while ((count = is.read(buf, 0, 1024)) != -1) {
                        log.append(new String(buf, 0, count));
                    }
                    is.close();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
                file.delete();
            }
            return log.toString();
        }
        return null;
    }
}
