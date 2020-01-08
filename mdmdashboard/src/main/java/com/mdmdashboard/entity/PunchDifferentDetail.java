package com.mdmdashboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PunchDifferentDetail {
	private String devserialnumber;
	private Date punchtime;
	private Date internaltime;
	
	public PunchDifferentDetail(String devserial, Date ptime, Date itime) {
		this.devserialnumber = devserial;
		this.punchtime = ptime;
		this.internaltime = itime;
	}

	public String getDevserialnumber() {
		return devserialnumber;
	}

	public void setDevserialnumber(String devserialnumber) {
		this.devserialnumber = devserialnumber;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getPunchtime() {
		return punchtime;
	}

	public void setPunchtime(Date punchtime) {
		this.punchtime = punchtime;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getInternaltime() {
		return internaltime;
	}

	public void setInternaltime(Date internaltime) {
		this.internaltime = internaltime;
	}
	
	
}