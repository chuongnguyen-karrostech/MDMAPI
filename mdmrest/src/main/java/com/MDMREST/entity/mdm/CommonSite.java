package com.MDMREST.entity.mdm;

public class CommonSite
{
    private SiteType site;
    private WifiType wifi;
    private UrlType url;
    public CommonSite() {
    	super();
    }
	public SiteType getSite() {
		return site;
	}
	public void setSite(SiteType site) {
		this.site = site;
	}
	public WifiType getWifi() {
		return wifi;
	}
	public void setWifi(WifiType wifi) {
		this.wifi = wifi;
	}
	public UrlType getUrl() {
		return url;
	}
	public void setUrl(UrlType url) {
		this.url = url;
	}
    
}











