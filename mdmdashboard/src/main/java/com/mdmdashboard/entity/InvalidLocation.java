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
@Table(name = "invalidlocation")
public class InvalidLocation {
	private Long id;
	private String devserialnumber;
	private Integer noofworking;
	private Integer noofceiling;
	private Integer noofbeforeceiling;
	private Date updatetime;
	private Date messagedate;
	
	public InvalidLocation() {
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

	@Column(name = "devserialnumber")
	public String getDevserialnumber() {
		return devserialnumber;
	}

	public void setDevserialnumber(String devserialnumber) {
		this.devserialnumber = devserialnumber;
	}

	@Column(name = "noofworking")
	public Integer getNoofworking() {
		return noofworking;
	}

	public void setNoofworking(Integer noofworking) {
		this.noofworking = noofworking;
	}

	@Column(name = "noofceiling")
	public Integer getNoofceiling() {
		return noofceiling;
	}

	public void setNoofceiling(Integer noofceiling) {
		this.noofceiling = noofceiling;
	}

	@Column(name = "noofbeforeceiling")
	public Integer getNoofbeforeceiling() {
		return noofbeforeceiling;
	}

	public void setNoofbeforeceiling(Integer noofbeforeceiling) {
		this.noofbeforeceiling = noofbeforeceiling;
	}

	@Column(name = "updatetime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "messagedate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getMessagedate() {
		return messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

}