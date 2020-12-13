package com.example.demo1.service;

import com.example.demo1.dao.GoodsDao;
import com.example.demo1.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;


    //添加商品到数据库
    public boolean addGoodsService(Goods goods){
        boolean flag=false;
        try{
            goodsDao.save(goods);
            flag=true;
        }
        catch (Exception e){
            System.out.println("添加商品失败");
            e.printStackTrace();
        }
        return flag;
    }

    //修改商品信息
    public boolean updateGoodsService(Goods goods){
        boolean flag=false;
        try{
            Goods goods1=goodsDao.findById(goods.getG_id()).orElse(null);
            /*goods1.setG_id(goods.getG_id());//user1.setU_id(user.getU_id());
            goods1.setG_name(goods.getG_name());//user1.setU_name(user.getU_name());
            goods1.setS_id(goods.getS_id());//user1.setU_pwd(user.getU_pwd());
            goods1.setG_type(goods.getG_type());//user1.setU_role(user.getU_role());
            goods1.setG_price(goods.getG_price());//user1.setU_sex(user.getU_sex());
            goods1.setG_para(goods.getG_para());//user1.setU_phone(user.getU_phone());
            goods1.setG_intro(goods.getG_intro());//userDao.save(user1);
            goods1.setG_pic(goods.getG_pic());
            goods1.setG_state(goods.getG_state());
            goods1.setG_size(goods.getG_size());
            goods1.setG_num(goods.getG_num());
            goods1.setG_spic(goods.getG_spic());*/
            goodsDao.save(goods);
            flag=true;
        }
        catch (Exception e){
            System.out.println("修改商品信息失败");
            e.printStackTrace();
        }
        return flag;
    }

    //删除商品信息
    public boolean deleteGoodsService(Goods goods){
        boolean flag=false;
        try {
            goodsDao.deleteById(goods.getG_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("删除商品失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据商品ID查询某商品信息
    public Goods selectGoodsServiceById(int g_id)
    {
        Goods goods = null;
        try{
            goods = goodsDao.findById(g_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goods;
    }

    //根据商品种类查询某商品信息
    public Goods selectGoodsServiceByType(String g_type)
    {
        Goods goods = null;
        try{
            goods = goodsDao.findByG_type(g_type);
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goods;
    }
    //根据商品状态查询某商品信息
    public List<Goods> selectGoodsServiceByState(int g_state)
    {
        List<Goods> goodsList=new ArrayList<>();
        try{
            goodsList = goodsDao.findByG_state(g_state);
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goodsList;
    }

    //根据商品价格查询某商品信息
    public Goods selectGoodsServiceByPrice(int g_price_low, int g_price_high)
    {
        Goods goods = null;
        try{
            goods = goodsDao.findByG_price(g_price_low,g_price_high);
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goods;
    }

    //组合查询
    public List<Goods> selectGoodsServiceByGroup(String g_name, String g_type, double g_price_low, double g_price_high)
    {
        List<Goods> goods_list=null;
        try{
            goods_list = goodsDao.findByGroup(g_name, g_type, g_price_low, g_price_high);
            System.out.println("service:"+goods_list.size());
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goods_list;
    }

    public List<Goods> selectGoodsServiceByName(String g_name)
    {
        List<Goods> goods_list=null;
        try{
            goods_list = goodsDao.findByG_name(g_name);
            System.out.println("service:"+goods_list.size());
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goods_list;
    }

    public List<Goods> selectGoodsServiceByS_id(int s_id) {
        List<Goods> goodsList=null;
        try{
            goodsList = goodsDao.findByS_id(s_id);
            //System.out.println("service:"+goods_list.size());
        }
        catch (Exception e)
        {
            System.out.println("查询商品信息失败");
            e.printStackTrace();
        }
        return goodsList;
    }
}
