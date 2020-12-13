package com.example.demo1.control;

import com.example.demo1.entity.Favor;
import com.example.demo1.entity.Goods;
import com.example.demo1.entity.User;
import com.example.demo1.service.FavorService;
import com.example.demo1.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/favor")
public class FavorControl {
    @Autowired
    private FavorService favorService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/insert")//添加收藏
    public String insertFavorControl(Favor favor) {
        boolean flag = favorService.insertFavorService(favor);
        if (flag) {
            return "favor1";//添加成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/delete")//取消收藏。
    public String deleteFavorControl(Favor favor) {
        boolean flag = favorService.deleteFavorService(favor);
        if (flag) {
            return "favor2";//取消成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/select")//根据用户ID查询某用户所有收藏
    public String selectFavorControl(HttpServletRequest request,Model model) {
        List<Favor> favor;
        User user=(User)request.getSession().getAttribute("user");
        favor = favorService.selectFavorService(user.getU_id());
        model.addAttribute("favor", favor);
        List<Goods> goodsList=new ArrayList<>();
        for(int i=0;i<favor.size();i++)
        {
            Goods goods=goodsService.selectGoodsServiceById(favor.get(i).getG_id());
            goodsList.add(i,goods);
        }
        model.addAttribute("goodslist",goodsList);
            return "favor";//查找成功。
    }

}
