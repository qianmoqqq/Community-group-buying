package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    private int s_id;

    @Column(name = "s_name")
    private String s_name;

    @Column(name = "u_id")
    private String u_id;

    @Column(name = "s_type")
    private String s_type;

    @Column(name = "s_cre")
    private String s_cre;

    @Column(name = "s_intro")
    private String s_intro;

    @Column(name = "s_phone")
    private String s_phone;

    @Column(name = "s_addr")
    private String s_addr;

    @Column(name = "s_state")
    private int s_state;

    @Column(name = "s_rating")
    private int s_rating;

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getS_type() {
        return s_type;
    }

    public void setS_type(String s_type) {
        this.s_type = s_type;
    }

    public String getS_cre() {
        return s_cre;
    }

    public void setS_cre(String s_cre) {
        this.s_cre = s_cre;
    }

    public String getS_intro() {
        return s_intro;
    }

    public void setS_intro(String s_intro) {
        this.s_intro = s_intro;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_addr() {
        return s_addr;
    }

    public void setS_addr(String s_addr) {
        this.s_addr = s_addr;
    }

    public int getS_state() {
        return s_state;
    }

    public void setS_state(int s_state) {
        this.s_state = s_state;
    }

    public int getS_rating() {
        return s_rating;
    }

    public void setS_rating(int s_rating) {
        this.s_rating = s_rating;
    }
}
