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
@Table(name = "wificellularusage")
public class WifiCellularUsage {
	private Long id;
	private String devserialnumber;
	private Double totaldata ;
	private String rectype;
	private Date repdate;
	
	public WifiCellularUsage() {
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

	@Column(name = "totaldata")
	public Double getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Double totaldata) {
		this.totaldata = totaldata;
	}

	@Column(name = "rectype")
	public String getRectype() {
		return rectype;
	}

	public void setRectype(String rectype) {
		this.rectype = rectype;
	}

	@Column(name = "repdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getRepdate() {
		return repdate;
	}

	public void setRepdate(Date repdate) {
		this.repdate = repdate;
	}
	
	
}