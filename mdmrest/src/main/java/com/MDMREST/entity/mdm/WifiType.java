package com.MDMREST.entity.mdm;

import java.util.List;

public class WifiType
{
    private List<WifiData> data;
    public WifiType() {
    	super();
    }
	public List<WifiData> getData() {
		return data;
	}
	public void setData(List<WifiData> data) {
		this.data = data;
	}
    
}