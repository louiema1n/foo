package com.lm.service;

import com.lm.domain.Catetree;
import com.lm.mapper.CatetreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-07-03.
 */
@Service
public class CatetreeService {

    @Autowired
    private CatetreeMapper catetreeMapper;

    public List<Catetree> getAll() {
        return this.catetreeMapper.selectAll();
    }

    public Integer add(Integer pid, String name, String url, byte open, byte checked, String path) {
        return this.catetreeMapper.insert(pid, name, url, open, checked, path);
    }
}
