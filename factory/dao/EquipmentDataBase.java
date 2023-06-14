package com.factory.dao;
import java.io.Serializable;
import java.util.ArrayList;

import com.factory.model.Category;
import com.factory.model.Equipment;
import com.factory.model.RentEquipment;

// 集成了产能中心的功能，因为RentEquipment也是Equipment的子类
public class EquipmentDataBase  implements Serializable{
	private ArrayList<Equipment> equipments = new ArrayList<Equipment>();
	
	
	public EquipmentDataBase() {
		
	}
	
	public ArrayList<Equipment> getEquipments() {
		return equipments;
	}
	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);
		
	}
	
	public void removeEquipment(Equipment equipment) {
		equipments.remove(equipment);
	}
	
	public Equipment getEquipment(int id) {
		for (Equipment k : equipments) {
			if (k.getID() == id) {
				return k;
			}
		}
		return null;
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
	
	//下面两方法用于产能中心的可租设备的模糊查找
		public ArrayList<RentEquipment> searchSpareREByName(String name) {
			ArrayList<RentEquipment> list = new ArrayList<RentEquipment>();
			for (RentEquipment k : getSpareREs()) {
				if (k.getName().startsWith(name)) {
					list.add(k);
				}
			}
			return list;
		}
		
		public ArrayList<RentEquipment> searchSpareREByCateName(String cateName) {
			ArrayList<RentEquipment> list = new ArrayList<RentEquipment>();
			for (RentEquipment k : getSpareREs()) {
				if (k.getCategory().getCategoryName().startsWith(cateName)) {
					list.add(k);
				}
			}
			return list;
		}
		
	
	public ArrayList<Equipment> getEquipmentsByFactoryID(int factoryID) {
		ArrayList<Equipment> list = new ArrayList<Equipment>();
		for (Equipment k : equipments) {
			if (k.getBelongFactoryID() == factoryID) {
				list.add(k);
			}
		}
		return list;
	}
	
	public ArrayList<Equipment> getEquipmentsByCategory(Category cate) {
		ArrayList<Equipment> list = new ArrayList<Equipment>();
		for (Equipment k : equipments) {
			if (k.getCategory().equals(cate)) {
				list.add(k);
			}
		}
		
		return list;
	}
	
	public ArrayList<RentEquipment> getRentEquipments() {
       ArrayList<RentEquipment> res = new ArrayList<RentEquipment>();
       for (Equipment k : equipments) {
    	   if (k instanceof RentEquipment) {
    		   res.add((RentEquipment)k);
    	   }
       }
    	   
       return res;
    }
	
	public ArrayList<RentEquipment> getRentEquipmentsByFactoryID(int factoryID) {
			ArrayList<RentEquipment> list = new ArrayList<RentEquipment>();
			for (RentEquipment k : getRentEquipments()) {
				if (k.getBelongFactoryID() == factoryID) {
					list.add(k);
				}
			}
			
			return list;
	}
	
	public ArrayList<RentEquipment> getRentEquipmentsByCate(Category cate) {
		ArrayList<RentEquipment> list = new ArrayList<RentEquipment>();
		for (RentEquipment k : getRentEquipments()) {
			if (k.getCategory().equals(cate)) {
				list.add(k);
			}
		}
		
		return list;
	}
	
	public ArrayList<RentEquipment> getSpareREs() {
		ArrayList<RentEquipment> res = new ArrayList<RentEquipment>();
	    for (Equipment k : equipments) {
	    	   if (k instanceof RentEquipment) {
	    		   RentEquipment re = (RentEquipment)k;
	    		   if (re.getRentState().equals("未被租用")) 
	    			   res.add(re); 
	    	   }
	    }
	    return res;
	}

	
}
