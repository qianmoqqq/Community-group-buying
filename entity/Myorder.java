package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "myorder")
public class Myorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private int o_id;

    @Column(name = "u_id")
    private String u_id;

    @Column(name = "s_id")
    private int s_id;

    //json数据格式[{"g_id":*,"g_size":*,"g_price":*,"g_num":*},{***}]
    @Column(name = "g_sizes")
    private String g_sizes;

    @Column(name = "o_text")//备注
    private String o_text;

    @Column(name = "o_sum")//总价
    private double o_sum;

    @Column(name = "o_state")
    private int o_state;

    @Column(name = "o_address")//地址
    private String o_address;

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
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

    public String getO_text() {
        return o_text;
    }

    public void setO_text(String o_text) {
        this.o_text = o_text;
    }

    public double getO_sum() {
        return o_sum;
    }

    public void setO_sum(double o_sum) {
        this.o_sum = o_sum;
    }

    public int getO_state() {
        return o_state;
    }

    public void setO_state(int o_state) {
        this.o_state = o_state;
    }

    public String getO_address() {
        return o_address;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }
}
