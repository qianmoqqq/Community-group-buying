package com.example.demo1.service;

import com.example.demo1.dao.OrderDao;
import com.example.demo1.entity.Myorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    //根据订单号查询订单
    public Myorder selectOrderService(int o_id)
    {
        Myorder myorder = new Myorder();
        try{
            myorder =orderDao.findById(o_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("按订单号查询订单失败");
            e.printStackTrace();
        }
        return myorder;
    }

    //根据用户号查询订单
    public List<Myorder> selectOrderByU_idService(String u_id)
    {
        List<Myorder> myorder =new ArrayList<>();
        try{
            myorder =orderDao.findByU_id(u_id);
        }
        catch (Exception e)
        {
            System.out.println("按用户查询订单失败");
            e.printStackTrace();
        }
        return myorder;
    }

    //根据用户号与所需订单状态查询订单
    public List<Myorder> selectOrderByU_idAndO_stateService(String u_id,int o_state)
    {
        List<Myorder> myorder =new ArrayList<>();
        try{
            myorder =orderDao.findByU_idAndO_state(u_id,o_state);
        }
        catch (Exception e)
        {
            System.out.println("按用户与状态查询订单失败");
            e.printStackTrace();
        }
        return myorder;
    }

    //根据商家号与所需订单状态查询订单
    public List<Myorder> selectOrderByS_idAndO_stateService(int s_id,int o_state)
    {
        List<Myorder> myorder =new ArrayList<>();
        try{
            myorder =orderDao.findByS_idAndO_state(s_id,o_state);
        }
        catch (Exception e)
        {
            System.out.println("按商家与状态查询订单失败");
            e.printStackTrace();
        }
        return myorder;
    }

    //添加订单到数据库
    public Myorder addOrderService(Myorder myorder)
    {
        Myorder myorder1 =null;
        try{
            myorder1 =orderDao.saveAndFlush(myorder);
        }
        catch (Exception e){
            System.out.println("添加订单失败");
            e.printStackTrace();
        }
        return myorder1;
    }

    //修改订单信息（备注、地址）
    public boolean changeOrderService(Myorder myorder)
    {
        boolean flag=false;
        try
        {
            Myorder myorder1;
            myorder1=orderDao.findById(myorder.getO_id()).orElse(null);
            if(myorder1!=null)
            {
                myorder1.setO_text(myorder.getO_text());
                myorder1.setO_address(myorder.getO_address());
                orderDao.save(myorder1);
                flag = true;
            }
        }
        catch (Exception e){
            System.out.println("修改订单信息失败");
            e.printStackTrace();
        }
        return flag;
    }

    //修改订单状态，支付成功或取消订单
    public boolean updateOrderService(Myorder myorder)
    {
        boolean flag=false;
        try{
            Myorder myorder1;
            myorder1=orderDao.findById(myorder.getO_id()).orElse(null);
            if(myorder1!=null) {
                myorder1.setO_state(myorder.getO_state());
                orderDao.save(myorder1);
                flag = true;
            }
        }
        catch (Exception e){
            System.out.println("修改订单状态失败");
            e.printStackTrace();
        }
        return flag;
    }


    //删除订单
    public boolean deleteOrderService(Myorder myorder)
    {
        boolean flag=false;
        try{
            orderDao.deleteById(myorder.getO_id());
            flag=true;
        }
        catch (Exception e){
            System.out.println("删除订单状态失败");
            e.printStackTrace();
        }
        return flag;
    }
}
