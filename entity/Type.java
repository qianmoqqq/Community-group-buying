package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private int t_id;

    @Column(name = "t_name")
    private String t_name;

    @Column(name = "t_next")
    private String t_next;

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_next() {
        return t_next;
    }

    public void setT_next(String t_next) {
        this.t_next = t_next;
    }

    public String getT_name() {
        return t_name;
    }


}
