package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int c_id;

    @Column(name = "u_id")
    private String u_id;

    @Column(name = "s_id")
    private int s_id;

    @Column(name = "s_name")
    private String s_name;
    //json格式数据[{"g_id":*,"g_size":*,"g_num":*,"g_price":*,"g_pic":*},{***}]
    //              货号     商品套餐类型   数量      价格    图片
    @Column(name = "g_sizes")
    private String g_sizes;
    public int getC_id() {
        return c_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getG_sizes() {
        return g_sizes;
    }

    public void setG_sizes(String g_sizes) {
        this.g_sizes = g_sizes;
    }
}
