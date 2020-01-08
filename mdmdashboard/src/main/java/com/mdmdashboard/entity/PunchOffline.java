package com.mdmdashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Immutable
@Subselect(
    "select workdate, sum(online) as onlines, sum(offline) as offlines " +
    "from (select date(punchtime) as workdate, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from sendtime)<=60 then 1 else 0 end as online, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from sendtime)>60 then 1 else 0 end as offline from punchlog) as dt group by workdate"
)

public class PunchOffline {
	private Date reportdate;
	private Integer onlines;
	private Integer offlines;
	
	public PunchOffline() {
		super();
	}

	@Id
	@Column(name = "workdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getReportdate() {
		return reportdate;
	}

	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}

	@Column(name = "onlines")
	public Integer getOnlines() {
		return onlines;
	}

	public void setOnlines(Integer onlines) {
		this.onlines = onlines;
	}

	@Column(name = "offlines")
	public Integer getOfflines() {
		return offlines;
	}

	public void setOfflines(Integer offlines) {
		this.offlines = offlines;
	}
	
	
}