package com.factory.model;

import java.io.Serializable;

import com.factory.dao.Dao;

public class Equipment  implements Serializable{
    private int ID; //系统自动生成
    private String name;
    private String typeNum; //设备编号（型号）， 也是自动生成
    private EquipmentCategory category; //设备类型
    private String size; //设备规格
    private String description; //设备描述
    private String workingState; //设备状态：开机/关机
    private String rentState; //租用状态
    private int belongFactoryID; //所属的工厂的编号
    
   

    public Equipment(String name, EquipmentCategory category, String size, String description) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.description = description;
        this.workingState = "关机";
        this.rentState = "工厂设备";
        this.belongFactoryID = -1;
        
        this.typeNum = "EQU2023000";
    }

    public int getBelongFactoryID() {
		return belongFactoryID;
	}

	public void setBelongFactoryID(int belongFactoryID) {
		this.belongFactoryID = belongFactoryID;
	}

	public String getRentState() {
        return rentState;
    }

    public void setRentState(String rentState) {
        this.rentState = rentState;
    }

    public String getWorkingState() {
        return workingState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }
    
    // 可控制开关机
    public void changeWorkingState() {
        if (workingState.equals("关机")) {
        	workingState = "开机";
        }
        else {
        	workingState = "关机";
        }
    } 

    public EquipmentCategory getCategory() {
        return category;
    }

    public void setCategory(EquipmentCategory category) {
        this.category = category;
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
        this.typeNum += ""+ID;
    }
}
