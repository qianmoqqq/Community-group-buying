package com.example.demo1.dao;

import com.example.demo1.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SellerDao extends JpaRepository<Seller,Integer> {
    @Query("select s from Seller s where s.s_name = ?1")
    Seller findByS_name(int s_name);

    @Query("select s from Seller s where s.u_id=?1")
    Seller findByU_id(String logid);
}