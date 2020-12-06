package com.chongyou.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.haha1).setOnClickListener(this);
		findViewById(R.id.haha2).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.haha1:
			Intent intent1 = new Intent();  
            intent1.setClassName("com.chongyou.main", "com.chongyou.main.WifiInfoListActivity");
            startActivity(intent1);    
            break;
		case R.id.haha2:
			Intent intent2 = new Intent();  
            intent2.setClassName("com.chongyou.main", "com.chongyou.main.PositioningActivity");
            startActivity(intent2); 
			break;
		default:
			break;
		}
	}


}
