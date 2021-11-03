package com.dhl.custom.model;

import java.io.Serializable;

/**
 * Item
 */
public class Item implements Serializable{

    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Item [code=" + code + ", name=" + name + "]";
    }
    
}