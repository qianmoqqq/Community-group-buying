package com.example.demo1.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.entity.*;
import com.example.demo1.service.CartService;
import com.example.demo1.service.GoodsService;
import com.example.demo1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartControl {
    @Autowired
    private CartService cartService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellerService sellerService;
    //json格式数据[{"g_id":*,"g_size":*,"g_num":*,"g_price":*},{***}]g_pic g_name s_name
    //              货号     商品套餐类型   数量      价格
    @RequestMapping("/insert")//加入购物车
    public String insertCartControl(HttpServletRequest request,Model model) {
        boolean flag = false;
        Cart cart;
        User user = (User) request.getSession().getAttribute("user");
        //Goods goods = (Goods) request.getAttribute("goods");

        //前端传来货物信息、用户选择的种类编号信息
        int goods_index = Integer.parseInt(request.getParameter("g_size"));
        int g_id=Integer.parseInt(request.getParameter("g_id"));
        Goods goods=goodsService.selectGoodsServiceById(g_id);
        String u_id = user.getU_id();//用户ID
        int s_id = goods.getS_id();//商家ID
        Seller seller=sellerService.selectSellerServiceById(s_id);
        String s_name=seller.getS_name();
        String g_name=goods.getG_name();
        String g_pic=goods.getG_pic();
        int g_num=Integer.parseInt(request.getParameter("g_num"));//商品数量
        String g_sizes = goods.getG_size();
        JSONArray g_sizes_array = JSONArray.parseArray(g_sizes);
        JSONObject g_sizei_object = g_sizes_array.getJSONObject(goods_index);//商品套餐类型
        cart = cartService.selectCartServiceByU_idAndS_id(u_id, s_id);
        System.out.println("break1");
        if (cart != null) {//有对应购物车
            String g_sizes1 = cart.getG_sizes();
            JSONArray array = JSONArray.parseArray(g_sizes1);//购物车中的商品数组
            for(int i=0;i<array.size();i++) {
                JSONObject a1 = array.getJSONObject(i);
                //商品号与类型相同只加数量，不同则新增一项
                //同
                if (a1.get("g_id").equals(g_id) && a1.get("g_size").equals(g_sizei_object.getString("g_sizea"))) {
                    int g_num0 = a1.getInteger("g_num");
                    g_num+=g_num0;
                    a1.replace("g_num", g_num);
                    //String a1_string=a1.toJSONString();
                    array.set(i, a1);
                    flag=true;
                    break;
                }
            }
            if(!flag)
            {
                System.out.println("break2");
                JSONObject a2 = new JSONObject();//g_id,g_size,g_num,g_price,g_pic,g_name
                a2.put("g_id", g_id);
                a2.put("g_size",g_sizei_object.getString("g_sizea"));
                a2.put("g_num",g_num);
                a2.put("g_price",g_sizei_object.getDoubleValue("g_pricea"));
                a2.put("g_pic",g_pic);
                a2.put("g_name",g_name);
                array.add(a2);
            }
            String g_sizes_final=array.toJSONString();
            System.out.println("break3"+g_sizes_final);
            cart.setG_sizes(g_sizes_final);
        }
        else//没有对应购物车
        {
            System.out.println("break4");
            cart=new Cart();
            cart.setS_id(s_id);
            cart.setS_name(s_name);
            cart.setU_id(u_id);
            JSONObject a3 = new JSONObject();//g_id,g_size,g_num
            a3.put("g_id", g_id);
            a3.put("g_size",g_sizei_object.getString("g_sizea"));
            a3.put("g_num",g_num);
            a3.put("g_price",g_sizei_object.getDoubleValue("g_pricea"));
            a3.put("g_pic",g_pic);
            a3.put("g_name",g_name);
            JSONArray array_new=new JSONArray();
            array_new.add(a3);
            String g_sizes_final=array_new.toJSONString();
            cart.setG_sizes(g_sizes_final);
        }
        flag=cartService.addCartService(cart);
        String result;
        if(flag) {
            result="addsuccess";
            model.addAttribute("result", "添加购物车成功");
        }
        else {
            result="addfail";
            model.addAttribute("result", "添加购物车失败");
        }
        model.addAttribute("g_id",g_id);
        return "redirect:/goods/select_by_id?g_id="+g_id+"&result="+result;
    }

    @RequestMapping("/find")//根据用户查找购物车
    public String findCartControl(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");//前端传来用户信息
        double sumprice=0;
        String u_id = user.getU_id();//用户ID
        List<Cart> cartList= cartService.findCartServiceByU_ID(u_id);
        request.getSession().setAttribute("cartlist",cartList);
        model.addAttribute("cartlist",cartList);
        List<List> arrays=new ArrayList<>();
        List<Double> sumpricelist=new ArrayList<>();
        for(int i=0;i<cartList.size();i++)
        {
            double sump=0;
            String aaa=cartList.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            for(int j=0;j<aa2.size();j++)
            {
                sump+=aa2.get(j).getG_price()*aa2.get(j).getG_num();
                sumprice+=aa2.get(j).getG_price()*aa2.get(j).getG_num();
            }
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays.add(i,aa2);
            sumpricelist.add(i,sump);
        }
        System.out.println(arrays.size());
        request.getSession().setAttribute("arrays",arrays);
        model.addAttribute("arrays",arrays);
        request.getSession().setAttribute("sumprice",sumprice);
        request.getSession().setAttribute("sumpricelist",sumpricelist);
        model.addAttribute("sumprice",sumprice);
        return "Cart";
    }

    @RequestMapping("/delete")//移除购物车
    public boolean deleteCartControl(HttpServletRequest request){
        boolean flag=false;
        Goods goods = (Goods) request.getAttribute("goods");
        int goods_index = Integer.parseInt(request.getParameter("goods_index"));
        int c_id=Integer.parseInt(request.getParameter("c_id"));//返回购物车id
        int g_num=Integer.parseInt(request.getParameter("g_num"));//商品数量
        int g_id = goods.getG_id();//商品ID
        String g_sizes = goods.getG_size();
        JSONArray g_sizes_array = JSONArray.parseArray(g_sizes);
        JSONObject g_sizes_object =  g_sizes_array.getJSONObject(goods_index);
        Cart cart=cartService.findCartServiceByC_ID(c_id);//根据购物车id查找购物车
        if (cart != null)//有对应购物车
        {
            String g_sizes1 = cart.getG_sizes();
            JSONArray g_sizes_array1 = JSONArray.parseArray(g_sizes1);//购物车中的商品数组
            int i = 0;
            while (g_sizes_array1.get(i) != null) {
                JSONObject a1 = g_sizes_array1.getJSONObject(i);//商品号与类型必然相同，只改变数量
                if (a1.get("g_id").equals(g_id) && a1.get("g_size").equals(g_sizes_object.getString("g_sizea"))) {
                    int g_num0 = a1.getInteger("g_num");
                    g_num0-=g_num;
                    if (g_num0 <= 0)
                    {
                        g_sizes_array1.remove(a1);
                        if (g_sizes_array1.isEmpty())
                            flag=cartService.deleteCartService(cart);
                        else
                        {
                            String g_sizes_final=g_sizes_array1.toJSONString();
                            cart.setG_sizes(g_sizes_final);
                            flag=cartService.addCartService(cart);
                        }
                    }
                    else
                    {
                        a1.replace("g_num", g_num0);
                        g_sizes_array1.set(i, a1);
                        String g_sizes_final=g_sizes_array1.toJSONString();
                        cart.setG_sizes(g_sizes_final);
                        flag=cartService.addCartService(cart);
                    }
                    break;
                }
            }
        }
        return flag;
    }
}
