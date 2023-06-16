package com.factory.dao;

import java.io.Serializable;
import java.util.ArrayList;

import com.factory.model.Category;
import com.factory.model.EquipmentCategory;
import com.factory.model.ProductCategory;

//存储的类别包括设备类别和产品类别
public class CategoryDataBase  implements Serializable{
	private ArrayList<Category> categories = new ArrayList<Category>();
	
	public CategoryDataBase() {
		
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public ArrayList<ProductCategory> getProductCategories() {
		ArrayList<ProductCategory> list = new ArrayList<ProductCategory>();
		for (Category k : categories) {
			if (k instanceof ProductCategory) {
				list.add((ProductCategory) k);
			}
		}
		return list;
	}
	
	public EquipmentCategory getECByName(String cateName) {
		ArrayList<EquipmentCategory> ecs = getEquipmentCategories();
		for (EquipmentCategory k : ecs) {
			if (k.getCategoryName().equals(cateName)) {
				return k;
			}
		}
		return null;
	}
	
	public EquipmentCategory getECByID(int id) {
		ArrayList<EquipmentCategory> ecs = getEquipmentCategories();
		for (EquipmentCategory k : ecs) {
			if (k.getID() == id) {
				return k;
			}
		}
		return null;
	}
	
	public ProductCategory getPCByName(String cateName) {
		ArrayList<ProductCategory> pcs = getProductCategories();
		for (ProductCategory k : pcs) {
			if (k.getCategoryName().equals(cateName)) {
				return k;
			}
		}
		return null;
	}
	
	public ProductCategory getPCByID(int id) {
		ArrayList<ProductCategory> pcs = getProductCategories();
		for (ProductCategory k : pcs) {
			if (k.getID() == id) {
				return k;
			}
		}
		return null;
	}
	
	public ArrayList<EquipmentCategory> getEquipmentCategories() {
		ArrayList<EquipmentCategory> list = new ArrayList<EquipmentCategory>();
		for (Category k : categories) {
			if (k instanceof EquipmentCategory) {
				list.add((EquipmentCategory) k);
			}
		}
		return list;
	}
	//下面两方法用于模糊查找
	public ArrayList<EquipmentCategory> searchECsByName(String name) {
		ArrayList<EquipmentCategory> list = new ArrayList<EquipmentCategory>();
		for (Category k : categories) {
			if (k instanceof EquipmentCategory && k.getCategoryName().startsWith(name)) {
				list.add((EquipmentCategory) k);
			}
		}
		return list;
	}
	
	public ArrayList<ProductCategory> searchPCsByName(String name) {
		ArrayList<ProductCategory> list = new ArrayList<ProductCategory>();
		for (Category k : categories) {
			if (k instanceof ProductCategory && k.getCategoryName().startsWith(name)) {
				list.add((ProductCategory) k);
			}
		}
		return list;
	}
	
	public void addCategory(Category category) {
		String cateName = category.getCategoryName();
		if (getCategoryByName(cateName) == null) {
			categories.add(category); //避免重名
		}
		
	}
	
	public void removeCategory(Category category) {
		categories.remove(category);
	}
	
	public Category getCategory(int id) {
		for (Category k : categories) {
			if (k.getID() == id) {
				return k;
			}
		}
		return null;
	}
	
	public Category getCategoryByName(String cateName) {
		for (Category k : categories) {
			if (k.getCategoryName().equals(cateName)) {
				return k;
			}
		}
		return null;
	}
	
//	public int getNumOfCategories() {
//		return categories.size();
//	}
}
