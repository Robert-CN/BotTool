package com.zyxm.common.util;

import cn.hutool.core.util.ArrayUtil;
import com.zyxm.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @Author Robert
 * @Create 2020/9/11
 * @Desc TODO 文件工具类
 **/
@Slf4j
public class FileUtil {
    /**
     * 创建文件
     */
    public static File createFile(String fileName, String fileSuffix) {
        File file = null;
        try {
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.indexOf("."));
            }
            file = File.createTempFile(fileName + "-", fileSuffix);
        } catch (Exception e) {
            log.debug(ResultEnum.FILE_GENERATE_FAILURE.msg);
        }
        return file;
    }

    /**
     * 递归删除
     */
    public static Boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            file.getParentFile().delete();
            return true;
        }
        File[] files = file.listFiles();
        if (ArrayUtil.isEmpty(files)) {
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            delFile(files[i]);
        }
        file.delete();
        return true;
    }

    public static Boolean delFile(List<File> fileList) {
        for (File file : fileList) {
            Boolean bol = delFile(file);
            if (!bol) {
                return false;
            }
        }
        return true;
    }

    /**
     * 格式化分隔符
     */
    public static String decollatorFormat(String str) {
        return str.replace("\\", "/");
    }
}
