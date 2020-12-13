package com.example.demo1.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

//import com..entity.Mail;
@RestController
@RequestMapping(value = "/api/mail", method = RequestMethod.POST)
public class EmailControl {

    @Autowired
    TemplateEngine templateEngine;
    private final Logger LOG = LoggerFactory.getLogger(EmailControl.class);
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private JavaMailSender mailSender;

    /*
     * 发送普通邮件
     */
    @RequestMapping("/sendMail")
    public int sendMail(@RequestParam("username") String receiver) {
        int code = 1234;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(receiver);
        message.setSubject("Test");
        message.setText("验证码为：" + code);
        mailSender.send(message);
//		model.addAttribute("code",code);
        LOG.info("发送成功!");
        return code;
    }
}
