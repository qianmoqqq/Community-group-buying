package com.example.demo1.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.entity.*;
import com.example.demo1.service.GoodsService;
import com.example.demo1.util.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/goods")
public class GoodsControl {
    @Autowired
    private GoodsService goodsService;
    @Value("${filePath}")
    private String filePath;

    private void uploadFile(byte[] filebyte, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(filebyte);
        out.flush();
        out.close();
//        System.out.println("targetFile.getAbsolutePath()!>>>"+targetFile.getAbsolutePath());
    }
    @RequestMapping("/insert")//点击添加，调用。
    public String insertGoodsControl(@RequestParam("file") MultipartFile file,HttpServletRequest request,Model model) {
        Goods goods=new Goods();
        System.out.println("insert");
        String url= WebConfig.Path+file.getOriginalFilename();
        try {
            uploadFile(file.getBytes(), file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败!");
           // return "uploading failure";
        }
        //model.addAttribute("url",url);//放入goods表单中提交保存
        System.out.println("文件上传成功!>>>"+url);
       // return "uploadphotos";
        goods.setG_pic(url);
        Seller seller=(Seller)request.getSession().getAttribute("seller");
        goods.setS_id(seller.getS_id());
        goods.setG_name(request.getParameter("g_name"));
        goods.setG_intro(request.getParameter("g_intro"));
        goods.setG_state(1);
        goods.setG_rating(4);
        double maxp=0;
        double minp=0;
        List<Sizes_g>sizes_gs=new ArrayList<>();
        for(int i=1;i<4;i++) {
            String numi="g_num"+i;
            String pricei="g_price"+i;
            String sizei="g_size"+i;
            Sizes_g sizes_g = new Sizes_g();
            sizes_g.setG_numa(Integer.parseInt(request.getParameter(numi)));
            sizes_g.setG_pica(url);
            sizes_g.setG_pricea(Double.parseDouble(request.getParameter(pricei)));
            double ppp=Double.parseDouble(request.getParameter(pricei));
            if(ppp>maxp)
                maxp=ppp;
            if(ppp<minp)
                minp=ppp;
            sizes_g.setG_sizea(request.getParameter(sizei));
            sizes_gs.add(sizes_g);
        }
        goods.setG_lowprice(minp);
        goods.setG_highprice(maxp);
        String sizejson= JSON.toJSONString(sizes_gs);
        goods.setG_size(sizejson);
        JSONArray type=new JSONArray();
        type.add(Integer.parseInt(request.getParameter("g_type")));
        String type1=type.toJSONString();
        goods.setG_type(type1);
        boolean flag = goodsService.addGoodsService(goods);
        if (flag) {
            request.setAttribute("errors","addsuccess");
            return "sellermain";//添加成功，跳转到添加成功页面。
        } else
            return "errorpage";
    }

    @RequestMapping("/shelf")//商品上架
    public String shelfGoodsControl(HttpServletRequest request){
        Goods goods;
        int g_id =  Integer.parseInt(request.getParameter("id"));
        goods = goodsService.selectGoodsServiceById(g_id);
        if (goods != null)
        {
            goods.setG_state(1);
            goodsService.updateGoodsService(goods);
            return "page";//上架成功
        }
        return "errorpage";//未找到商品，未知错误
    }

    @RequestMapping("/update1")//通过商品ID查找商品。
    public  String select_by_idGoodsConctrol1(HttpServletRequest request,Model model){
        int g_id=Integer.parseInt(request.getParameter("g_id"));
        Goods goods;
        goods = goodsService.selectGoodsServiceById(g_id);
        List<JSONObject> sizelist=new ArrayList<>();
        String aa=goods.getG_size();
        JSONArray array=JSONArray.parseArray(aa);
        for(int i=0;i<array.size();i++)
        {
            sizelist.add(i,array.getJSONObject(i));
        }
        if(goods != null){
            model.addAttribute("goods",goods);
            model.addAttribute("sizelist",sizelist);
            return "addgoods.html";//查找成功。
        }else
            return "errorpage";
    }
    @RequestMapping("/update")//修改商品信息后点保存。
    public String updateGoodsControl(Goods goods){
        boolean flag = goodsService.updateGoodsService(goods);
        if(flag){
            return "goods2";//保存成功，返回商品信息页面。
        }else
            return "errorpage";
    }
    @RequestMapping("/findbys_id")//根据商家查找购物车
    public String findbysidControl(HttpServletRequest request, Model model) {
        Seller seller = (Seller) request.getSession().getAttribute("seller");//前端传来用户信息
        int s_id=seller.getS_id();
        List<Goods> goodsList= goodsService.selectGoodsServiceByS_id(s_id);
        request.getSession().setAttribute("goodslist",goodsList);
        //model.addAttribute("cartlist",cartList);
        List<List> arrays=new ArrayList<>();
       // List<Double> sumpricelist=new ArrayList<>();
        for(int i=0;i<goodsList.size();i++)
        {
            double sump=0;
            String aaa=goodsList.get(i).getG_size();
            JSONArray aaa2=JSONArray.parseArray(aaa);
            List<Sizes_g> aa2=new ArrayList<Sizes_g>();
            aa2=aaa2.toJavaList(Sizes_g.class);
            //System.out.println("list:"+aa2.get(0).getG_size());
            arrays.add(i,aa2);
            //sumpricelist.add(i,sump);
        }
        System.out.println(arrays.size());
        request.getSession().setAttribute("arrays",arrays);
        model.addAttribute("arrays",arrays);
        //request.getSession().setAttribute("sumprice",sumprice);
        //request.getSession().setAttribute("sumpricelist",sumpricelist);
       // model.addAttribute("sumprice",sumprice);
        return "seller";
    }

    @RequestMapping("/select_by_id")//通过商品ID查找商品。
    public  String select_by_idGoodsConctrol(HttpServletRequest request,Model model){
        System.out.println("break5");
        int g_id=Integer.parseInt(request.getParameter("g_id"));
        System.out.println(g_id);
        String result=request.getParameter("result");
        if(result!=null&&result!="")
            System.out.println("break6"+result);
            model.addAttribute("result",result);
        Goods goods;
        goods = goodsService.selectGoodsServiceById(g_id);
        List<JSONObject> sizelist=new ArrayList<>();
        String aa=goods.getG_size();
        JSONArray array=JSONArray.parseArray(aa);
        for(int i=0;i<array.size();i++)
        {
            sizelist.add(i,array.getJSONObject(i));
        }
        if(goods != null){
            model.addAttribute("goods",goods);
            model.addAttribute("sizelist",sizelist);
            return "Goods";//查找成功。
        }else
            return "errorpage";
    }

    @RequestMapping("/select_by_type")//通过商品类型查找商品。//需改
    public  String select_by_typeGoodsConctrol(String g_type,Model model) {
        Goods goods;
        goods = goodsService.selectGoodsServiceByType(g_type);
        if (goods != null) {
            model.addAttribute("goods", goods);
            return "goods4";//查找成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/select_by_state")//通过商品状态查找商品。
    public  String select_by_stateGoodsConctrol(int g_state,Model model) {
        List<Goods> goodsList;
        goodsList = goodsService.selectGoodsServiceByState(g_state);
        if (goodsList != null) {
            model.addAttribute("goodslist", goodsList );
            return "goods5";//查找成功。
        } else
            return "errorpage";

    }
    @RequestMapping("/select_by_price")//通过商品价格查找商品。
    public  String select_by_priceGoodsConctrol(int g_price_low,int g_price_high,Model model) {
        Goods goods;
        goods = goodsService.selectGoodsServiceByPrice(g_price_low, g_price_high);
        if (goods != null) {
            model.addAttribute("goods", goods);
            return "goods6";//查找成功。
        } else
            return "errorpage";
    }

    @RequestMapping("/select_By_Group")//组合查询搜索
    public String selectByGroupControl(HttpServletRequest request,Model model){
        String type1=request.getParameter("type1");//水果、蔬菜、肉类、海鲜、蛋类、图书、日用品、其他
        String searchmessage;
        if(type1.equals("1"))
            searchmessage="水果";
        else if(type1.equals("2"))
            searchmessage="蔬菜";
        else if(type1.equals("3"))
            searchmessage="肉类";
        else if(type1.equals("4"))
            searchmessage="海鲜";
        else if(type1.equals("5"))
            searchmessage="蛋类";
        else if(type1.equals("6"))
            searchmessage="图书";
        else if(type1.equals("7"))
            searchmessage="日用品";
        else
            searchmessage=request.getParameter("searchmessage");
        String g_type0=request.getParameter("g_type");

        String searchmessage0=searchmessage;
        String pa=request.getParameter("price_low");
        if(pa==null)
            pa="0";
        double price_low= Integer.parseInt(pa);
        String pb=request.getParameter("price_high");
        double price_high;
        if (pb==null)
        {
            price_high=9999999;
        }
        else
            price_high=Double.parseDouble(pb);
        String sa=request.getParameter("sort_type");
        if(sa==null)
            sa="0";
        int sort_type= Integer.parseInt(sa);
        String g_type;
        if(g_type0!=null)
            g_type="%"+g_type0+"%";
        else
            g_type="%%";
        if(searchmessage!=null)
            searchmessage="%"+searchmessage+"%";
        else
            searchmessage="%%";

        List<Goods> goodsList;
        System.out.println(searchmessage);
        System.out.println(g_type);
        goodsList=goodsService.selectGoodsServiceByGroup(searchmessage, g_type, price_low, price_high);
        if(goodsList!=null)
        {
            goodsList=goodsService.selectGoodsServiceByGroup("%%","%%",0,99999);
        }
            System.out.println("list is not null");
            System.out.println(goodsList.size());
            System.out.println(goodsList.get(0).getG_name());
            if(sort_type==1)
            {//价格低到高（正序）
                goodsList.sort(Comparator.comparingDouble(Goods::getG_lowprice));
            }
            else if(sort_type==2)
            {//价格高到低（倒序）
                goodsList.sort(Comparator.comparingDouble(Goods::getG_lowprice).reversed());
            }
            else if(sort_type==3)
            {//信用、评分高到低
                goodsList.sort(Comparator.comparingDouble(Goods::getG_rating).reversed());
            }

        model.addAttribute("searchmessage",searchmessage0);
        model.addAttribute("g_type",g_type0);
        model.addAttribute("price_low",price_low);
        model.addAttribute("price_high",price_high);
        model.addAttribute("sort_type",sort_type);
        model.addAttribute("goodslist",goodsList);
        if(goodsList.size()==0)
            return "errorpage";
        return "search";
    }

    @RequestMapping("/downshelf")//商品下架
    public String downshelfGoodsControl(HttpServletRequest request){
        Goods goods;
        int g_id =  Integer.parseInt(request.getParameter("id"));
        goods = goodsService.selectGoodsServiceById(g_id);
        if (goods != null)
        {
            goods.setG_state(0);
            goodsService.updateGoodsService(goods);
            return "page";//下架成功
        }
        return "errorpage";//未找到商品，未知错误
    }

    @RequestMapping("/delete")//删除商品。
    public String deleteGoodsControl(Goods goods) {
        boolean flag = goodsService.deleteGoodsService(goods);
        if (flag)
            return "goods7";//删除成功。
        else
            return "errorpage";
    }

    @RequestMapping("/select_by_id2")//通过商品ID查找商品。
    public  String select_by_id2GoodsConctrol(HttpServletRequest request,Model model){
        System.out.println("break5");
        int g_id=Integer.parseInt(request.getParameter("g_id"));
        System.out.println(g_id);
        String result=request.getParameter("result");
        if(result!=null&&result!="")
            System.out.println("break6"+result);
        model.addAttribute("result",result);
        Goods goods;
        goods = goodsService.selectGoodsServiceById(g_id);
        List<JSONObject> sizelist=new ArrayList<>();
        String aa=goods.getG_size();
        JSONArray array=JSONArray.parseArray(aa);
        for(int i=0;i<array.size();i++)
        {
            sizelist.add(i,array.getJSONObject(i));
        }
        if(goods != null){
            model.addAttribute("goods",goods);
            model.addAttribute("sizelist",sizelist);
            return "goodsmanagement";//查找成功。
        }else
            return "errorpage";
    }

}
