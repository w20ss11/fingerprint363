package com.chongyou.main;


import java.util.ArrayList;
import java.util.List;

import com.chongyou.mapUtil.CountPoi;
import com.chongyou.mapUtil.DBManager;
import com.chongyou.mapUtil.WifiAPInfo;
import com.chongyou.wifiAdmin.WifiAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class PositioningActivity extends Activity  {
	public double currentX;
	public double currentY;
	private ImageView iv;
	private WifiAdmin mWifiAdmin;
	private List<ScanResult> list;
	private ScanResult mScanResult;  
    public  WifiAPInfo wifiAPInfo_unknow=new WifiAPInfo();
    private CountPoi countPoi=new CountPoi();
    public DBManager dbHelper;
    private SQLiteDatabase database;
    public List<WifiAPInfo> wifiAPInfoList2;
    private boolean flag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		
        setContentView(R.layout.activity_positioning);  
        //获取布局文件中的容器
        iv=(ImageView) findViewById(R.id.iv);
        iv.setBackgroundResource(R.drawable.map);
        
        mWifiAdmin = new WifiAdmin(PositioningActivity.this); 
        mWifiAdmin.openWifi();
//        first();
        
      //首次执行导入.db文件
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        wifiAPInfoList2=queryDatabase();
        dbHelper.closeDatabase();
        
        //System.out.println("wifiAPInfoList2:--------------"+wifiAPInfoList2.toString());
        
        getList2();
        new TvThread().start();
	}
	public double[] getList2(){
        if(wifiAPInfo_unknow!=null){
        	wifiAPInfo_unknow=new WifiAPInfo();
        	wifiAPInfo_unknow.setId(000);
    	}
    	mWifiAdmin.startScan();
    	list=mWifiAdmin.getWifiList();//掃描結果
    	String mWifiInfo=null;
    	double m[]=new double[2];
    	if(list != null && list.size() > 0){
    		for(int i=0;i<list.size();i++){
    			mScanResult=list.get(i);
    			mWifiInfo=mScanResult.BSSID+"";
    			if(mScanResult.SSID.equals("Orange"))//asdf就相当于AP1
    				wifiAPInfo_unknow.setAP1((double)mScanResult.level);//mScanResult.SSID  mScanResult.level mWifiInfo
    			else if (mScanResult.SSID.equals("just")) 
    				wifiAPInfo_unknow.setAP2((double)mScanResult.level);
    			else if (mScanResult.SSID.equals("Apple")) 
    				wifiAPInfo_unknow.setAP3((double)mScanResult.level);
    			else if (mScanResult.SSID.equals("ChinaNet")) 
    				wifiAPInfo_unknow.setAP4((double)mScanResult.level);
    			else if (mScanResult.SSID.equals("CoCo")) 
        			wifiAPInfo_unknow.setAP5((double)mScanResult.level);
    		}
    		m=countPoi.count(wifiAPInfoList2,wifiAPInfo_unknow);
//    		currentX=m[0];
//    		currentY=m[1];
//    		System.out.println(m[0]);
//    		第五步 检查传过来的m[]坐标对否
//    		System.out.println(currentX+":"+(h-h_2)/65.3*currentX);
//    		System.out.println(currentY+":"+currentY/17.03*(w-w_2));
    		//Toast.makeText(this, str, 0).show();
    	}
		return m;
    }
	
	//绘制标记到地图
	public void first() {  
		  
        // 防止出现Immutable bitmap passed to Canvas constructor错误  
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),  
                R.drawable.map).copy(Bitmap.Config.ARGB_8888, true);  
        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(  
                R.drawable.c)).getBitmap();  
  
        Bitmap newBitmap = null;  
  
        newBitmap = Bitmap.createBitmap(bitmap1);  
        Canvas canvas = new Canvas(newBitmap);  
        Paint paint = new Paint();  
  
        int w = bitmap1.getWidth();  
        int h = bitmap1.getHeight();  
  
        int w_2 = bitmap2.getWidth();  
        int h_2 = bitmap2.getHeight();  
        
//        canvas.drawBitmap(bitmap2, Math.abs(w - w_2) / 2,  
//                Math.abs(h - h_2) / 2, paint);  
        System.out.println("currentX:"+(h-h_2)/65.3*currentX);
		System.out.println("currentY:"+currentY/17.03*(w-w_2));
        canvas.drawBitmap(bitmap2, (float) (currentY/17.03*(w-w_2)),  
        		(float) ((h-h_2)/65.3*currentX), paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);  
        // 存储新合成的图片  
        canvas.restore();  
  
        iv.setImageBitmap(newBitmap);  
    }
	class TvThread extends Thread{  
	        @Override  
	        public void run(){  
	            do{  
	                try{  
	                    Thread.sleep(3000);  
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

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch(msg.what){  
            case 1: 
            	double xM=0;double yM=0;
            	for(int i=0;i<5;i++){
            		 double m2[]=getList2();      //使用getSystemService(String)来取回WifiManager然后处理wifi接入，  
            		 xM=m2[0]+xM;
            		 yM=m2[1]+yM;
            	}
            	if(flag){
            		if(Math.pow(xM/5-currentX, 2)+Math.pow(yM/5-currentY, 2)>100){
            			break;
            		}
            	}
            	currentX=xM/5;
            	currentY=yM/5;
            	System.out.println("x最后的坐标是："+currentX+"；y最后的坐标是："+currentY);
                first();
                flag=true;
                	break;  
                default:  
                    break;  
            }  
              
        }  
    };
    public List<WifiAPInfo> queryDatabase(){
		if(database.isOpen()){
			Cursor cursor=database.rawQuery("select * from oldWifiinfo;", null);
			if(cursor!=null&&cursor.getCount()>0){
				List<WifiAPInfo> wifiAPInfoList=new ArrayList<WifiAPInfo>();
				while(cursor.moveToNext()){
					WifiAPInfo wifiAPInfo=new WifiAPInfo();
					wifiAPInfo.setId(cursor.getInt(0));
					wifiAPInfo.setX(Double.parseDouble(cursor.getString(1)));
					wifiAPInfo.setY(Double.parseDouble(cursor.getString(2)));
					wifiAPInfo.setAP1(Double.parseDouble(cursor.getString(3)));
					wifiAPInfo.setAP2(Double.parseDouble(cursor.getString(4)));
					wifiAPInfo.setAP3(Double.parseDouble(cursor.getString(5)));
					wifiAPInfo.setAP4(Double.parseDouble(cursor.getString(6)));
					wifiAPInfo.setAP5(Double.parseDouble(cursor.getString(7)));
					wifiAPInfoList.add(wifiAPInfo);
				}
				database.close();
				return wifiAPInfoList;
			}
			database.close();
		}
		return null;
	}
	    
}
