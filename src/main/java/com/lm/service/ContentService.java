package com.lm.service;

import com.lm.domain.Content;
import com.lm.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-07-03.
 */
@Service
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    public List<Content> getAll(String search) {
        return this.contentMapper.selectAll(search);
    }

    public Integer add(String title, String createname, String lasteditname, String creattime, String edittime, byte state, String content, String description, String url, Integer cid) {
        return this.contentMapper.add(title, createname, lasteditname, creattime, edittime, state, content, description, url, cid);
    }

    public Content getContentById(Integer id) {
        return this.contentMapper.selectById(id);
    }

    public Content getContentByCid(Integer cid) {
        return this.contentMapper.selectByCid(cid);
    }

    public Integer upd(Integer id, String title, String lasteditname, String edittime, byte state, String content, String description, String url, Integer cid) {
        return this.contentMapper.upd(id, title, lasteditname, edittime, state, content, description, url, cid);
    }

    public Integer del(Integer id) {
        return this.contentMapper.delete(id);
    }
}
