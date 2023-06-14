package com.factory.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DBController {
	private DataBase db = new DataBase();
	
	public DBController() {
		load(); //让db完成数据读取
	}
	
	public DataBase getDb() {
		return db;
	}
	
	public void save() {
    	String fileName = "DataBase.dat";
    	try {
    		FileOutputStream fOut = new FileOutputStream(fileName);
    		ObjectOutputStream objOut = new ObjectOutputStream(fOut);
    		objOut.writeObject(db);
    		objOut.close();
    		fOut.close();
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void load() {
    	String fileName = "DataBase.dat";
    	try {
    		FileInputStream fIn = new FileInputStream(fileName);
    		ObjectInputStream objIn = new ObjectInputStream(fIn);
    		db = (DataBase)(objIn.readObject());
    		objIn.close();
    		fIn.close();
    		
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
	
	
}
