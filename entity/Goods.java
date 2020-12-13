package com.example.demo1.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.gson.JsonArray;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_id")
    private int g_id;

    @Column(name = "g_name")
    private String g_name;

    @Column(name = "s_id")
    private int s_id;

    @Column(name = "g_type")
    private String g_type;

    @Column(name = "g_lowprice")
    private double g_lowprice;

    @Column(name = "g_highprice")
    private double g_highprice;

    @Column(name = "g_para")
    private String g_para;

    @Column(name = "g_intro")
    private String g_intro;

    @Column(name = "g_pic")
    private String g_pic;

    @Column(name = "g_state")
    private int g_state;

    @Column(name = "g_rating")
    private double g_rating;

    //json格式数据[{"g_sizea":*,"g_pica":*,"g_numa":*,"g_pricea":*},{***}]
    @Column(name = "g_size")
    private String g_size;

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getG_type() {
        return g_type;
    }

    public void setG_type(String g_type) {
        this.g_type = g_type;
    }

    public double getG_lowprice() {
        return g_lowprice;
    }

    public void setG_lowprice(double g_lowprice) {
        this.g_lowprice = g_lowprice;
    }

    public double getG_highprice() {
        return g_highprice;
    }

    public void setG_highprice(double g_highprice) {
        this.g_highprice = g_highprice;
    }

    public String getG_para() {
        return g_para;
    }

    public void setG_para(String g_para) {
        this.g_para = g_para;
    }

    public String getG_intro() {
        return g_intro;
    }

    public void setG_intro(String g_intro) {
        this.g_intro = g_intro;
    }

    public String getG_pic() {
        return g_pic;
    }

    public void setG_pic(String g_pic) {
        this.g_pic = g_pic;
    }

    public int getG_state() {
        return g_state;
    }

    public void setG_state(int g_state) {
        this.g_state = g_state;
    }

    public double getG_rating() {
        return g_rating;
    }

    public void setG_rating(double g_rating) {
        this.g_rating = g_rating;
    }

    public String getG_size() {
        return g_size;
    }

    public void setG_size(String g_size) {
        this.g_size = g_size;
    }
}
