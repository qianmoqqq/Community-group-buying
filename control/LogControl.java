package com.example.demo1.control;

import com.example.demo1.entity.Goods;
import com.example.demo1.entity.Seller;
import com.example.demo1.entity.User;
import com.example.demo1.service.GoodsService;
import com.example.demo1.service.SellerService;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/login")
public class LogControl {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellerService sellerService;

    @RequestMapping("/loginbypwd")//用户使用密码登录形式，用户ID为英文与数字组合，登录时可用ID、邮箱、手机号登录
    public String loginControl(HttpServletRequest request, Model model) {
        boolean flag = true;
        User user = null;
        String logid = request.getParameter("logid");//传入的登录ID
        String logpwd = request.getParameter("logpwd");//传入的密码
        int logrole = Integer.parseInt(request.getParameter("logrole"));//传入的网页标识
        String idEx = "^[a-zA-Z]{1}([a-zA-Z0-9]|[._+]){7,19}$";//ID正则表达式，英文与数字组合
        String emailEx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";//邮箱正则表达式
        String phoneEx = "^\\d{11}$";//手机号正则表达式（11位数字）
        Pattern pattern = Pattern.compile(idEx);
        Matcher matcher = pattern.matcher(logid);
        if (matcher.find()) {
            System.out.println("1");
            user = userService.selectUserServiceById(logid);
        } else {
            System.out.println("2");
            pattern = Pattern.compile(emailEx);
            matcher = pattern.matcher(logid);
            if (matcher.find())
                user = userService.selectUserServiceByEmail(logid);
            else {
                pattern = Pattern.compile(phoneEx);
                matcher = pattern.matcher(logid);
                if (matcher.find())
                    user = userService.selectUserServiceByPhone(logid);
                else
                    flag = false;
            }
        }
        if (user != null) {
            if (user.getU_pwd().equals(logpwd)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if (logrole == 1)//登录用户界面
                {
                    List<Goods> goodsList;
                    goodsList = goodsService.selectGoodsServiceByState(1);
                    List<Goods> goodsList1=new ArrayList<>();
                    goodsList1.add(goodsList.get(0));
                    goodsList1.add(goodsList.get(1));
                    goodsList1.add(goodsList.get(2));
                    model.addAttribute("goodslist",goodsList1);
                    return "main";//任何身份都能登录用户界面
                } else {
                    if (user.getU_role() == logrole) {
                        if (logrole == 2) {
                            Seller seller = sellerService.selectSellerServiceByU_d(logid);
                            session.setAttribute("seller",seller);
                            return "sellermain";//商家
                        }
                        else
                            return "goodsmanagement";//管理员
                    } else
                        return "errorpage";//身份错误
                }
            } else {
                String epage="log in"+logrole;
                model.addAttribute("errors","登录失败：密码错误");
                return epage;//密码错误
            }
        } else {
            String epage = "log in" + logrole;
            model.addAttribute("errors", "登录失败：用户不存在");
            return epage;//密码错误
        }
    }

    @RequestMapping("/loginbyphone")//通过手机验证码登录
    public String loginControlbyphone(HttpServletRequest request) {
        String u_phone = request.getParameter("u_phone");
        int logrole = Integer.parseInt(request.getParameter("logrole"));
        User user = userService.selectUserServiceByPhone(u_phone);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if (logrole == 1)//登录用户界面
            {
                return "page1";//任何身份都能登录用户界面
            } else {
                if (user.getU_role() == logrole) {
                    if (logrole == 2)
                        return "page2";//商家
                    else
                        return "page3";//管理员
                } else
                    return "errorpage";//身份错误
            }
        } else
            return "errorpage";//用户不存在
    }


}
