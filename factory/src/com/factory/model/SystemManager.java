package com.factory.model;

import java.io.Serializable;

public class SystemManager extends User implements Serializable{
    //系统管理员
	

	public SystemManager(String account, String password, String name, String connectionWay) {
        super(account, password, name, connectionWay);
        this.setIdentity("管理员");
    }
	

}
