package com.example.demo1.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.entity.*;
import com.example.demo1.service.GoodsService;
import com.example.demo1.service.OrderService;
import com.example.demo1.service.SellerService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/myorder")
public class OrderControl {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellerService sellerService;
//id,size,price,num        id,size,num
    @RequestMapping("/order1")
    public String toorder1(){
        return "Order1";
    }
    @RequestMapping("/insert")//购物车点击结算、下单,跳转到确认订单详情
    public String insertOrderControl(HttpServletRequest request, Model model) {//或考虑通过URL传送信息到这里
        List<Cart> cartList=(List<Cart>)request.getAttribute("cartlist");
        int len=cartList.size();
        List<Myorder> orderList=new ArrayList<Myorder>();
        int i=0;
        while(i<len)
        {
            Cart cart=cartList.get(i);
            Myorder myorder=new Myorder();
            double sum=0;
            myorder.setU_id(cart.getU_id());
            myorder.setS_id(cart.getS_id());
            String g_sizes=cart.getG_sizes();
            JSONArray array=JSONArray.parseArray(g_sizes);
            int j=0;
            while(j<array.size())
            {
                JSONObject object=array.getJSONObject(j);
                int g_id=object.getInteger("g_id");//商品ID
                String g_size=object.getString("g_size");//商品型号
                Goods goods=goodsService.selectGoodsServiceById(g_id);//商品库中搜索商品以查价格
                JSONArray array_goods=JSONArray.parseArray(goods.getG_size());
                for(int k=0;k<array_goods.size();k++)
                {
                    JSONObject object_goods=array_goods.getJSONObject(k);
                    if(object_goods.getString("g_sizea").equals(g_size))//找到型号一致，查价格
                    {
                        double g_price=object_goods.getDoubleValue("g_pricea");
                        object.put("g_price",g_price);
                        sum+=g_price;
                        break;
                    }
                }
                array.set(j,object);//把改完的size放回数组中
            }
            g_sizes=array.toJSONString();
            myorder.setG_sizes(g_sizes);
            myorder.setO_sum(sum);
            myorder.setO_state(1);
            orderList.add(myorder);//放入list中
        }
        model.addAttribute("orderlist",orderList);
        return "Order1";//Order确认详情页
    }

    @RequestMapping("/insertd")//商品页面直接购买下单,跳转至订单确认页面
    public String insertOrderControld(HttpServletRequest request,Model model)
    {
        int g_id= Integer.parseInt(request.getParameter("g_id"));
        Goods goods=goodsService.selectGoodsServiceById(g_id);
        User user=(User)request.getSession().getAttribute("user");
        int g_num=Integer.parseInt(request.getParameter("g_num"));
        String s_name=sellerService.selectSellerServiceById(goods.getS_id()).getS_name();
        JSONObject g_sizes=new JSONObject();//订单的size

        int g_sizeindex=Integer.parseInt(request.getParameter("g_size"));
        String g_sizeaaa=goods.getG_size();
        JSONArray array=JSONArray.parseArray(g_sizeaaa);
        JSONObject g_size_object=array.getJSONObject(g_sizeindex);//传来的所选商品的sizes
        g_sizes.put("g_id",goods.getG_id());
        g_sizes.put("g_size",g_size_object.getString("g_sizea"));
        g_sizes.put("g_num",g_num);
        g_sizes.put("g_price",g_size_object.getDoubleValue("g_pricea"));
        g_sizes.put("g_pic",goods.getG_pic());
        g_sizes.put("g_name",goods.getG_name());
        String g_sizes_string="["+g_sizes.toJSONString()+"]";
        System.out.println("g_sizes"+g_sizes_string);
        double sum=g_sizes.getDoubleValue("g_price")*g_num;


        List<Cart> cartList= new ArrayList<>();
        Cart cart=new Cart();
        cart.setU_id(user.getU_id());
        cart.setS_id(goods.getS_id());
        cart.setS_name(s_name);
        cart.setG_sizes(g_sizes_string);
        cartList.add(cart);
        double sumprice=0;
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

        return "Order1";
    }

    @RequestMapping("/inserttoorder")//订单确认页面点击付款时调用，先添加订单到数据库
    public String insertToOrderControl(HttpServletRequest request,Model model)
    {
        List<Cart>cartList=(List<Cart>)request.getSession().getAttribute("cartlist");
        List<List>arrays=(List<List>)request.getSession().getAttribute("arrays");
        List<Double>sumpricelist=(List<Double>)request.getSession().getAttribute("sumpricelist");
        String o_address=request.getParameter("o_phone")+request.getParameter("o_addr");
        System.out.println(o_address);
        String o_text=request.getParameter("o_text");
        System.out.println(o_text);
        List<Myorder>myorderList=new ArrayList<>();
        for(int i=0;i<cartList.size();i++)
        {
            System.out.println("for"+i);
            Myorder myorder=new Myorder();
            System.out.println("size"+cartList.size());
            System.out.println(cartList.get(i).getS_id());
            myorder.setU_id(cartList.get(i).getU_id());
            myorder.setS_id(cartList.get(i).getS_id());
            myorder.setO_sum(sumpricelist.get(i));
            myorder.setO_state(1);
            myorder.setG_sizes(cartList.get(i).getG_sizes());
            myorder.setO_address(o_address);
            myorder.setO_text("o_text");
            Myorder myorder1=orderService.addOrderService(myorder);
            myorderList.add(i,myorder1);
        }
        model.addAttribute("myorderlist",myorderList);
        return "Ordered";
    }

