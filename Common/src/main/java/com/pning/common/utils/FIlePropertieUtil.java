package com.pning.common.utils;

/**
 * @Author Pning
 * @Date 2021/12/29 15:47
 **/
public class FIlePropertieUtil {

    /**
     * 切割文件名得到文件后缀
     * @param fileName
     * @return
     */
    public static String getfileExtName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
