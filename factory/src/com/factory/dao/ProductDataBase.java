package com.factory.dao;

import com.factory.model.Product;
import com.factory.model.ProductCategory;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDataBase  implements Serializable{
    private ArrayList<Product> products = new ArrayList<Product>();
   

    public ProductDataBase() {
        
    }

    public void addProduct(Product product) {
        products.add(product);
    
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Product getProduct(int id) {
        for (Product k : products) {
            if (k.getID() == id) {
                return k;
            }
        }
        return null;
    }

    public Product getProductByName(String name) {
    	for (Product k : products) {
    		if (k.getName().equals(name)) {
    			return k;
    		}
    	}
    	return null;
    }
    
    //以下两方法用于模糊查找
    public ArrayList<Product> searchProductsByName(String name) {
    	ArrayList<Product> list = new ArrayList<Product>();
    	for (Product k : products) {
    		if (k.getName().startsWith(name)) {
    			list.add(k);
    		}
    	}
    	
    	return list;
    }
    
    public ArrayList<Product> searchProductsByCateName(String cateName) {
    	ArrayList<Product> list = new ArrayList<Product>();
    	for (Product k : products) {
    		if (k.getCategory().getCategoryName().startsWith(cateName)) {
    			list.add(k);
    		}
    	}
    	
    	return list;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    
    public ArrayList<Product> getProductsByCategory(ProductCategory cate) {
        ArrayList<Product> list = new ArrayList<Product>();
    	for (Product k : products) {
    		if (k.getCategory().equals(cate)) {
    			list.add(k);
    		}
    	}
        
    	return list;
    }

}
