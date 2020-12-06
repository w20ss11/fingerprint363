package com.chongyou.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chongyou.dao.WifiDao;
import com.chongyou.db.WifiInfoSQLiteOpenHelper;
import com.chongyou.wifiAdmin.SerializableMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SaveActivity extends Activity implements OnClickListener {
	public static int RESULT_CODE=2;
	private TextView tv_save;
	private Map<String, String> map;
	private ListView lv_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		lv_save=(ListView) findViewById(R.id.lv_save);
		tv_save=(TextView) findViewById(R.id.tv_save);
		findViewById(R.id.but01).setOnClickListener(this);
		findViewById(R.id.but02).setOnClickListener(this);
		findViewById(R.id.but03).setOnClickListener(this);
		
		Bundle bundle = getIntent().getExtras();  
        SerializableMap serializableMap = (SerializableMap) bundle  
                .get("wifiInfo");  
        map = serializableMap.getMap();
        tv_save.setText("wifi名称："+map.get("wifiName")+" \nwifi强度："
        +map.get("wifiStrength")+"\nMac地址："+map.get("MacAddress"));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
       case R.id.but01:
    	    WifiInfoSQLiteOpenHelper openHelper=new WifiInfoSQLiteOpenHelper(this);
   			openHelper.getWritableDatabase();
   			insert();
			break;
       case R.id.but02:
    	    queryAll();
			break;
       case R.id.but03:
    	    deleteAll();
			break;
		default:
			break;
		}
	}
	
	public void insert(){
		WifiDao dao=new WifiDao(getBaseContext());
		dao.insert(map);
		Toast.makeText(this, "存储成功", 0).show();
	}
	public void queryAll(){
		WifiDao dao=new WifiDao(getBaseContext());
		List<Map<String, String>> data2=new ArrayList<Map<String,String>>();
		data2=dao.queryAll();
		if(data2!=null){
			SimpleAdapter adapter=new SimpleAdapter(this, 
	    			data2, 
	    			R.layout.savewifi_item, 
	    			new String[]{"wifiName","wifiStrength","MacAddress"}, 
	    			new int[]{R.id.wifi_name2,R.id.wifi_strength2,R.id.macaddress2});
			lv_save.setAdapter(adapter);
		}
	}
	
	private void deleteAll() {
		WifiDao dao=new WifiDao(getBaseContext());
		dao.deleteAll();
		Toast.makeText(this, "删完喽", 0).show();
	}
}
