package com.lm.controller;

import com.lm.domain.Content;
import com.lm.domain.Store;
import com.lm.service.*;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private TmpService tmpService;

    @Autowired
    private StoreService storeService;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/new")
    public String newIndex() {
        return "indexNew";
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
    public String contentadd(Model model) {
        Content content = new Content();
        model.addAttribute("content", content);
        return "content/contentadd";
    }

    @RequestMapping("/contentadd/{id}")
    public String initEdit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("content", this.contentService.getContentById(id));
        return "content/contentadd";
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("content", this.contentService.getContentById(id));
        return "content/preview";
    }

    @RequestMapping("/show/{cid}")
    @ResponseBody
    public Content show(@PathVariable("cid") Integer cid) {
        return this.contentService.getContentByCid(cid);
    }

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("tree", this.catetreeService.getAll());
        return "content/test";
    }

    @RequestMapping("/view")
    public String view() {
        return "view/view";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("content", this.contentService.getContentById(id));
        return "view/view3";
    }

    @RequestMapping("/view4")
    public String view4() {
        return "view/view4";
    }

    @RequestMapping("/contentedit")
    public String contentedit() {
        return "content/contentedit";
    }

    @RequestMapping("/form")
    public String form() {
        return "content/form";
    }

    @RequestMapping("/tmp")
    public String tmp() {
        return "test/tmp";
    }

    @RequestMapping("/print")
    public String print() {
        return "test/print";
    }

    @RequestMapping("/use")
    public String use(Model model) {
        model.addAttribute("contents", this.tmpService.getAll());
        return "test/use";
    }

    @RequestMapping("/store")
    public String store() {
        return "store/store";
    }

    @RequestMapping("/inAndOut")
    public String inAndOut() {
        return "store/inAndOut";
    }

    @RequestMapping("/storeadd/{id}")
    public String storeadd(@PathVariable("id") long id, Model model) {
        if (id == 0) {
            Store store = new Store();
            model.addAttribute("store", store);
        } else {
            model.addAttribute("store", this.storeService.getById(id));
        }
        return "store/storeadd";
    }

    @RequestMapping("/schedule")
    public String schedule() {
        return "schedule/schedule";
    }

    @RequestMapping("/403")
    public String unauthorized() {
        return "403";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录处理
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        // 登录失败中request中获取shiro处理的异常信息（shiroLoginFailure:就是shiro异常类的全类名.）
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String errMsg = exception;
        /**
         * DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、ExpiredCredentialsException（过期的凭证）
         */
        if (UnknownAccountException.class.getName().equals(exception)) {
            errMsg = "用户名不存在，请重新输入。";
        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
            errMsg = "密码错误！";
        } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
            errMsg = "密码连续错误5次，用户被锁定1分钟。如果继续错误，用户将被永久锁定！";
        } else if (LockedAccountException.class.getName().equals(exception)) {
            errMsg = "该用户已被永久锁定，请联系管理员！";
        } else if (DisabledAccountException.class.getName().equals(exception)) {
            errMsg = "该用户已被停用，请联系管理员！";
        } else if (ExpiredCredentialsException.class.getName().equals(exception)) {
            errMsg = "该用户未激活，请联系管理员！";
        } else {
            errMsg = "服务器繁忙，请联系管理员。";
        }
        model.addAttribute("errMsg", errMsg);
        return "login";
    }

    @RequestMapping("/user")
    public String user() {
        return "user/user";
    }

    @RequestMapping("/role")
    public String role() {
        return "role/role";
    }

    @RequestMapping("/permission")
    public String permission() {
        return "permission/permission";
    }

    @RequestMapping("/tst")
    public String test() {
        return "test/test";
    }
}
