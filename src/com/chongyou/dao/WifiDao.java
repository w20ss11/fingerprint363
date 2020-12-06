package com.chongyou.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chongyou.db.WifiInfoSQLiteOpenHelper;

public class WifiDao {
	private WifiInfoSQLiteOpenHelper mOpenHelper;

	public WifiDao(Context context){
		mOpenHelper = new WifiInfoSQLiteOpenHelper(context);
	}
	public void insert(Map<String, String> map){
		SQLiteDatabase db=mOpenHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("insert into wifiInfo(wifiName,wifiStrength,MacAddress)values(?,?,?);",new Object[]{map.get("wifiName"),map.get("wifiStrength"),map.get("MacAddress")});
			db.close();
		}
	}
	public List<Map<String, String>> queryAll(){
		SQLiteDatabase db=mOpenHelper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor=db.rawQuery("select * from wifiInfo;", null);
			if(cursor!=null&&cursor.getCount()>0){
				List<Map<String, String>> wifiQueryList=new ArrayList<Map<String,String>>();
				while(cursor.moveToNext()){
					Map<String, String> wifiQuery=new HashMap<String, String>();
					wifiQuery.put("_id", cursor.getInt(0)+"");
					wifiQuery.put("wifiName",cursor.getString(1));
					wifiQuery.put("wifiStrength",cursor.getString(2));
					wifiQuery.put("MacAddress",cursor.getString(3));
					System.out.println("aaa");
					wifiQueryList.add(wifiQuery);
				}
				db.close();
				return wifiQueryList;
			}
			db.close();
		}
		return null;
	}
	
	public void deleteAll(){
		SQLiteDatabase db=mOpenHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from wifiInfo;");
			db.close();
		}
	}
}
