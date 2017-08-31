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
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Louie on 2017-07-03.
 */
@Controller
@RequestMapping("/content")
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
    public PageResult getAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "search", defaultValue = "") String search) {
        PageResult pageResult = new PageResult();
        Page<Object> page = PageHelper.offsetPage(offset, limit, true);
        pageResult.setRows(this.contentService.getAll(search));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 保存content(修改和新增)
     * @param content
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Content content) {
        if (content.getId() != null) {
            // 修改
            // catetree
            // 获取数据库content
            Content dataContent = this.contentService.getContentById(content.getId());
            if (dataContent.getCid() == content.getCid()) {
                // 不做任何处理
            } else {
                Catetree catetree = new Catetree();
                catetree.setName(content.getTitle());
                // 前端的cid即为pid
                catetree.setPid(content.getCid());
                // 暂时不设置url
                catetree.setChecked(new Byte("0"));
                catetree.setOpen(new Byte("1"));
                catetree.setPath(content.getUrl());
                this.catetreeService.upd(
                        dataContent.getCid(),
                        catetree.getPid(),
                        catetree.getName(),
                        catetree.getUrl(),
                        catetree.getOpen(),
                        catetree.getChecked(),
                        catetree.getPath());
            }
            // content cid不变
            content.setLasteditname("管理员");
            content.setEdittime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            this.contentService.upd(
                    content.getId(),
                    content.getTitle(),
                    content.getLasteditname(),
                    content.getEdittime(),
                    content.getState(),
                    content.getContent(),
                    content.getDescription(),
                    content.getUrl(),
                    dataContent.getCid());
            return "redirect:/content";
        }
        // 新增
        // catetree
        Catetree catetree = new Catetree();
        catetree.setName(content.getTitle());
        // 前端的cid即为pid
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
        // 重新设置content的cid为catetree的id
        Integer cid = this.catetreeService.getCid();

        // content
        content.setCreatename("管理员");
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
                cid);
        return "redirect:/content";
    }

    @RequestMapping(value = "/del/{id}/{cid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String del(@PathVariable("id") Integer id, @PathVariable("cid") Integer cid) {
        // 删除content
        this.contentService.del(id);
        // 删除catetree
        this.catetreeService.delById(cid);
        return "删除成功";
    }
}
