package com.factory.dao;

import com.factory.model.FactoryOwner;
import com.factory.model.Seller;
import com.factory.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDataBase  implements Serializable{
    private ArrayList<User> users = new ArrayList<User>();
    

    public UserDataBase(){
        
    }
    
    public ArrayList<User> getAllUsers() {
    	return users;
    }

    public ArrayList<FactoryOwner> getFactoryOwners() {
        ArrayList<FactoryOwner> owners = new ArrayList<FactoryOwner>();
        for (User k : users) {
        	if (k instanceof FactoryOwner) {
        		owners.add((FactoryOwner)k);
        	}
        }
        return owners;
    }
    
    public ArrayList<Seller> getSellers() {
    	ArrayList<Seller> sellers = new ArrayList<Seller>();
    	for (User k : users) {
    		if (k instanceof Seller) {
    			sellers.add((Seller)k);
    		}
     	}
    	return sellers;
    }

    public void addUser(User user) {
        users.add(user);
     
    }

    public void removeUser(User user) {
        users.remove(user);
    }
    //注意这个方法，可用于检查是否存在重复账号
   public User getUserByAccount(String account) {
        for (User k : users) {
            if (k.getAccount().equals(account)) {
                return k;
            }
        }
        return  null;
   }
   
   public User getUserByName(String name) {
       for (User k : users) {
           if (k.getName().equals(name)) {
               return k;
           }
       }
       return  null;
   }
   
   //用于模糊查找
   public ArrayList<User> searchUsersByName(String name) {
	   ArrayList<User> list = new ArrayList<User>();
	   for (User k : users) {
		   if (k.getName().startsWith(name)) {
			   list.add(k);
		   }
	   }
	   return list;
   }

   public User getUserByID(int id) {
       for (User k : users) {
           if (k.getID() == id) {
               return k;
           }
       }
       return  null;
   }
   
   public FactoryOwner getOwnerByFactID(int factoryID) {
	   for (FactoryOwner k : getFactoryOwners()) {
		   if (k.getMyFactoryID() == factoryID) {
			   return k;
		   }
	   }
	   return null;
   }
    

//    public int getNumOfOwners() {
//        return owners.size();
//    }

}
