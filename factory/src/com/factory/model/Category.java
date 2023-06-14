package com.factory.model;

import java.io.Serializable;

import com.factory.dao.Dao;

public abstract class Category  implements Serializable{
	private int ID; //系统自动生成
	private String categoryName;
	
	//private static int totalNum = 0; //直接在实体类自己里面定义静态变量

    public Category(String categoryName) {
        this.categoryName = categoryName;
 
    }

    @Override
	public String toString() {
		return categoryName;
	}
    
    
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			Category category = (Category) obj;
			if (category.getCategoryName().equals(this.categoryName)) {
				return true;
			}
		}
		
		return false;
	}

	public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public int getID() {
		return ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}
}
