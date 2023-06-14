package com.factory.model;

import java.io.Serializable;

import com.factory.dao.Dao;

public class FactoryOwner extends User  implements Serializable{
    private int myFactoryID; //只有一个云工厂
    
    public FactoryOwner(String account, String password, String name, String connectionWay) {
        super(account, password, name, connectionWay);
        this.setIdentity("云工厂");
        //剩余操作在service层注册中和DataBase初始化中写完了
    }

    public int getMyFactoryID() {
        return myFactoryID;
    }


	public void setMyFactoryID(int factoryID) {
		this.myFactoryID = factoryID;
	}


}
