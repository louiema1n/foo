package com.lm.controller;

import com.lm.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Louie on 2017-08-23.
 */
@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @Value("${web.dwd-path}")
    private String path;

    @RequestMapping("/send")
    public void sendMessage() {
        // 邮件接收人
        String to = "gy-luoyu@kingmed.com.cn";
        // 邮件主题
        String subject = "这是一封带附件的邮件";
        // 邮件内容
        String content = "这是测试内容";
        String fileName = "病理技术室08月份考勤表.xls";
        this.mailService.sendAttachmentsMail(to, subject, content, path + fileName);
    }
}
