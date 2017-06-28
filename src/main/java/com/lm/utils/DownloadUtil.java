package com.lm.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载工具
 * Created by Louie on 2017-06-28.
 */
public class DownloadUtil {

    public Boolean download(HttpServletResponse response, String path, String oldname) {
        if (path != null) {
            // 创建文件对象
            File file = new File(path);
            if (file.exists()) {
                // 设置下载文件类型(application/octet-stream：二进制流，不知道什么类型)
                response.setContentType("application/force-download");  // 只下载不打开
                // 添加响应头信息(解决中文文件名乱码)
                try {
                    response.addHeader("Content-Disposition","attachment;fileName=" + new String(oldname.getBytes("gbk"),"iso-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    // 获取file的文件输入流
                    fis = new FileInputStream(file);
                    // 获取buffered输入流
                    bis = new BufferedInputStream(fis);
                    // 获取输出流
                    OutputStream ops = response.getOutputStream();
                    // 读取buffered输入流,每次读取buffer个字节
                    int i = bis.read(buffer);
                    while (i != -1) {
                        // 写入输出流
                        ops.write(buffer, 0, i);
                        // 继续读取
                        i = bis.read(buffer);
                    }
                    return Boolean.TRUE;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return Boolean.FALSE;
                } catch (IOException e) {
                    e.printStackTrace();
                    return Boolean.FALSE;
                } finally {
                    if (bis != null) {
                        try {
                            // 关闭流
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
