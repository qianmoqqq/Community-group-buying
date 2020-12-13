package com.example.demo1.entity;

public class Sizes_g {
    private String g_sizea;
    private String g_pica;
    private int g_numa;
    private double g_pricea;
    public Sizes_g(){

    }

    public String getG_sizea() {
        return g_sizea;
    }

    public void setG_sizea(String g_sizea) {
        this.g_sizea = g_sizea;
    }

    public String getG_pica() {
        return g_pica;
    }

    public void setG_pica(String g_pica) {
        this.g_pica = g_pica;
    }

    public int getG_numa() {
        return g_numa;
    }

    public void setG_numa(int g_numa) {
        this.g_numa = g_numa;
    }

    public double getG_pricea() {
        return g_pricea;
    }

    public void setG_pricea(double g_pricea) {
        this.g_pricea = g_pricea;
    }

    public Sizes_g(String g_sizea, String g_pica, int g_numa, double g_pricea) {
        this.g_sizea = g_sizea;
        this.g_pica = g_pica;
        this.g_numa = g_numa;
        this.g_pricea = g_pricea;
    }

    @Override
    public String toString() {
        return "Sizes_g{" +
                "g_sizea='" + g_sizea + '\'' +
                ", g_pica='" + g_pica + '\'' +
                ", g_numa=" + g_numa +
                ", g_pricea=" + g_pricea +
                '}';
    }
}
