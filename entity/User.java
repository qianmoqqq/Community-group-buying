package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "u_id")
    private String u_id;

    @Column(name = "u_name")
    private String u_name;

    @Column(name = "u_pwd")
    private String u_pwd;

    @Column(name = "u_role")
    private int u_role;

    @Column(name = "u_sex")
    private int u_sex;

    @Column(name = "u_phone")
    private String u_phone;

    @Column(name = "u_email")
    private String u_email;

    @Column(name = "u_state")
    private int u_state;

    @Column(name = "u_address")
    private String u_address;



    public String getU_id() {
        return u_id;
    }

    public void setU_id(String  u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_pwd() {
        return u_pwd;
    }

    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }

    public int getU_role() {
        return u_role;
    }

    public void setU_role(int u_role) {
        this.u_role = u_role;
    }

    public int getU_sex() {
        return u_sex;
    }

    public void setU_sex(int u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public int getU_state() {
        return u_state;
    }

    public void setU_state(int u_state) {
        this.u_state = u_state;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }
}

