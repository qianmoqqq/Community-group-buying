package com.example.demo1.control;

import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserControl {
    @Autowired
    private UserService userService;

    @RequestMapping("/register2")
    public String pagecontrol_register2(HttpServletRequest request,Model model) {
        String phone=request.getParameter("userphone");
        User user=userService.selectUserServiceByPhone(phone);
        if(user!=null)
        {
            return "errorpage";//该手机号以及被注册
        }
        else {
            model.addAttribute("phone", phone);
            return "Register2";
        }
    }
    @RequestMapping("/register3")//用户使用ID、手机号、密码进行注册，用户ID为英文与数字组合
    public String insertUserControl(HttpServletRequest request) {
        boolean flag=false;
        User user=new User();
        user.setU_id(request.getParameter("u_id"));
        user.setU_role(1);//( request.getParameter("u_role");
        user.setU_pwd(request.getParameter("u_pwd"));
        user.setU_phone(request.getParameter("u_phone"));
        user.setU_name(request.getParameter("u_name"));
        user.setU_sex(Integer.parseInt(request.getParameter("u_sex")));
        user.setU_state(1);
        user.setU_address(null);
        user.setU_email(null);
        flag=userService.addUserService(user);
        if(flag)
        {
            return "Register3";
        }
        else
            return "error";
    }

    @RequestMapping("/update")//修改个人信息后点击保存
    public String updateUserControl(HttpServletRequest request) {
        boolean flag;
        User user0=(User)request.getSession().getAttribute("user");
        User user=user0;
        String phone = request.getParameter("u_phone");
        //String pwd = request.getParameter("u_pwd");
        String name = request.getParameter("u_name");
        int sex = Integer.parseInt(request.getParameter("u_sex"));
        //user.setU_pwd(pwd);
        user.setU_phone(phone);
        user.setU_name(name);
        user.setU_sex(sex);
        flag = userService.addUserService(user);
        if (flag)
            return "user";//修改成功，返回个人信息
        else
            return "errorpage";//未知错误
    }

    @RequestMapping("/delete")//点击注销，调用
    public String deleteUserControl(String u_id) {
        boolean flag = userService.deleteUserService(userService.selectUserServiceById(u_id));
        if (flag)
            return "mpage";//注销成功，跳转首页
        else
            return "errorpage";
    }

    @RequestMapping("/select_by_id")
    public String select_by_idUserControl(HttpServletRequest request,Model model) {
        User user1;
        String u1_id=request.getParameter("u1_id");
        user1 = userService.selectUserServiceById(u1_id);
        model.addAttribute("user",user1);
        if (user1 != null) {
            return "goodsmanagement";
        }
        else
            return "errorpage";
    }


    @RequestMapping("/select_by_phone")
    public String select_by_phoneUserControl(String u_phone, Model model) {
        User user;
        user = userService.selectUserServiceByPhone(u_phone);
        if (user != null) {
            model.addAttribute("user", user);
            return "page";//查找成功，跳转页面
        } else
            return "errorpage";
    }

    @RequestMapping("/lock")
    public String blockUserControl(String u_id) {//用户状态：未封禁、已封禁
        boolean flag = userService.blockUserServiceById(u_id);
        if (flag)
            return "page";
        else
            return "errorpage";
    }

    @RequestMapping("/unlock")
    public String unblockUserControl(String u_id) {//用户状态：未封禁、已封禁
        boolean flag = userService.unblockUserServiceById(u_id);
        if (flag)
            return "page";
        else
            return "errorpage";
    }
}
