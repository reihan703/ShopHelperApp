package com.example.warkopproject.model;

import java.io.Serializable;

public class Barang implements Serializable {
    private String namaBarang, kategoriBarang, key;
    private Integer stockBarang;

    //CONSTRUCTOR
    public Barang(){
    }

    public Barang(String namaBarang, String kategoriBarang, Integer stockBarang) {
        this.namaBarang = namaBarang;
        this.kategoriBarang = kategoriBarang;
        this.stockBarang = stockBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getStockBarang() {
        return stockBarang;
    }

    public void setStockBarang(Integer stockBarang) {
        this.stockBarang = stockBarang;
    }

}
