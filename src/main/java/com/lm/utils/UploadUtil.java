package com.lm.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传工具
 * Created by Louie on 2017-06-28.
 */
public class UploadUtil {
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 格式化新文件名，避免重复
        String suffixFn = fileName.substring(fileName.lastIndexOf("."));
        String dataStr = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        String newFn = dataStr + UUID.randomUUID() + suffixFn;
        File dest = new File(path + newFn);
        if (!dest.getParentFile().exists()) {
            // 创建多及目录
            dest.getParentFile().mkdirs();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
            bos.write(file.getBytes());
            bos.flush();
            bos.close();
            return newFn;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
