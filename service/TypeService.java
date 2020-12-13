package com.example.demo1.service;

import com.example.demo1.dao.TypeDao;
import com.example.demo1.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {
    @Autowired
    private TypeDao typeDao;

    //添加、更新种类
    public boolean saveTypeService(Type type)
    {
        boolean flag=false;
        try{
            typeDao.save(type);
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("添加/更新种类失败");
            e.printStackTrace();
        }
        return flag;
    }

    //删除种类
    public boolean deleteTypeService(Type type)
    {
        boolean flag=false;
        try{
            typeDao.deleteById(type.getT_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("删除种类失败");
            e.printStackTrace();
        }
        return flag;
    }

    //按照ID查询种类
    public Type selectTypeServiceById(int t_id)
    {
        Type type=new Type();
        try{
            type=typeDao.findById(t_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("查询种类失败");
            e.printStackTrace();
        }
        return type;
    }

    //按照名称查询种类
    public Type selectTypeServiceByName(String t_name)
    {
        Type type=new Type();
        try{
            type=typeDao.findByT_name(t_name);
        }
        catch (Exception e)
        {
            System.out.println("查询种类失败");
            e.printStackTrace();
        }
        return type;
    }
}
