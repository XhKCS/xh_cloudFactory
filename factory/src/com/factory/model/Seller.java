package com.factory.model;

import java.io.Serializable;

public class Seller extends User  implements Serializable{
	
	
	public Seller(String account, String password, String name, String connectionWay) {
		super(account, password, name, connectionWay);
		this.setIdentity("经销商");
	}

	
}
