package com.MDMREST.entity.mdm;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tablet", schema = "public", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "tablet_serialno" }, name = "Serial No") })


@NamedNativeQuery(name = "update_tablet", query = " "
			+ " update tablet set site_id=:siteid"
			+ " where tablet_id=:tabletid ")
	
public class Tablet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tabletid;
	private String tabletserialno;
	private Integer siteid;
	private Site site;

	public Tablet() {
		super();
	}

	public Tablet(Integer tabletid) {
		super();
		this.tabletid = tabletid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tablet_id")
	public Integer getTabletid() {
		return tabletid;
	}

	public void setTabletid(Integer tabletid) {
		this.tabletid = tabletid;
	}

	@Column(name = "tablet_serialno", unique = true)
	@NotBlank(message = "Missing tablet serial number")
	@Size(max = 50, message = "Max length of tablet serial number is {max} characters")
	public String getTabletserialno() {
		return tabletserialno;
	}

	public void setTabletserialno(String tabletserialno) {
		this.tabletserialno = tabletserialno;
	}

	@Column(name = "site_id", unique = true)
	@NotNull(message = "Missing site id")
	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", nullable = false, insertable = false, updatable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}
