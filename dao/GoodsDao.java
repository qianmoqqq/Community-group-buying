package com.example.demo1.dao;

import com.example.demo1.entity.Goods;
import com.example.demo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GoodsDao extends JpaRepository<Goods,Integer> {
    @Query("select g from Goods g where g.g_type = ?1")
    Goods findByG_type(String g_type);

    @Query("select g from Goods g where g.g_state = ?1")
    List<Goods> findByG_state(int g_state);

    @Query("select g from Goods g where g.g_highprice>?1 or g.g_lowprice < ?2")
    Goods findByG_price(int g_price_low, int g_price_high);

    @Query("select g from Goods g where g.g_name like ?1 and g.g_type like ?2 and g.g_highprice>?3 ")
    List<Goods> findByGroup(String g_name, String g_type,double g_price_low, double g_price_high);

    @Query("select g from Goods g where g.g_name like %?1%")
    List<Goods> findByG_name(String g_name);

    @Query("select g from Goods g where g.s_id = ?1")
    List<Goods> findByS_id(int s_id);
}
