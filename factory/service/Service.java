package com.factory.service;

import javax.swing.JOptionPane;

import com.factory.dao.CategoryDataBase;
import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.ProductDataBase;
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
import com.factory.model.User;
import com.factory.util.DataBase;

//service层需要从view层获取到用户输入的一些数据，之后与数据层交互实现一些功能（主要是各种添加功能）
public class Service {
	
	private static Service singletonInstance;
	private Service() {}
	public static synchronized Service getSingletonInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Service();
		}
		return singletonInstance;
	}
	
	//经销商注册
	public Seller registerSeller(String account, String password, String name, 
			String connectionWay) {
		DataBase db = Dao.getDb();	
		User check = db.getUsersDB().getUserByAccount(account);
		if (check != null) {
			JOptionPane.showMessageDialog(null, "该账号已存在！请重新设置账号！");
			return null;
		}
		Seller newSeller = new Seller(account, password, name, connectionWay);
		int id = 1; //系统管理员id
		for (User k : db.getUsersDB().getAllUsers()) {
			if (k.getID() > id) {
				id = k.getID();
			}
		}
		newSeller.setID(id+1);
		db.getUsersDB().addUser(newSeller);
		
		return newSeller;
	}
	
	//云工厂管理员注册
	public FactoryOwner registerFO(String account, String password, String name, 
			String connectionWay, String factoryName, String factoryDescrip) {
		DataBase db = Dao.getDb();	
		User check = db.getUsersDB().getUserByAccount(account);
		if (check != null) {
			JOptionPane.showMessageDialog(null, "该账号已存在！请重新设置账号！");
			return null;
		}
		FactoryOwner newOwner = new FactoryOwner(account, password, name, connectionWay);
		int id = 1; //要记得设置ID
		for (User k : db.getUsersDB().getAllUsers()) {
			if (k.getID() > id) {
				id = k.getID();
			}
		}
		newOwner.setID(id+1);
		db.getUsersDB().addUser(newOwner);
		
		Factory factory = new Factory(factoryName, factoryDescrip, newOwner.getID());
		//还要记得自动分配的工厂也要设置自己的ID
		id = 1;
		for (Factory k : db.getFactoriesDB().getFactories()) {
			if (k.getID() > id) {
				id = k.getID();
			}
		}
		factory.setID(id+1);
		db.getFactoriesDB().addFactory(factory);
		
		//让每个分配给工厂管理员的云工厂自带默认设备，要记得默认设备也得设置ID，也得添加到相应DataBase中
      	EquipmentCategory cate1 = Dao.getDb().getCategoriesDB().getECByName("一类");
      	Equipment defaultEquip = new Equipment("默认设备", cate1, "3535", "工厂自带的默认设备");
      	id = 1;
		for (Equipment k : db.getEquipmentsDB().getEquipments()) {
			if (k.getID() > id) {
				id = k.getID();
			}
		}
		defaultEquip.setID(id+1);
		db.getEquipmentsDB().addEquipment(defaultEquip); //别忘记
		
     	defaultEquip.setBelongFactoryID(factory.getID());
      	factory.addEquiment(defaultEquip);
		
		factory.setOwnerID(newOwner.getID());
		newOwner.setMyFactoryID(factory.getID());
		
		return newOwner;
	}
	
	//用户登录
	public User logIn(String account, String password) {
		DataBase db = Dao.getDb();	
		
		SystemManager manager = db.getManager();
		if (manager.getAccount().equals(account) && manager.getPassword().equals(password)) {
			return manager;
		}
		for (User k : db.getUsersDB().getAllUsers()) {
			if (k.getAccount().equals(account) && k.getPassword().equals(password) ) {
				return k;
			}
		}
		
		return null;
	}
	
	public int addEquipmentToFactory(String name, String cateName, String size, 
			String descrip, Factory factory) {
		EquipmentCategory cate = Dao.getDb().getCategoriesDB().getECByName(cateName);
		if (cate != null && factory != null) {
			Equipment e = new Equipment(name, cate, size, descrip);
			EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
			int id = 1;
			for (Equipment k : edb.getEquipments()) {
				if (k.getID() > id) {
					id = k.getID();
				}
			}
			e.setID(id+1);
			edb.addEquipment(e);
			//工厂设备增加的操作
			e.setBelongFactoryID(factory.getID());
			factory.addEquiment(e);
			JOptionPane.showMessageDialog(null, "添加成功！");
			return 0;
		}
		else {
			JOptionPane.showMessageDialog(null, "添加失败！");
			return -1;
		}
		
	}
	
	public int addREToCenter(String name, String cateName, String size, String descrip) {
		EquipmentCategory cate = Dao.getDb().getCategoriesDB().getECByName(cateName);
		if (cate != null) {
			RentEquipment re = new RentEquipment(name, cate, size, descrip);
			EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
			int id = 1;
			for (Equipment k : edb.getEquipments()) {
				if (k.getID() > id) {
					id = k.getID();
				}
			}
			re.setID(id+1);
			edb.addEquipment(re);
			JOptionPane.showMessageDialog(null, "添加成功！");
			return 0;
		}
		else {
			JOptionPane.showMessageDialog(null, "添加失败！");
			return -1;
		}
	}
	
	public int addProduct(String name, String cateName, String size, String des) {
		ProductCategory cate = Dao.getDb().getCategoriesDB().getPCByName(cateName);
		if (cate != null) {
			Product p = new Product(name, (ProductCategory)cate, size, des);
			ProductDataBase pdb = Dao.getDb().getProductsDB();
			int id = 1;
			for (Product k : pdb.getProducts()) {
				if (k.getID() > id) {
					id = k.getID();
				}
			}
			p.setID(id+1);
			pdb.addProduct(p);
			JOptionPane.showMessageDialog(null, "添加成功！");
			return 0;
		}
		else {
			JOptionPane.showMessageDialog(null, "添加失败！");
			return -1;
		}
	}
	
	public int addProductCategory(String pcName) {
		CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
		ProductCategory check = cdb.getPCByName(pcName);
		if (check != null) {
			JOptionPane.showMessageDialog(null, "该名称的产品类型已存在！请重新设置名称！");
			return -1;
		}
		else {
			ProductCategory pc = new ProductCategory(pcName);
			int id = 1;
			for (Category k : cdb.getCategories()) {
				if (k.getID() > id) {
					id = k.getID();
				}
			}
			pc.setID(id+1);
			cdb.addCategory(pc);
			JOptionPane.showMessageDialog(null, "添加成功！");
			return 0;
		}
	}
	
	public int addEquipmentCategory(String ecName) {
		CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
		EquipmentCategory check = cdb.getECByName(ecName);
		if (check != null) {
			JOptionPane.showMessageDialog(null, "该名称的设备类型已存在！请重新设置名称！");
			return -1;
		}
		else {
			EquipmentCategory ec = new EquipmentCategory(ecName);
			int id = 1;
			for (Category k : cdb.getCategories()) {
				if (k.getID() > id) {
					id = k.getID();
				}
			}
			ec.setID(id+1);
			cdb.addCategory(ec);
			JOptionPane.showMessageDialog(null, "添加成功！");
			return 0;
		}
	}
	
	
}
