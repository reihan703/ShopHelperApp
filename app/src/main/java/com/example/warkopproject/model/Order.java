package com.example.warkopproject.model;

import java.io.Serializable;

public class Order implements Serializable {
    private String namaOrder, key;
    private Integer hargaOrder;

    public Order(){

    }

    public Order(String namaOrder, Integer hargaOrder) {
        this.namaOrder = namaOrder;
        this.key = key;
        this.hargaOrder = hargaOrder;
    }

    public String getNamaOrder() {
        return namaOrder;
    }

    public void setNamaOrder(String namaOrder) {
        this.namaOrder = namaOrder;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getHargaOrder() {
        return hargaOrder;
    }

    public void setHargaOrder(Integer hargaOrder) {
        this.hargaOrder = hargaOrder;
    }
}
