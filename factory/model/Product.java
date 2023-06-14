package com.factory.model;

import java.io.Serializable;

import com.factory.dao.Dao;

public class Product  implements Serializable{
    private int ID; //自动生成
    private String name;
    private String typeNum; //产品编号，也是自动生成
    private ProductCategory category;
    private String size; //产品规格
    private String description; //产品描述
    
//    private static int totalNum = 0;

    public Product(String name, ProductCategory category, String size, String description) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.description = description;
        
        this.typeNum = "PRO2023000";
       // Dao.getDb().getProductsDB().addProduct(this);
//        totalNum++;
//        this.ID = totalNum;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        this.typeNum += ""+ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
