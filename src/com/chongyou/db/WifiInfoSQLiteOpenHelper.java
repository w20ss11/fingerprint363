package com.chongyou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WifiInfoSQLiteOpenHelper extends SQLiteOpenHelper {

	//数据帮助类  用于创建和管理数据库
	public WifiInfoSQLiteOpenHelper(Context context) {
		super(context, "wifiInfo.db", null, 1);
		// TODO 自动生成的构造函数存根
	}
//数据创建时回调此方法 初始化一些表
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db对象可以操作数据库
		String sql="create table wifiInfo(_id integer primary key,wifiName varchar(20),wifiStrength integer,MacAddress varchar(20));";
		db.execSQL(sql);
	}
//数据库版本号更新时回调此方法 更新数据库的内容（删除表 添加表 修改表）
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
