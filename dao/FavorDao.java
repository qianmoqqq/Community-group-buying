package com.example.demo1.dao;

import com.example.demo1.entity.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FavorDao extends JpaRepository<Favor,Integer> {
   @Query("select f from Favor f where f.u_id = ?1")
   List<Favor> findByU_id(String u_id);
}
