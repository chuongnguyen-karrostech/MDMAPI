package com.MDMREST.entity.mdm;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "site", schema = "public", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "site_tenanid" }, name = "Tenanid's id"),
		@UniqueConstraint(columnNames = { "site_name" }, name = "Site's name") })
public class Site {

	private Integer siteid;
	private String sitetenanid;
	private String sitename;
	private String forwardurl;
	private List<Wifi> wifis;
	private List<Tablet> tablets;
	private List<Url> urls;
	private String dashboardurl;

	@Column(name = "site_dashboardurl")
	@Size(max = 500)
	@JsonIgnore
	public String getDashboardurl() {
		return dashboardurl;
	}

	public void setDashboardurl(String dashboardurl) {
		this.dashboardurl = dashboardurl;
	}

	public Site() {
		super();
	}

	public Site(Integer siteid) {
		super();
		this.siteid = siteid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "site_id")
	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	@Column(name = "site_tenanid")
	@Size(max = 50, message = "Max length of tenanid is {max} characters")
	public String getSitetenanid() {
		return sitetenanid;
	}

	public void setSitetenanid(String sitetenanid) {
		this.sitetenanid = sitetenanid;
	}

	@Column(name = "site_name", unique = true)
	@NotBlank(message = "Site name can not be blank")
	@Size(max = 50, message = "Max length of site name is {max} characters")
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	// @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
	// mappedBy="site")
	// @JoinColumn(name = "site_id", referencedColumnName = "site_id",
	// insertable = true, updatable = true)
	// @JsonIgnoreProperties(value = { "site" })
	// @NotNull(message = "Missing site's wifi configuration")
	// @PrimaryKeyJoinColumn
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = { "site" })
	@JsonIgnore
	public List<Wifi> getWifis() {
		return wifis;
	}

	public void setWifis(List<Wifi> wifis) {
		this.wifis = wifis;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = { "site" })
	@JsonIgnore
	public List<Tablet> getTablets() {
		return tablets;
	}

	public void setTablets(List<Tablet> tablets) {
		this.tablets = tablets;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = { "site" })
	@JsonIgnore
	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
	
	public HashMap<String, Object> ZipInfo(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("sitename", this.sitename);
		map.put("sitetenanid", this.sitetenanid);				
		return map;
	}
	
	@Column(name = "site_forwardurl")
	@Size(max = 500)
	@JsonIgnore
	public String getforwardurl() {
		if (this.forwardurl == null) return "";
		return this.forwardurl.trim();
	}

	public void setforwardurl(String urlpath) {
		this.forwardurl = urlpath;
	}

}
