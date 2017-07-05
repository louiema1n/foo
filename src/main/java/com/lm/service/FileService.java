package com.lm.service;

import com.lm.domain.File;
import com.lm.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Louie on 2017-06-26.
 */
@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public List<File> getAll() {
        return this.fileMapper.selectAll();
    }

    public Integer add(String oldname, String newname, String path, String uploadtime, String type, String description, String suffix, Integer size) {
        return this.fileMapper.insert(oldname,newname,path,uploadtime,type,description,suffix,size);
    }

    public Integer allTotal() {
        return this.fileMapper.count();
    }

    public File getFileById(Integer id) {
        return this.fileMapper.selectFileById(id);
    }

    public Integer deleteById(Integer id) {
        return this.fileMapper.delFileById(id);
    }
}
