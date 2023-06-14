package com.factory.dao;

import com.factory.util.DBController;
import com.factory.util.DataBase;


public class Dao {
	private static DBController dbc = new DBController(); //创建时就已经完成初始化了
	//该数据库一定要定义为静态的！保证系统中的数据都只来自这一个数据库！
	
    // 文件操作需要

	public static DataBase getDb() {

		return dbc.getDb();
	}
	
	public Dao() {}
	
	public static void save() {
		dbc.save();
	}
	
	
}
	
