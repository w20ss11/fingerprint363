package com.chongyou.wifiAdmin;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1897387837442052912L;
	private Map<String,String> map;  
    public Map<String, String> getMap()  
    {  
        return map;  
    }  
    public void setMap(Map<String, String> map)  
    {  
        this.map=map;  
    }  
}  
