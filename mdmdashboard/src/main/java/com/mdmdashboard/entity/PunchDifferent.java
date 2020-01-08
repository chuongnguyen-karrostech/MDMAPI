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
    "select workdate, sum(l1) as level1, sum(l2) as level2, sum(l3) as level3, sum(l4) as level4, sum(l5) as level5 " +
    "from (select date(punchtime) as workdate, case when extract(EPOCH from punchtime) - extract(EPOCH from internaltime)<=60 then 1 else 0 end as l1, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from internaltime)>60 and extract(EPOCH from punchtime) - extract(EPOCH from internaltime)<=300 then 1 else 0 end as l2, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from internaltime)>300 and extract(EPOCH from punchtime) - extract(EPOCH from internaltime)<=1800 then 1 else 0 end as l3, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from internaltime)>1800 and extract(EPOCH from punchtime) - extract(EPOCH from internaltime)<=3600 then 1 else 0 end as l4, " +
    "case when extract(EPOCH from punchtime) - extract(EPOCH from internaltime)>1800 then 1 else 0 end as l5 from punchlog) as dt group by workdate"
)

public class PunchDifferent {
	private Date reportdate;
	private Integer level1;
	private Integer level2;
	private Integer level3;
	private Integer level4;
	private Integer level5;
	
	public PunchDifferent() {
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

	@Column(name = "level1")
	public Integer getLevel1() {
		return level1;
	}

	public void setLevel1(Integer level1) {
		this.level1 = level1;
	}

	@Column(name = "level2")
	public Integer getLevel2() {
		return level2;
	}

	public void setLevel2(Integer level2) {
		this.level2 = level2;
	}

	@Column(name = "level3")
	public Integer getLevel3() {
		return level3;
	}

	public void setLevel3(Integer level3) {
		this.level3 = level3;
	}

	@Column(name = "level4")
	public Integer getLevel4() {
		return level4;
	}

	public void setLevel4(Integer level4) {
		this.level4 = level4;
	}

	@Column(name = "level5")
	public Integer getLevel5() {
		return level5;
	}

	public void setLevel5(Integer level5) {
		this.level5 = level5;
	}
	
}