    @RequestMapping("/cancel")//修改订单状态
    public String cancelOrderControl(HttpServletRequest request) {//订单状态：待支付、已支付（未完成）、已完成、已取消、待售后
        int state=Integer.parseInt(request.getParameter("state"));//传入要修改的订单状态
        int o_id=Integer.parseInt(request.getParameter("o_id"));
        Myorder myorder=orderService.selectOrderService(o_id);
        myorder.setO_state(state);
        boolean flag=orderService.updateOrderService(myorder);
        if (flag)
            return "order3";
        else
            return "errorpage";
    }

    @RequestMapping("/find_by_u_id_and_state")//根据用户ID和状态查询
    public boolean findByU_IdAndStateOrderControl(HttpServletRequest request,Model model)
    {
        boolean flag;
        int state=Integer.parseInt(request.getParameter("state"));//传入要查询的订单状态
        String u_id=request.getParameter("u_id");
        List<Myorder>myorderList=orderService.selectOrderByU_idAndO_stateService(u_id, state);
        if (!myorderList.isEmpty())
        {
            model.addAttribute("myorderlist", myorderList);
            flag = true;
        }
        else
        {
            flag = false;
        }
        return flag;
    }

    @RequestMapping("/find_by_s_id_and_state")//根据商家ID和状态查询
    public boolean findByS_IdAndStateOrderControl(HttpServletRequest request,Model model)
    {
        boolean flag;
        int state=Integer.parseInt(request.getParameter("state"));//传入要查询的订单状态
        int s_id=Integer.parseInt(request.getParameter("s_id"));
        List<Myorder>myorderList=orderService.selectOrderByS_idAndO_stateService(s_id, state);
        if (!myorderList.isEmpty())
        {
            model.addAttribute("myorderlist", myorderList);
            flag = true;
        }
        else
        {
            flag = false;
        }
        return flag;
    }
    @RequestMapping("/find_by_s_id")//根据商家ID
    public String findByS_IdOrderControl(HttpServletRequest request,Model model) {
        System.out.println("find by s_id");
        Seller seller = (Seller) request.getSession().getAttribute("seller");
        int s_id = seller.getS_id();
        List<Myorder> myorderList1 = orderService.selectOrderByS_idAndO_stateService(s_id, 1);
        List<Myorder> myorderList2 = orderService.selectOrderByS_idAndO_stateService(s_id, 2);
        List<Myorder> myorderList3 = orderService.selectOrderByS_idAndO_stateService(s_id, 3);
        List<Myorder> myorderList4 = orderService.selectOrderByS_idAndO_stateService(s_id, 4);
        List<List> arrays1=new ArrayList<>();
        for(int i=0;i<myorderList1.size();i++)
        {
            String aaa=myorderList1.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays1.add(i,aa2);
        }
        List<List> arrays2=new ArrayList<>();
        for(int i=0;i<myorderList2.size();i++)
        {
            String aaa=myorderList2.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays2.add(i,aa2);
        }
        List<List> arrays3=new ArrayList<>();
        for(int i=0;i<myorderList3.size();i++)
        {
            String aaa=myorderList3.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays3.add(i,aa2);
        }
        List<List> arrays4=new ArrayList<>();
        for(int i=0;i<myorderList4.size();i++)
        {
            String aaa=myorderList4.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays4.add(i,aa2);
        }
        model.addAttribute("myorderlist1", myorderList1);
        model.addAttribute("myorderlist2", myorderList2);
        model.addAttribute("myorderlist3", myorderList3);
        model.addAttribute("myorderlist4", myorderList4);
        model.addAttribute("arrays1",arrays1);
        model.addAttribute("arrays2",arrays2);
        model.addAttribute("arrays3",arrays3);
        model.addAttribute("arrays4",arrays4);
        System.out.println("yes");
        return "Ordercon";
    }
    @RequestMapping("/find_by_u_id")//根据商家ID
    public String findByU_IdOrderControl(HttpServletRequest request,Model model) {
        System.out.println("find by u_id");
        User user= (User) request.getSession().getAttribute("user");
        String u_id=user.getU_id();
        List<Myorder> myorderList1 = orderService.selectOrderByU_idService(u_id);
        model.addAttribute("myorderlist1", myorderList1);
        System.out.println("yes");
        List<List> arrays=new ArrayList<>();
        for(int i=0;i<myorderList1.size();i++)
        {
            String aaa=myorderList1.get(i).getG_sizes();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes> aa2=new ArrayList<Sizes>();
            aa2=aaa2.toJavaList(Sizes.class);
            System.out.println("list:"+aa2.get(0).getG_size());
            arrays.add(i,aa2);
        }
        System.out.println(arrays.size());
        model.addAttribute("arrays",arrays);
        return "myorder";
    }

}
