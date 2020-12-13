package com.example.demo1.dao;

import com.example.demo1.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartDao extends JpaRepository<Cart,Integer> {
    @Query("select c from Cart c where c.u_id = ?1")
    List<Cart> findByU_id(String u_id);

    @Query("select c from Cart c where c.s_id = ?1")
    Cart findByS_id(int s_id);

    @Query("select c from Cart c where c.u_id = ?1 and c.s_id=?2")
    Cart findByU_idAndS_id(String u_id,int s_id);
}