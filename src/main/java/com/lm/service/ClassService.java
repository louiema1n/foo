package com.lm.service;

import com.lm.domain.Class;
import com.lm.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@Service
public class ClassService {
    @Autowired
    private ClassMapper classMapper;

    public List<Class> queryAll() {
        return this.classMapper.selectAll();
    };
}
