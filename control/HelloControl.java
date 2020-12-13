package com.example.demo1.control;

import com.example.demo1.dao.CartDao;
import com.example.demo1.dao.GoodsDao;
import com.example.demo1.entity.Goods;
import com.example.demo1.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
//@RestController
public class HelloControl {

    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    CartDao cartDao;
   // FavorDao favorDao;
    @Value("${filePath}")
    private String filePath;

    @GetMapping("/")
    public String maincontrol(Model model){
        //String a="{\"name\":\"wangwu\",\"age\":30}";
       // JSONObject object=JSONObject.parseObject(a);
        //model.addAttribute("json1",object);
       // model.addAttribute("json1",a);
      /*  Cart cart = null;
        Cart cart1=null;
        Cart cart2=new Cart();
        cart2=null;
        if(cart2!=null)
            System.out.println("not null");
        else
            System.out.println("is null");

       */
        /*cart2.setS_id(2345);
        try {
            cart=cartDao.findByU_id("5");
        }
        catch (Exception e)
        {
            System.out.println("查询购物车失败");
            e.printStackTrace();
        }
        cart1=cart2;
        System.out.println(cart1.getS_id());
        System.out.println(cart2.getS_id());
        if(cart1!=null) {
            System.out.println("not null");
            System.out.println(cart1.getS_id());
        }
        else
            System.out.println("is null");

        if(cart!=null) {
            System.out.println("not null");
            System.out.println(cart.getG_sizes());
        }
        else
            System.out.println("is null");

         */
        //System.out.println(cart);
        //System.out.println(cart.getC_id());
        //System.out.println(cart.getS_id());
        //System.out.println(cart.getG_sizes());
        /*JSONObject a=new JSONObject();
        a.put("color","red");
        System.out.println(a);
       // User user=null;
       // user.setU_id("isnulluser");
        //System.out.println(user.getU_id());
        User user1=new User();
        user1.setU_id("isnewuser");
        System.out.println(user1.getU_id());


        String a="[{\"color\":\"red\",\"size\":\"big\"},{\"color\":\"blue\",\"size\":\"small\"}]";
        System.out.println(a);
        JSONArray array=JSONArray.parseArray(a);
        System.out.println(array);

        JSONObject object=array.getJSONObject(1);
        System.out.println(object);
        object.replace("color","yellow");
        System.out.println(object);
        array.set(1,object);
        System.out.println(array);
        String b=array.toJSONString();
        System.out.println(b);


        array.remove(0);
        System.out.println(array);
        array.remove(0);
        if(array.isEmpty())
        {
            System.out.println("is null");
        }
        else
        {
            System.out.println(array);
        }

        String aa=array.toJSONString();
        String aa2=array.toString();
        System.out.println(aa);
        System.out.println(aa2);
        Goods goods=goodsDao.findById(2134).orElse(null);
        String arr=goods.getJtest();
        JSONArray array= JSON.parseArray(arr);
        System.out.println("arr"+arr);
        System.out.println("array"+array);
        System.out.println("array0"+array.getString(0));
        System.out.println("array1"+array.get(1));
        JSONObject array1=array.getJSONObject(0);
        System.out.println("array0:"+array1.get("size"));
        */

       // return "email_phoneTest";
        //return "test1";

        return "log in1";//*********
        //return "Ordertest";
    }

    @RequestMapping("/test")
    public String ltest(){
        return "Ordertest";
    }

    @RequestMapping("/logout1")
    public String logout1(HttpServletRequest request){
        request.getSession().invalidate();
        return "log in1";
    }
    @RequestMapping("/logout2")
    public String logout2(HttpServletRequest request){
        request.getSession().invalidate();
        return "log in2";
    }
    @RequestMapping("/logout3")
    public String logout3(HttpServletRequest request){
        request.getSession().invalidate();
        return "log in3";
    }
    @RequestMapping("/main1")
    public String ltestm1(Model model){
        List<Goods> goodsList;
        goodsList = goodsService.selectGoodsServiceByState(1);
        List<Goods> goodsList1=new ArrayList<>();
        goodsList1.add(goodsList.get(0));
        goodsList1.add(goodsList.get(1));
        goodsList1.add(goodsList.get(2));
        model.addAttribute("goodslist",goodsList1);
        return "main";//任何身份都能登录用户界面
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "log in";
    }
    @RequestMapping("/main2")
    public String ltestm2(){
        return "sellermain";//任何身份都能登录用户界面
    }
    @RequestMapping("/seller")
    public String log2(){
        return "log in2";
    }

    @RequestMapping("/manage")
    public String log3(){
        return "log in3";
    }


    @RequestMapping("/seller3")
    public String seller3(){
        return "Seller3";
    }

    @RequestMapping("/addgoods")
    public String inpage(){
        return "addgoods";
    }

    @GetMapping("/ccc")
    public String pccc(HttpServletRequest request) {
        String aa[]= request.getParameterValues("Fruit");
        System.out.println(Arrays.toString(aa));
        return "Register13343";
    }
    @GetMapping("/register")
    public String pagecontrol_register() {
        return "Register1";
    }
    @RequestMapping("/user1")
    public String pagecontrol_user1()
    {
        return "user";
    }


    @RequestMapping(value = "/ttest")
    public void downLoadFile(String id, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");//注意text/html，和application/html
        res.getWriter().print("<html><body><script type='text/javascript'>alert('无权下载文件！');</script></body></html>");
        res.getWriter().close();
        }
}
