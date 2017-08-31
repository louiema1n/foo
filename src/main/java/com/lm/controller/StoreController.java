package com.lm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lm.domain.PageResult;
import com.lm.domain.Store;
import com.lm.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Louie on 2017-08-12.
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    /**
     * 获取所有store
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public PageResult all(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam(value = "search", defaultValue = "") String search) {
        Page<Object> page = PageHelper.startPage(offset + 1, limit, true);
        PageResult pageResult = new PageResult();
        pageResult.setRows(this.storeService.getAll(search));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 获取可用store
     * @return
     */
    @RequestMapping(value = "/able", method = RequestMethod.GET)
    @ResponseBody
    public PageResult able(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam(value = "search", defaultValue = "") String search) {
        Page<Object> page = PageHelper.startPage(offset + 1, limit, true);
        PageResult pageResult = new PageResult();
        pageResult.setRows(this.storeService.getAble(search));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 使用/补充
     * @param id
     * @param num
     * @return
     */
    @RequestMapping(value = "/{key}/{id}/{num}", method = RequestMethod.POST)
    @ResponseBody
    public String opr(@PathVariable("id") long id, @PathVariable("num") long num, @PathVariable("key") String key) {
        Store store = this.storeService.getById(id);
        long safeCount = store.getSafeCount();
        // 当前库存
        long rest = store.getTotal() - store.getUsed();
        if (key.equals("use")) {
            // 使用
            store.setUsed(store.getUsed() + num);
            rest = rest - num;
        } else if (key.equals("add")) {
            // 当前库存 = 剩余库存 + num
            long tmp = store.getTotal() - store.getUsed() + num;
            store.setTotal(tmp);
            store.setUsed(0);
            store.setAfterAddTime(new Timestamp(new Date().getTime()));
            rest = rest + num;
        }
        // 设置state
        if (rest > (safeCount + safeCount * 0.2)) {
            // 库存安全
            store.setState(2);
        }
        if (rest < (safeCount + safeCount * 0.2) && (rest > safeCount - safeCount * 0.2)) {
            // 需及时请购
            store.setState(3);
        }
        if (rest < (safeCount - safeCount * 0.2)) {
            // 库存危险
            store.setState(4);
        }
        // 更新
        this.storeService.updById(store);
        return "success";
    }

    /**
     * 保存
     * @param store
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Store store) {
        if (store.getId() == 0) {
            // 新增
            store.setCreateTime(new Timestamp(new Date().getTime()));
            store.setCreator("管理员");
            store.setState(1);
            this.storeService.addStore(store);
        } else {
            // 修改
            this.storeService.updById(store);
        }
        return "redirect:/store";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String del(@PathVariable("id") long id) {
        Integer del = this.storeService.delById(id);
        if (del > 0) {
            return "删除成功！";
        }
        return "删除失败，请重试";
    }

    @RequestMapping(value = "/disab/{id}/{key}", method = RequestMethod.POST)
    @ResponseBody
    public String disab(@PathVariable("id") long id, @PathVariable("key") String key) {
        Store store = this.storeService.getById(id);
        if (key.equals("off")) {
            // 停用
            store.setState(0);
        } else {
            // 启用
            store.setState(1);
        }
        this.storeService.updById(store);
        return "success";
    }

}
