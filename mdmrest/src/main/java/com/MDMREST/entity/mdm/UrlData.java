package com.MDMREST.entity.mdm;

public class UrlData
{
    private Integer tempIndex;
    private Integer urlfilesize;
    private String urlpath;
    private String urltype;
    private String urlversion;
    public UrlData() {
    	super();
    }
	public Integer getTempIndex() {
		return tempIndex;
	}
	public void setTempIndex(Integer tempIndex) {
		this.tempIndex = tempIndex;
	}
	public Integer getUrlfilesize() {
		return urlfilesize;
	}
	public void setUrlfilesize(Integer urlfilesize) {
		this.urlfilesize = urlfilesize;
	}
	public String getUrlpath() {
		return urlpath;
	}
	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}
	public String getUrltype() {
		return urltype;
	}
	public void setUrltype(String urltype) {
		this.urltype = urltype;
	}
	public String getUrlversion() {
		return urlversion;
	}
	public void setUrlversion(String urlversion) {
		this.urlversion = urlversion;
	}
    
}