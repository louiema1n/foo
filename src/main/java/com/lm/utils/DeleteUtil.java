package com.lm.utils;
import java.io.*;

/**
 * 删除文件
 * Created by Louie on 2017-07-02.
 */
public class DeleteUtil {
    public Boolean delete(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            // 不存在
            return Boolean.FALSE;
        } else if (file.isFile()) {
            // 文件
            return deleteFile(filename);
        } else {
            // 目录
            return deletedirectory(filename);
        }
    }

    /**
     * 删除目录-暂不实现
     * @param filename
     * @return
     */
    private Boolean deletedirectory(String filename) {
        return true;
    }

    /**
     * 删除文件
     * @param filename
     * @return
     */
    private Boolean deleteFile(String filename) {
        File file = new File(filename);
        if (file.delete()) {
            return true;
        }
        return false;
    }
}
