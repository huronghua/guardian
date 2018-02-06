package com.banmatrip.guardian.core.utils;

/**
 * @author Miracle Xu
 * @Description: 文件后缀判断
 * @create 2017-09-19 18:06
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class WDWUtil {
    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
