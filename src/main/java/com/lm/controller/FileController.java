package com.lm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lm.domain.File;
import com.lm.domain.FileInputResult;
import com.lm.domain.PageResult;
import com.lm.service.FileService;
import com.lm.utils.DeleteUtil;
import com.lm.utils.DownloadUtil;
import com.lm.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Louie on 2017-06-26.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    // 文件上传地址
    @Value("${web.upd-path}")
    private String path;

    /**
     * 获取所有file
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PageResult all(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit) {
        Page<Object> page = PageHelper.startPage(offset, limit, true);
        List<File> files = this.fileService.getAll();

        long total = page.getTotal();

        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRows(files);

        return pageResult;
    }

    /**
     * 自定义上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileInputResult upload(MultipartFile file) {
        FileInputResult fileInputResult = new FileInputResult();
        if (file.isEmpty()) {
            fileInputResult.setName("失败");
            fileInputResult.setMsg("文件为空");
            return fileInputResult;
        }
        // 上传文件
        String url = new UploadUtil().upload(file, path);

        File newFile = new File();
        String fileName = file.getOriginalFilename();
        newFile.setOldname(fileName);
        newFile.setPath(url);
        newFile.setSize(Integer.parseInt(file.getSize() + ""));
        newFile.setType(file.getContentType());
        newFile.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
        newFile.setNewname(url.substring(url.lastIndexOf("/")));
        newFile.setUploadtime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()).toString());

        // 同步至数据库
        int i = this.fileService.add(
                newFile.getOldname(),
                newFile.getNewname(),
                newFile.getPath(),
                newFile.getUploadtime(),
                newFile.getType(),
                newFile.getDescription(),
                newFile.getSuffix(),
                newFile.getSize());
        if (i < 0) {
            fileInputResult.setName("失败");
            fileInputResult.setMsg("上传失败");
            return fileInputResult;
        }

        fileInputResult.setName("成功");
        fileInputResult.setMsg("上传成功");
        return fileInputResult;
    }

    /**
     * CKEditor上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/ckupload", method = RequestMethod.POST)
    public String ckupload(
            @RequestParam("upload") MultipartFile file,
            @RequestParam("CKEditorFuncNum") Integer CKEditorFuncNum,
            HttpServletResponse response) {
        if (file.isEmpty()) {
            return "文件为空，请选择需要上传的文件";
        }
        // 上传文件
        String url = new UploadUtil().upload(file, path);

        File newFile = new File();
        String fileName = file.getOriginalFilename();
        newFile.setOldname(fileName);
        newFile.setPath(url);
        newFile.setSize(Integer.parseInt(file.getSize() + ""));
        newFile.setType(file.getContentType());
        newFile.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
        newFile.setNewname(url.substring(url.lastIndexOf("/")));
        newFile.setUploadtime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()).toString());

        // 同步至数据库
        int i = this.fileService.add(
                newFile.getOldname(),
                newFile.getNewname(),
                newFile.getPath(),
                newFile.getUploadtime(),
                newFile.getType(),
                newFile.getDescription(),
                newFile.getSuffix(),
                newFile.getSize());
        if (i < 0) {
            return "服务器繁忙";
        }
        // 图片地址自动填写到url
        OutputStream writer;
        String callback = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum +
                ", '" + url + "');</script>";
        try {
            writer = response.getOutputStream();
            writer.write(callback.getBytes());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传成功！";
    }

    /**
     * 下载文件
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/download/{id}")
    public String download(HttpServletResponse response, @PathVariable("id") Integer id) {
        // 根据Id获取File
        File file = this.fileService.getFileById(id);
        // 下载文件
        if (new DownloadUtil().download(response, path + file.getPath(), file.getOldname())) {
            return "下载成功";
        }
        return "下载失败";
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public String delById(@PathVariable("id") Integer id) {
        File file = this.fileService.getFileById(id);
        String url = file.getPath();
        // 删除本地文件
        Boolean dellocal = new DeleteUtil().delete(path + url);
        // 删除数据库
        Integer deldatabase = this.fileService.deleteById(id);
        if (dellocal && deldatabase > 0) {
            return "文件删除成功";
        } else if (!dellocal) {
            return "本地文件删除失败，请手动删除";
        } else {
            return "数据库删除失败，请联系管理员";
        }
    }
}
