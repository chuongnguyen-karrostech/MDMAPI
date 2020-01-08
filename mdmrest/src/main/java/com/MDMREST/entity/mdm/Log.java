package com.MDMREST.entity.mdm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "log", schema = "public")
public class Log {

	private Long logid;
	private Date logdate;
	private String logsite;
	private String logmessage;
	private String logdescription;	
	private Date logcreateddate;
	
	public Log() {
		super();
	}

	public Log(Long logid) {
		super();
		this.logid = logid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	public Long getLogid() {
		return logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	@Column(name = "log_date")
	@NotNull(message = "Missing log date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC")
	public Date getLogdate() {
		return logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	@Column(name = "log_site")
	@Size(max = 100, message = "Max length of site name is {max} characters")
	@NotBlank(message = "Missing site name")
	public String getLogsite() {
		return logsite;
	}

	public void setLogsite(String logsite) {
		this.logsite = logsite;
	}

	@Column(name = "log_message")
	@Size(max = 5000, message = "Max length of log message is {max} characters")
	public String getLogmessage() {
		return logmessage;
	}

	public void setLogmessage(String logmessage) {
		this.logmessage = logmessage;
	}

	@Column(name = "log_description")
	public String getLogdescription() {
		return logdescription;
	}

	public void setLogdescription(String logdescription) {
		this.logdescription = logdescription;
	}

	@Column(name = "log_created_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC")
	public Date getLogcreateddate() {
		return logcreateddate;
	}

	public void setLogcreateddate(Date logcreateddate) {
		this.logcreateddate = logcreateddate;
	}
}
