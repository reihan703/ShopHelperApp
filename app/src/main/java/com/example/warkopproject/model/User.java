package com.example.warkopproject.model;

import java.io.Serializable;

public class User implements Serializable {

    private String keyUser, email, password, name,
            nomorTelepon, alamatToko, namaToko;

    //CONSTRUCTOR
    public User(){

    }

    public User(String keyUser, String email, String name,
                String nomorTelepon, String alamatToko, String namaToko) {
        this.keyUser = keyUser;
        this.email = email;
        this.name = name;
        this.nomorTelepon = nomorTelepon;
        this.alamatToko = alamatToko;
        this.namaToko = namaToko;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

}
