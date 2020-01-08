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
@Table(name = "tabletsqlite")
public class TabletSQLite {

	private Long id;
	private String devserialnumber;
	private Date syncdate;
	private Date updatetime;
	
	public TabletSQLite() {
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

	@Column(name = "syncdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getSyncdate() {
		return syncdate;
	}

	public void setSyncdate(Date syncdate) {
		this.syncdate = syncdate;
	}

	@Column(name = "updatetime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
