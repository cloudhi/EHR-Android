package com.philit.ehr.util;

public class VersionCompare {
    
    /**
     * 比较程序版本号与升级包版本号的大小,如果程序版本号更高,则返回1, 相等则返回0, 小于则返回-1
     * @param appVer 不能为null
     * @param updateVer 不能为null
     * @return
     */
    public static int compareVersion(String appVer, String updateVer)
    {
        if (appVer.equals(updateVer))
            return 0;
        String[] appVerNum = appVer.split("\\.");
        String[] updateVerNum = updateVer.split("\\.");
        for (int i = 0; i < Math.min(appVerNum.length, updateVerNum.length); ++i)
        {
            if (Integer.parseInt(appVerNum[i]) > Integer.parseInt(updateVerNum[i]))
                return 1;
            else if (Integer.parseInt(appVerNum[i]) < Integer.parseInt(updateVerNum[i]))
                return -1;
        }
        return appVerNum.length > updateVerNum.length ? 1 : -1;
    }
}
