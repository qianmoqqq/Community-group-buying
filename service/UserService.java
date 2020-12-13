package com.example.demo1.service;

import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;


    //添加用户到数据库
    public boolean addUserService(User user){
        boolean flag=false;
        try{
            userDao.save(user);
            flag=true;
        }
        catch (Exception e){
            System.out.println("添加用户失败");
            e.printStackTrace();
        }
        return flag;
    }

    //修改用户信息
    public boolean updateUserService(User user){
        boolean flag=false;
        try{
            userDao.save(user);
            flag=true;
        }
        catch (Exception e){
            System.out.println("修改用户信息失败");
            e.printStackTrace();
        }
        return flag;
    }

    //将用户从数据库删除
    public boolean deleteUserService(User user){
        boolean flag=false;
        try {

            userDao.deleteById(user.getU_id());
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("删除用户失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户ID查询某用户信息
    public User selectUserServiceById(String u_id)
    {
        User user = new User();
        try{
            user = userDao.findById(u_id).orElse(null);
        }
        catch (Exception e)
        {
            System.out.println("查询用户信息失败");
            e.printStackTrace();
        }
        return user;
    }

    //根据用户电话查询某用户信息
    public User selectUserServiceByPhone(String u_phone)
    {
        User user = new User();
        try{
            user = userDao.findByU_phone(u_phone);
        }
        catch (Exception e)
        {
            System.out.println("查询用户信息失败");
            e.printStackTrace();
        }
        return user;
    }

    //根据用户ID封禁用户
    public boolean blockUserServiceById(String u_id)
    {
        User user = new User();
        boolean flag=false;
        try{
            user = userDao.findById(u_id).orElse(null);
            user.setU_state(0);
            userDao.save(user);
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("封禁用户失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户ID解封用户
    public boolean unblockUserServiceById(String u_id)
    {
        User user = new User();
        boolean flag=false;
        try{
            user = userDao.findById(u_id).orElse(null);
            user.setU_state(1);
            userDao.save(user);
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("解封用户失败");
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户ID重置密码
    User esetUserServiceById(String u_id, String u_pwd)
    {
        User user = new User();
        boolean flag=false;
        try{
            user = userDao.findById(u_id).orElse(null);
            user.setU_pwd(u_pwd);
            userDao.save(user);
            flag=true;
        }
        catch (Exception e)
        {
            System.out.println("重置密码失败");
            e.printStackTrace();
        }
        return user;
    }

    public User selectUserServiceByEmail(String u_email) {//根据邮箱查找
        User user = new User();
        try{
            user = userDao.findByU_email(u_email);
        }
        catch (Exception e)
        {
            System.out.println("查询用户信息失败");
            e.printStackTrace();
        }
        return user;
    }
}
