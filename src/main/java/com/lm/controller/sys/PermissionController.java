package com.lm.controller.sys;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lm.domain.PageResult;
import com.lm.service.sys.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限控制器
 * Created by Louie on 2017-09-03.
 */
@RequestMapping("/per")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PageResult all(@RequestParam("offset") int offset,
                          @RequestParam("limit") int limit,
                          @RequestParam("order") String order) {
        PageResult pageResult = new PageResult();
        Page page = PageHelper.offsetPage(offset, limit, true);
        pageResult.setRows(this.permissionService.getAll());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
}
