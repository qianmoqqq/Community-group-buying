package com.example.demo1.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "favor")
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private int f_id;

    @Column(name = "u_id")
    private String u_id;

    @Column(name = "g_id")
    private int g_id;

    @Column(name = "time")
    private Date time;

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
