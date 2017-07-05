package com.lm.controller;

import com.lm.service.CatetreeService;
import com.lm.service.ContentService;
import com.lm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Louie on 2017-06-26.
 */
@Controller
public class IndexController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CatetreeService catetreeService;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "main/main";
    }

    @RequestMapping("/file")
    public String file(Model model) {
        model.addAttribute("picTotal", this.fileService.allTotal());
        return "file/file";
    }

    @RequestMapping("/content")
    public String editor() {
        return "content/content";
    }

    @RequestMapping("/ck")
    public String ck() {
        return "content/ckeditor";
    }

    @RequestMapping("/contentadd")
    public String contentadd() {
        return "content/contentadd";
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("content", this.contentService.getContentById(id));
        return "content/preview";
    }

    @RequestMapping("/show/{cid}")
    public String show(@PathVariable("cid") Integer cid, Model model) {
        model.addAttribute("content", this.contentService.getContentByCid(cid));
        return "content/preview";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("tree", this.catetreeService.getAll());
        return "content/test";
    }
}
