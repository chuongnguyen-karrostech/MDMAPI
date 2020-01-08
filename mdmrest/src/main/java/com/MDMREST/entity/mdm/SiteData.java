package com.MDMREST.entity.mdm;

public class SiteData
{
    private String sitename;
    private String sitetenanid;
    private Integer tempIndex;
    public SiteData() {
    	super();
    }
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getSitetenanid() {
		return sitetenanid;
	}
	public void setSitetenanid(String sitetenanid) {
		this.sitetenanid = sitetenanid;
	}
	public Integer getTempIndex() {
		return tempIndex;
	}
	public void setTempIndex(Integer tempIndex) {
		this.tempIndex = tempIndex;
	}
    
}