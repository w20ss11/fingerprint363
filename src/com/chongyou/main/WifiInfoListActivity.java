package com.chongyou.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chongyou.wifiAdmin.SerializableMap;
import com.chongyou.wifiAdmin.WifiAdmin;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class WifiInfoListActivity extends Activity implements OnClickListener {
    private ListView lv;
    private WifiAdmin mWifiAdmin;  
    private List<ScanResult> list;  
    private ScanResult mScanResult;  
    List<Map<String, String>> data=new ArrayList<Map<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_info_list);
		
		mWifiAdmin = new WifiAdmin(WifiInfoListActivity.this);  
	    lv=(ListView) findViewById(R.id.lv);
	    findViewById(R.id.start).setOnClickListener(this);  
	    findViewById(R.id.stop).setOnClickListener(this); 
	    getList();
	    new TvThread().start(); 
	}
	public void getList(){
        if(data!=null){
    		data=new ArrayList<Map<String,String>>();
    	}
    	mWifiAdmin.startScan();
    	list=mWifiAdmin.getWifiList();
    	String mWifiInfo=null;
    	if(list != null && list.size() > 0){
    		for(int i=0;i<list.size();i++){
    			mScanResult=list.get(i);
    			mWifiInfo=mScanResult.BSSID+"  ";
    			if(mScanResult.SSID.equals("Pear")
    					|mScanResult.SSID.equals("Apple")
    					|mScanResult.SSID.equals("Banana")
    					|mScanResult.SSID.equals("Orange")
    					|mScanResult.SSID.equals("CoCo")){
	    			Map<String, String>map=new HashMap<String, String>();
	    			map.put("wifiName", mScanResult.SSID);
	    			map.put("wifiStrength", ""+mScanResult.level);
	    			map.put("MacAddress",mWifiInfo);
	    			data.add(map);
    			}
    		}
    	}
        SimpleAdapter adapter=new SimpleAdapter(this, 
    			data, 
    			R.layout.wifi_item, 
    			new String[]{"wifiName","wifiStrength","MacAddress"}, 
    			new int[]{R.id.wifi_name,R.id.wifi_strength,R.id.macaddress});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map1=data.get(position);
				Intent data1=new Intent(WifiInfoListActivity.this,SaveActivity.class);
				SerializableMap tmpmap=new SerializableMap();  
	            tmpmap.setMap(map1);  
	            Bundle bundle=new Bundle();
	            bundle.putSerializable("wifiInfo", tmpmap);  
	            data1.putExtras(bundle);  
	            startActivity(data1);
			}
		});
		
    }
    
    class TvThread extends Thread{  
        @Override  
        public void run(){  
            do{  
                try{  
                    Thread.sleep(5000);  
                    Message msg = new Message();  
                    msg.what = 1;//what，int类型，未定义的消息，以便接收消息者可以鉴定消息是关于什么的。每个句柄都有自己的消息命名空间，不必担心冲突   
                    mHandler.sendMessage(msg);  
                }  
                catch (InterruptedException e){  
                    e.printStackTrace();  
                }  
            }while (true);  
              
        }  
          
    }  
    
    private Handler mHandler = new Handler(){  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch(msg.what){  
            case 1:  
                getList();      //使用getSystemService(String)来取回WifiManager然后处理wifi接入，  
                	break;  
                default:  
                    break;  
            }  
              
        }  
    };

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.start:
     		mWifiAdmin.openWifi();
				Toast.makeText(WifiInfoListActivity.this, "当前wifi状态："+mWifiAdmin.checkState(), 1).show();
				break;
        case R.id.stop:
				mWifiAdmin.closeWifi();
				Toast.makeText(WifiInfoListActivity.this, "当前wifi状态："+mWifiAdmin.checkState(), 1).show();
				break;
			default:
				break;
		}
	} 
}
 
