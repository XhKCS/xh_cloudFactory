package com.factory.dao;

import com.factory.util.DBController;
import com.factory.util.DataBase;

// 所有用到数据的地方都通过Dao的静态方法获得DataBase，保证程序中使用的都只是同一个DataBase
public class Dao {
	private static DBController dbc = new DBController(); //创建时就已经完成初始化
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
	
