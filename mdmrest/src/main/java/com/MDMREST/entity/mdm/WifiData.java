package com.MDMREST.entity.mdm;

public class WifiData
{
    private Integer tempIndex;
    private Float wifilatitude;
    private Float wifilongitude;
    private String wifipassword;
    private Float wifiradius;
    private String wifisecuritytype;
    private String wifissid;
    public WifiData() {
    	super();
    }
	public Integer getTempIndex() {
		return tempIndex;
	}
	public void setTempIndex(Integer tempIndex) {
		this.tempIndex = tempIndex;
	}
	public Float getWifilatitude() {
		return wifilatitude;
	}
	public void setWifilatitude(Float wifilatitude) {
		this.wifilatitude = wifilatitude;
	}
	public Float getWifilongitude() {
		return wifilongitude;
	}
	public void setWifilongitude(Float wifilongitude) {
		this.wifilongitude = wifilongitude;
	}
	public String getWifipassword() {
		return wifipassword;
	}
	public void setWifipassword(String wifipassword) {
		this.wifipassword = wifipassword;
	}
	public Float getWifiradius() {
		return wifiradius;
	}
	public void setWifiradius(Float wifiradius) {
		this.wifiradius = wifiradius;
	}
	public String getWifisecuritytype() {
		return wifisecuritytype;
	}
	public void setWifisecuritytype(String wifisecuritytype) {
		this.wifisecuritytype = wifisecuritytype;
	}
	public String getWifissid() {
		return wifissid;
	}
	public void setWifissid(String wifissid) {
		this.wifissid = wifissid;
	}
    
}