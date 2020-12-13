package com.example.demo1.service;

import com.example.demo1.dao.CartDao;
import com.example.demo1.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;


    //添加购物车到数据库&更新购物车
    public boolean addCartService(Cart cart){
        boolean flag=false;
        try{
            cartDao.save(cart);
            flag=true;
        }
        catch (Exception e){
            System.out.println("添加购物车失败");
            e.printStackTrace();
        }
        return flag;
    }

    //从购物车中删除
    public boolean deleteCartService(Cart cart){
        boolean flag=false;
        try{
            cartDao.deleteById(cart.getC_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("删除购物车失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据购物车ID查询购物车
    public Cart findCartServiceByC_ID(int c_id){
        Cart cart = null;
        try {
            cart = cartDao.findById(c_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("查询购物车失败");
            e.printStackTrace();
        }
        return cart;
    }

    //根据用户ID查询购物车
    public List<Cart> findCartServiceByU_ID(String u_id){
        List<Cart> cartList=new ArrayList<>();
        try {
            cartList = cartDao.findByU_id(u_id);
        }
        catch (Exception e)
        {
            System.out.println("查询购物车失败");
            e.printStackTrace();
        }
        return cartList;
    }

    //根据商家ID查询购物车
    public Cart findCartServiceByS_ID(int s_id){
        Cart cart = null;
        try {
            cart = cartDao.findByS_id(s_id);
        }
        catch (Exception e)
        {
            System.out.println("查询购物车失败");
            e.printStackTrace();
        }
        return cart;
    }


    public Cart selectCartServiceByU_idAndS_id(String u_id,int s_id){
        Cart cart=null;
        try{
            cart=cartDao.findByU_idAndS_id(u_id, s_id);
        }
        catch (Exception e) {
            System.out.println("按照用户与卖家查询购物车失败");
            e.printStackTrace();
        }
        return cart;
    }
}