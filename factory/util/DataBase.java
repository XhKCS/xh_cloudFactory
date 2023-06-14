package com.factory.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.factory.dao.CategoryDataBase;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.FactoryDataBase;
import com.factory.dao.ProductDataBase;
import com.factory.dao.UserDataBase;

import com.factory.model.Category;
import com.factory.model.Equipment;
import com.factory.model.EquipmentCategory;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.Product;
import com.factory.model.ProductCategory;
import com.factory.model.RentEquipment;
import com.factory.model.Seller;
import com.factory.model.SystemManager;

public class DataBase  implements Serializable{
	private  SystemManager manager; //唯一的系统管理员
    private  FactoryDataBase factoriesDB = new FactoryDataBase();
    private  UserDataBase usersDB = new UserDataBase();
    private  ProductDataBase productsDB = new ProductDataBase();
    private  EquipmentDataBase equipmentsDB = new EquipmentDataBase();
    private  CategoryDataBase categoriesDB = new CategoryDataBase();
   
	public DataBase() {
    	//init();
    }
    
    //数据初始化（在没有存文件之前）
    public void init() {
    	manager = new SystemManager("11111", "123", "xiaohan", "1602915784");
    	manager.setID(1);
    	
    	//不能偷懒！不要在其他类的构造方法里就用到DataBase！
    	
    	Seller seller1 = new Seller("aaa", "000", "010", "00a");
    	seller1.setID(2);
    	usersDB.addUser(seller1);
    	
    	//设备类别、设备、工厂、工厂管理员相互挂钩
    	FactoryOwner owner1 = new FactoryOwner("AAA","001", "a01", "aaa");
    	owner1.setID(3);
    	usersDB.addUser(owner1);
		Factory factory1 = new Factory("aaa", "aaa", owner1.getID());
		factory1.setID(1);
		factoriesDB.addFactory(factory1);
		EquipmentCategory ec1 = new EquipmentCategory("一类");
		ec1.setID(1);
		EquipmentCategory ec2 = new EquipmentCategory("二类");
		ec2.setID(2);
		categoriesDB.addCategory(ec1);
		categoriesDB.addCategory(ec2);
		Equipment fe1 = new Equipment( "a111",  ec1, "401", "501");
		Equipment fe2 = new Equipment( "a222",  ec1, "402", "502");
		Equipment fe3 = new Equipment( "a123",  ec2, "403", "503");
		fe1.setID(1);
		fe2.setID(2);
		fe3.setID(3);
		equipmentsDB.addEquipment(fe1);
		equipmentsDB.addEquipment(fe2);
		equipmentsDB.addEquipment(fe3);
		factory1.addEquiment(fe1);
		factory1.addEquiment(fe2);
		factory1.addEquiment(fe3);
		owner1.setMyFactoryID(factory1.getID()); 
		
		FactoryOwner owner2 = new FactoryOwner("BBB","001", "a02", "aaa");
		owner2.setID(4);
		usersDB.addUser(owner2);
		Factory factory2 = new Factory("bbb", "bbb", owner2.getID());
		factory2.setID(2);
		factoriesDB.addFactory(factory2);
		Equipment fe4 = new Equipment( "b111",  ec1, "401", "501");
		Equipment fe5 = new Equipment( "b222",  ec1, "402", "502");
		Equipment fe6 = new Equipment( "b123",  ec2, "403", "503");
		fe4.setID(4);
		fe5.setID(5);
		fe6.setID(6);
		equipmentsDB.addEquipment(fe4);
		equipmentsDB.addEquipment(fe5);
		equipmentsDB.addEquipment(fe6);
		factory2.addEquiment(fe4);
		factory2.addEquiment(fe5);
		factory2.addEquiment(fe6);
		owner2.setMyFactoryID(factory2.getID());
		
		//下面添加产品类别与产品
		ProductCategory pc1 = new ProductCategory("1类");
		ProductCategory pc2 = new ProductCategory("2类");
		pc1.setID(1);
		pc2.setID(2);
		categoriesDB.addCategory(pc1);
		categoriesDB.addCategory(pc2);
		Product p1 = new Product("产品A1",  pc1, "300*500", "不买就亏");
		Product p2 = new Product("产品A2",  pc1, "300*500", "可遇不可求");
		Product p3 = new Product("产品A3",  pc1, "300*500", "这都不买？");
		Product p4 = new Product("产品A4",  pc2, "500*300", "买买买！");
		p1.setID(1);
		p2.setID(2);
		p3.setID(3);
		p4.setID(4);
		productsDB.addProduct(p1);
		productsDB.addProduct(p2);
		productsDB.addProduct(p3);
		productsDB.addProduct(p4);
		
		//添加产能中心的可租用设备
		RentEquipment re1 = new RentEquipment("666", ec1, "333*555", "2333");
		RentEquipment re2 = new RentEquipment("999", ec2, "300*500", "快来租我");
		RentEquipment re3 = new RentEquipment("999", ec2, "500*300", "租到就是赚到");
		re1.setID(7);
		re2.setID(8);
		re3.setID(9);
		equipmentsDB.addEquipment(re1);
		equipmentsDB.addEquipment(re2);
		equipmentsDB.addEquipment(re3);
		
    }
    
    
    public  SystemManager getManager() {
		return manager;
	}

	public  FactoryDataBase getFactoriesDB() {
		return factoriesDB;
	}
	
	public  UserDataBase getUsersDB() {
		return usersDB;
	}

	public  ProductDataBase getProductsDB() {
		return productsDB;
	}

	

	public  EquipmentDataBase getEquipmentsDB() {
		return equipmentsDB;
	}

	
	public  CategoryDataBase getCategoriesDB() {
		return categoriesDB;
	}

	

	
	
    
}
