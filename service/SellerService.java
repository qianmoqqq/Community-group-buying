package com.example.demo1.service;

import com.example.demo1.dao.SellerDao;
import com.example.demo1.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    private SellerDao sellerDao;


    //添加商家到数据库
    public boolean addSellerService(Seller seller){
        boolean flag=false;
        try{
            sellerDao.save(seller);
            flag=true;
        }
        catch (Exception e){
            System.out.println("添加商家失败");
            e.printStackTrace();
        }
        return flag;
    }

    //修改商家信息
    public boolean updateSellerService(Seller seller){
        boolean flag=false;
        try{
            sellerDao.save(seller);
            flag=true;
        }
        catch (Exception e){
            System.out.println("修改商家信息失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据商家ID查询某商家信息
    public Seller selectSellerServiceById(int s_id)
    {
        Seller seller = new Seller();
        try{
            seller = sellerDao.findById(s_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("查询商家信息失败");
            e.printStackTrace();
        }
        return seller;
    }

    //根据商家店名查询某商家信息
    public Seller selectSellerServiceByName(int s_name)
    {
        Seller seller = new Seller();
        try{
            seller = sellerDao.findByS_name(s_name);
        }
        catch (Exception e)
        {
            System.out.println("查询商家信息失败");
            e.printStackTrace();
        }
        return seller;
    }

    //将商家从数据库删除
    public boolean deleteSellerService(Seller seller){
        boolean flag=false;
        try{
            sellerDao.deleteById(seller.getS_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("删除商家失败");
            e.printStackTrace();
        }
        return flag;
    }

    public Seller selectSellerServiceByU_d(String logid) {
        Seller seller = new Seller();
        try{
            seller = sellerDao.findByU_id(logid);
        }
        catch (Exception e)
        {
            System.out.println("查询商家信息失败");
            e.printStackTrace();
        }
        return seller;
    }
}
