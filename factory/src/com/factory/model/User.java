package com.factory.model;

import java.io.Serializable;

public abstract class User implements Serializable{
   private int  ID; //自动生成
   private String account; //登录账号
   private String password;
   private String name;
   private String connectionWay; //联系方式
   private String identity; //身份类型
   

   public User (String account, String password, String name, String connectionWay) {
       this.account = account;
       this.password = password;
       this.name = name;
       this.connectionWay = connectionWay;
       //ID需要在service层手动添加
   }

    public int getID() {
	return ID;
}

    public void setID(int ID) {
    	this.ID = ID;
    }

	public String getConnectionWay() {
        return connectionWay;
    }

    public void setConnectionWay(String connectionWay) {
        this.connectionWay = connectionWay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getIdentity() {
    	return identity;
    }

    public void setIdentity(String identity) {
    	this.identity = identity;
    }

}