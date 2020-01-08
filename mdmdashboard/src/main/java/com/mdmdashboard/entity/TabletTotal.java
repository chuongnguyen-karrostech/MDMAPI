package com.mdmdashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect(
    "select workdate, count(*) as tablets from (" +
    "select distinct devserialnumber,to_char(updatetime,'yyyy-MM-dd') as workdate, date(updatetime) from tabletsqlite " + 
    "where date(updatetime)<=CURRENT_DATE and date(updatetime)>=CURRENT_DATE - 14 " +
    "union " +
    "select distinct devserialnumber,'Others' as workdate, date(updatetime) from tabletsqlite " +
    "where date(updatetime)<CURRENT_DATE - 14) as dt " + 
    "group by workdate order by workdate"
)

public class TabletTotal {
	private String reportdate;
	private Integer tablets;
		
	public TabletTotal() {
		super();
	}

	@Id
	@Column(name = "workdate")
	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	@Column(name = "tablets")
	public Integer getTablets() {
		return tablets;
	}

	public void setTablets(Integer tablets) {
		this.tablets = tablets;
	}
		
}