package com.lm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lm.domain.PageResult;
import com.lm.domain.shiro.User;
import com.lm.service.UserService;
import com.lm.utils.MD5PwdUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 直接查询所有参与排班user
     * @return
     */
    @RequestMapping(value = "/schall", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllSch() {
        return this.userService.queryAllSch();
    }

    /**
     * 分页查询所有user
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public PageResult getAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("order") String order,
            @RequestParam("limit") Integer limit) {
        PageResult pageResult = new PageResult();
        Page page = PageHelper.offsetPage(offset, limit, true);
        pageResult.setRows(this.userService.queryAll());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/add")
    @RequiresPermissions("user:add")//权限管理;
    public String userInfoAdd(){
        return "index";
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public String resetPwd(User user) {
        // 加密
        User newUser = MD5PwdUtil.md52PasswordByUser(user);
        this.userService.updAccountByUser(user);
        return "redirect:/user";
    }
}
