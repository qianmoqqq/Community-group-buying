package com.example.demo1.dao;

import com.example.demo1.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeDao extends JpaRepository<Type,Integer> {
    @Query("select t from Type t where t.t_name = ?1")
    Type findByT_name(String t_name);
}
