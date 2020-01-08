package com.MDMREST.entity.mdm;


public class Summary {
	 
    private Long total_downloaded;
    private Long total_upgraded;

    public Summary(Long downloaded, Long upgraded) {
        this.total_downloaded = downloaded;
        this.total_upgraded = upgraded;
    }
    
    public Long gettotal_downloaded() {
    	return this.total_downloaded;
    }
    public void settotal_downloaded(Long downloaded) {
    	this.total_downloaded = downloaded;
    }
    
    public Long gettotal_upgraded() {
    	return this.total_upgraded;
    }
    public void settotal_upgraded(Long upgraded) {
    	this.total_upgraded = upgraded;
    }
    
}