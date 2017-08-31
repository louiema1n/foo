package com.lm.controller;

import com.lm.domain.Tmp;
import com.lm.service.TmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Louie on 2017-07-25.
 */
@Controller
@RequestMapping("/tmp")
public class TmpController {

    @Autowired
    private TmpService tmpService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(String content) {
//        String str;
//        str = content.replace("[颜色]", "<div><input type=\"text\" /></div>");
//        str = str.replace("[形态]", "<div><input type=\"text\" /></div>");
//        str = str.replace("[数量]", "<div><input type=\"text\" /></div>");
//        str = str.replace("[直径]", "<div><input type=\"text\" /></div>");
//        str = str.replace("[体积]", "<div><input type=\"text\" /></div>");
//        str = str.replace("[常用]", "<div><input type=\"text\" /></div>");
        this.tmpService.add(content);
        return "test/tmp";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Tmp> all() {
        return this.tmpService.getAll();
    }
}
