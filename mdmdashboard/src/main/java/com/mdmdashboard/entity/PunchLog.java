package com.mdmdashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "punchlog")
public class PunchLog {
	private Long id;
	private Integer activitycode;
	private String driverid;
	private Date punchtime;
	private String billingid;
	private Float longitude;
	private Float latitude;
	private Float lastsynclongitude;
	private Float lastsynclatitude;
	private Date lastsynctime;
	private Date receivetime;
	private Date internaltime;
	private Date sendtime;
	private String devserialnumber;
	private String busid;
	private String lastsynctype;
	
	public PunchLog() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "activitycode")
	public Integer getActivitycode() {
		return activitycode;
	}

	public void setActivitycode(Integer activitycode) {
		this.activitycode = activitycode;
	}

	@Column(name = "driverid")
	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	@Column(name = "punchtime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getPunchtime() {
		return punchtime;
	}

	public void setPunchtime(Date punchtime) {
		this.punchtime = punchtime;
	}

	@Column(name = "billingid")
	public String getBillingid() {
		return billingid;
	}

	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}

	@Column(name = "longitude")
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@Column(name = "lastsynclongitude")
	public Float getLastsynclongitude() {
		return lastsynclongitude;
	}

	public void setLastsynclongitude(Float lastsynclongitude) {
		this.lastsynclongitude = lastsynclongitude;
	}

	@Column(name = "lastsynclatitude")
	public Float getLastsynclatitude() {
		return lastsynclatitude;
	}

	public void setLastsynclatitude(Float lastsynclatitude) {
		this.lastsynclatitude = lastsynclatitude;
	}

	@Column(name = "lastsynctime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getLastsynctime() {
		return lastsynctime;
	}

	public void setLastsynctime(Date lastsynctime) {
		this.lastsynctime = lastsynctime;
	}

	@Column(name = "receivetime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	@Column(name = "internaltime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getInternaltime() {
		return internaltime;
	}

	public void setInternaltime(Date internaltime) {
		this.internaltime = internaltime;
	}

	@Column(name = "sendtime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "devserialnumber")
	public String getDevserialnumber() {
		return devserialnumber;
	}

	public void setDevserialnumber(String devserialnumber) {
		this.devserialnumber = devserialnumber;
	}

	@Column(name = "busid")
	public String getBusid() {
		return busid;
	}

	public void setBusid(String busid) {
		this.busid = busid;
	}

	@Column(name = "lastsynctype")
	public String getLastsynctype() {
		return lastsynctype;
	}

	public void setLastsynctype(String lastsynctype) {
		this.lastsynctype = lastsynctype;
	}
	
	
}