package com.example.demo1.service;

import com.example.demo1.dao.FavorDao;
import com.example.demo1.entity.Favor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FavorService {
    @Autowired
    private FavorDao favorDao;

    //添加收藏
    public boolean insertFavorService(Favor favor){
        boolean flag=false;
        try{
            favorDao.save(favor);
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("添加收藏失败");
            e.printStackTrace();
        }
        return flag;
    }

    //取消收藏
    public boolean deleteFavorService(Favor favor){
        boolean flag=false;
        try {
            favorDao.deleteById(favor.getF_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("取消收藏失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户ID查询某用户所有收藏
    public List<Favor> selectFavorService(String u_id)
    {
        List<Favor> f=null;
        try{
            f=favorDao.findByU_id(u_id);
        }
        catch (Exception e)
        {
            System.out.println("查询所有收藏失败");
            e.printStackTrace();
        }
        return f;
    }
}
