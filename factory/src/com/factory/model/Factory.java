package com.factory.model;

import com.factory.dao.Dao;

import java.io.Serializable;
import java.util.ArrayList;

public class Factory  implements Serializable{
    private int ID; //系统自动生成
    private String name;
    private String description; //工厂简介
    private int ownerID; //负责人的ID
    private String state; //关停或正常
    private ArrayList<Equipment> equipments = new ArrayList<Equipment>(); 
    //包括工厂自有设备和租赁设备
    
//    private static int totalNum = 0;
    
    public Factory(String name, String description, int ownerID) {
        this.name = name;
        this.description = description;
        this.ownerID = ownerID;
        this.state = "正常";    
//        totalNum++;
//        this.ID = totalNum;
    }
    
    public String getState() {
    	return state;
    }
    
    public void changeState() {
    	if (state.equals("正常")) {
    		state = "关停";
    	}
    	else {
    		state = "正常";
    	}
    }
    
    public void addEquiment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setBelongFactoryID(this.ID); //在这里就把工作做好，避免忘记
    }
    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
    }

    // 工厂所有的设备集合
    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }
    
    // 工厂租用的设备集合
    public ArrayList<RentEquipment> getRentEquipments() {
        ArrayList<RentEquipment> res = new ArrayList<RentEquipment>();
        for (Equipment k : equipments) {
     	   if (k instanceof RentEquipment) {
     		   res.add((RentEquipment)k);
     	   }
        }
     	   
        return res;
    }
    
    public Equipment getEquipment(int id) {
    	for (Equipment k : equipments) {
    		if (k.getID() == id) {
    			return k;
    		}
    	}
    	return null;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    //下面两方法用于模糊查找
  	public ArrayList<Equipment> searchEquipmentsByName(String name) {
  		ArrayList<Equipment> list = new ArrayList<Equipment>();
  		for (Equipment k : equipments) {
  			if (k.getName().startsWith(name)) {
  				list.add(k);
  			}
  		}
  		return list;
  	}
  	
  	public ArrayList<Equipment> searchEquipmentsByCateName(String cateName) {
  		ArrayList<Equipment> list = new ArrayList<Equipment>();
  		for (Equipment k : equipments) {
  			if (k.getCategory().getCategoryName().startsWith(cateName)) {
  				list.add(k);
  			}
  		}
  		return list;
  	}


}
