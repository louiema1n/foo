package com.lm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lm.domain.Catetree;
import com.lm.domain.Content;
import com.lm.domain.PageResult;
import com.lm.service.CatetreeService;
import com.lm.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Louie on 2017-07-03.
 */
@Controller
@RequestMapping("content/")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CatetreeService catetreeService;

    /**
     * 查询所有content
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public PageResult getAll(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        PageResult pageResult = new PageResult();
        Page<Object> page = PageHelper.offsetPage(offset, limit, true);
        pageResult.setRows(this.contentService.getAll());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 保存content
     * @param content
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Content content) {
        if (content.getId() != null) {
            // 修改
        }
        // 新增
        // content
        content.setCreatename("test");
        content.setCreattime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.contentService.add(
                content.getTitle(),
                content.getCreatename(),
                content.getLasteditname(),
                content.getCreattime(),
                content.getEdittime(),
                content.getState(),
                content.getContent(),
                content.getDescription(),
                content.getUrl(),
                content.getCid());
        // catetree
        Catetree catetree = new Catetree();
        catetree.setName(content.getTitle());
        catetree.setPid(content.getCid());
        // 暂时不设置url
        catetree.setChecked(new Byte("0"));
        catetree.setOpen(new Byte("1"));
        catetree.setPath(content.getUrl());
        this.catetreeService.add(catetree.getPid(),
                catetree.getName(),
                catetree.getUrl(),
                catetree.getOpen(),
                catetree.getChecked(),
                catetree.getPath());
        return "redirect:/content";
    }
}
