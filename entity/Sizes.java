package com.example.demo1.entity;

import javax.persistence.Entity;


public class Sizes {
    private int g_id;
    private String g_size;
    private int g_num;
    private double g_price;
private String g_pic;
    private String g_name;


    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getG_size() {
        System.out.println("getgsize:"+g_size);
        return g_size;
    }

    public void setG_size(String g_size) {
        this.g_size = g_size;
    }

    public double getG_price() {
        return g_price;
    }

    public void setG_price(double g_price) {
        this.g_price = g_price;
    }

    public String getG_pic() {
        return g_pic;
    }

    public void setG_pic(String g_pic) {
        this.g_pic = g_pic;
    }

    public int getG_num() {
        return g_num;
    }

    public void setG_num(int g_num) {
        this.g_num = g_num;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public Sizes()
    {

    }

    public Sizes(int g_id, String g_size, int g_num, double g_price, String g_pic, String g_name) {
        this.g_id = g_id;
        this.g_size = g_size;
        this.g_num = g_num;
        this.g_price = g_price;
        this.g_pic = g_pic;
        this.g_name = g_name;
    }

    @Override
    public String toString() {
        return "Sizes{" +
                "g_id=" + g_id +
                ", g_size='" + g_size + '\'' +
                ", g_num=" + g_num +
                ", g_price=" + g_price +
                ", g_pic='" + g_pic + '\'' +
                ", g_name='" + g_name + '\'' +
                '}';
    }
}
