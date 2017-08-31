package com.lm.service;

import com.lm.domain.Tmp;
import com.lm.mapper.TmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-07-25.
 */
@Service
public class TmpService {

    @Autowired
    private TmpMapper tmpMapper;

    public Integer add(String content) {
        return this.tmpMapper.insert(content);
    }

    public List<Tmp> getAll() {
        return this.tmpMapper.selectAll();
    }
}
