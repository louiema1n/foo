package com.lm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 电子邮件发送
 * Created by Louie on 2017-08-23.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(from);
        smm.setTo(to);
        smm.setSubject(subject);
        smm.setText(content);
        // 发送
        sender.send(smm);
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        // 创建一个类型消息对象
        MimeMessage message = sender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            // 添加附件
            FileSystemResource fsr = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            helper.addAttachment(MimeUtility.encodeText(fileName, "GBK", null), fsr);

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
