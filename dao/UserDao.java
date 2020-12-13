package com.example.demo1.dao;

import com.example.demo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserDao extends JpaRepository<User,String> {
    @Query("select u from User u where u.u_phone = ?1")
    User findByU_phone(String u_phone);

    @Query("select u from User u where u.u_email = ?1")
    User findByU_email(String u_email);
}
