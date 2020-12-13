package com.example.demo1.dao;

import com.example.demo1.entity.Myorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderDao extends JpaRepository<Myorder,Integer> {
    @Query("select o from Myorder o where o.u_id = ?1")
    List<Myorder> findByU_id(String u_id);

    @Query("select o from Myorder o where o.u_id = ?1 and o.o_state= ?2")
    List<Myorder> findByU_idAndO_state(String u_id, int o_state);

    @Query("select o from Myorder o where o.s_id = ?1 and o.o_state= ?2")
    List<Myorder> findByS_idAndO_state(int s_id, int o_state);
}
