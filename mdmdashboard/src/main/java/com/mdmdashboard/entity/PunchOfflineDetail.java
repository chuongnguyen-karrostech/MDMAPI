package com.mdmdashboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PunchOfflineDetail {
	private String devserialnumber;
	private Date punchtime;
	private Date sendtime;
	
	public PunchOfflineDetail(String devserial, Date ptime, Date stime) {
		this.devserialnumber = devserial;
		this.punchtime = ptime;
		this.sendtime = stime;
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
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	
}