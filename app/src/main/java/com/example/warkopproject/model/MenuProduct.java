package com.example.warkopproject.model;

import android.view.Menu;

import java.io.Serializable;

public class MenuProduct implements Serializable {
    private String namaMenu, kategoriMenu, key;
    private Integer hargaMenu;

    //Constructor
    public MenuProduct(){

    }

    public MenuProduct(String namaMenu, String kategoriMenu, Integer hargaMenu){
        this.namaMenu = namaMenu;
        this.kategoriMenu = kategoriMenu;
        this.hargaMenu = hargaMenu;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public String getKategoriMenu() {
        return kategoriMenu;
    }

    public void setKategoriMenu(String kategoriMenu) {
        this.kategoriMenu = kategoriMenu;
    }

    public Integer getHargaMenu() {
        return hargaMenu;
    }

    public void setHargaMenu(Integer hargaMenu) {
        this.hargaMenu = hargaMenu;
    }
}
