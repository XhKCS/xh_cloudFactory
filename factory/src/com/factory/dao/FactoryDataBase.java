package com.factory.dao;

import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class FactoryDataBase  implements Serializable{
    private ArrayList<Factory> factories = new ArrayList<Factory>();
    

    public FactoryDataBase() {
    	
    }

    public void addFactory(Factory factory) {
        factories.add(factory);
      
    }

    public void removeFactory(Factory factory) {
        factories.remove(factory);
    }

    public Factory getFactory(int id) {
        for (Factory k : factories) {
            if (k.getID() == id) {
                return k;
            }
        }
        return null;
    }
    
    public Factory getFactoryByOwnerID(int ownerID) {
        for (Factory k : factories) {
            if (k.getOwnerID() == ownerID) {
                return k;
            }
        }
        return null;
    }
    
    public Factory getFactoryByName(String factName) {
        for (Factory k : factories) {
            if (k.getName().equals(factName)) {
                return k;
            }
        }
        return null;
    }
    
    //以下两方法用于模糊查找
    public ArrayList<Factory> searchFactoriesByName(String factName) {
    	ArrayList<Factory> list = new ArrayList<Factory>();
    	for (Factory k : factories) {
    		if (k.getName().startsWith(factName)) {
    			list.add(k);
    		}
    	}
    	
        return list;
    }
    
    public ArrayList<Factory> searchFactoriesByOwnerName(String ownerName) {
    	ArrayList<Factory> list = new ArrayList<Factory>();
    	UserDataBase udb = Dao.getDb().getUsersDB();
    	for (Factory k : factories) {
    		FactoryOwner owner = udb.getOwnerByFactID(k.getID());
    		if (owner.getName().startsWith(ownerName)) {
    			list.add(k);
    		}
    	}
    	
        return list;
    }
    
    public ArrayList<Factory> getOpeningFactories() {
    	ArrayList<Factory> list = new ArrayList<Factory>();
    	for (Factory k : factories) {
    		if (k.getState().equals("正常")) {
    			list.add(k);
    		}
    	}
    	
        return list;
    }
    
    public ArrayList<Factory> getClosedFactories() {
    	ArrayList<Factory> list = new ArrayList<Factory>();
    	for (Factory k : factories) {
    		if (k.getState().equals("关停")) {
    			list.add(k);
    		}
    	}
    	
        return list;
    }

//    public int getNumOfFactories() {
//        return factories.size();
//    }

    public ArrayList<Factory> getFactories() {
        return factories;
    }
    
}
