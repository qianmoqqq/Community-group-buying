package com.example.demo1.control;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.demo1.util.SmsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: SMSController
 * @Description: 短信发送测试
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/api")
public class SMSController {
    private static final Logger LOG = LoggerFactory.getLogger(SMSController.class);

    /*
     * 发送短信验证
     */
    @PostMapping("/sendSMS")
    public String sendSMS(@RequestParam("username") String phone) {
        // 验证码（指定长度的随机数）
        String code = String.valueOf((int) (Math.random() * 9000 + 1000));
        String thecode=null;
        System.out.println(code);
        //String code = "1234";
        String TemplateParam = "{\"code\":\"" + code + "\"}";
        try {
            SendSmsResponse response = SmsTool.sendSms(phone, TemplateParam);
            if (response.getCode().equals("OK")) {
                //request.setAttribute("thecode", code);
                thecode=code;
            } else {
                System.out.println("response值=" + response.getCode() + ":" + response.getMessage() + "," + "当前类=SMSController.sendSMS()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //model.addAttribute("thecode",code);
        return thecode;

    }
}
