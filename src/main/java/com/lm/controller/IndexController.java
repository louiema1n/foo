package com.lm.controller;

import com.lm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Louie on 2017-06-26.
 */
@Controller
public class IndexController {

    @Autowired
    private FileService fileService;

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
}
