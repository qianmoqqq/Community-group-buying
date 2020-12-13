package com.example.demo1.control;

import com.example.demo1.entity.Seller;
import com.example.demo1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/seller", method = RequestMethod.POST)
public class SellerControl {
    @Autowired
    private SellerService sellerService;

    @RequestMapping("/insert")//点击添加，调用。
    public String insertSellerControl(Seller seller) {
        boolean flag = sellerService.addSellerService(seller);
        if (flag) {
            return "seller1";//添加成功，跳转到添加成功页面。
        } else
            return "errorpage";
    }

    @RequestMapping("/update")//修改商家信息后点保存。
    public String updateSellerControl(HttpServletRequest request,Model model) {
        String s_name=request.getParameter("s_name");
        String s_phone=request.getParameter("s_phone");
        String s_addr=request.getParameter("s_addr");
        String s_intro=request.getParameter("s_intro");
        Seller seller=(Seller)request.getSession().getAttribute("seller");
        boolean flag = sellerService.updateSellerService(seller);
        if (flag) {
            request.setAttribute("errors","updatesuccess");
            return "sellermain";//保存成功，返回商家信息页面。
        } else
            return "errorpage";
    }

    @RequestMapping("/select_by_id")//通过商家ID查找商家。
    public String select_by_idSellerConctrol(int s_id, Model model) {
        Seller seller;
        seller = sellerService.selectSellerServiceById(s_id);
        if (seller != null) {
            model.addAttribute("seller", seller);
            return "seller3";//查找成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/select_by_name")//通过商家name查找商家。
    public String select_by_nameSellerConctrol(int s_name, Model model) {
        Seller seller;
        seller = sellerService.selectSellerServiceByName(s_name);
        if (seller != null) {
            model.addAttribute("seller", seller);
            return "seller3";//查找成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/delete")//点击删除，调用
    public String deleteSellerControl(Seller seller) {
        boolean flag = sellerService.deleteSellerService(seller);
        if (flag)
            return "seller5";//删除成功。
        else
            return "errorpage";
    }

}
