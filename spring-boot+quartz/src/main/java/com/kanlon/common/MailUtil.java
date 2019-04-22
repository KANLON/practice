package com.kanlon.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮箱发送工具类
 *
 * @author zhangcanlong
 * @since 2019年4月22日
 */
@Component
public class MailUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送者
     */
    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 简单的邮件发送，发送纯文本
     *
     * @param to      接受者
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     *
     * @param to      接受者
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        // true表示需要创建一个multipart message
        MimeMessageHelper helper =this.setToAndFromAndSubject(to,subject,content,message);
        mailSender.send(message);
        logger.info("html邮件发送成功");
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     * @param filePath 文件路径
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =this.setToAndFromAndSubject(to,subject,content,message);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }

    /**
     * 发送静态资源的邮件
     *
     * @param to      接受者
     * @param subject 主题
     * @param content 内容
     * @param rscPath 静态资源文件路劲
     * @param rscId   静态资源id
     */
    public void sendInlineResoureceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =this.setToAndFromAndSubject(to,subject,content,message);
        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);
        mailSender.send(message);
        logger.info("嵌入静态资源的邮件已经发送");
    }

    /**
     * 通用的設置 发送人，主题和内容
     * @param to, subject, content, message]
     * @return org.springframework.mail.javamail.MimeMessageHelper
     **/
    private MimeMessageHelper setToAndFromAndSubject(String to, String subject, String content,MimeMessage message) throws MessagingException {
        // true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        return helper;
    }

}