package com.MDMREST.entity.mdm;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "url", schema = "public")
public class Url {

	private Integer urlid;
	private String urlpath;
	private String urlversion;
	private String urltype;
	private Integer siteid;
	private Integer urlfilesize;
	private Site site;

	public Url() {
		super();
	}

	public Url(Integer urlid) {
		super();
		this.urlid = urlid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "url_id")
	public Integer getUrlid() {
		return urlid;
	}

	public void setUrlid(Integer urlid) {
		this.urlid = urlid;
	}

	@Column(name = "url_path")
	@URL(message = "Url path is not a valid url")
	@NotBlank(message = "Missing url path")
	@Size(max = 500, message = "Max length of url path is {max} characters")
	public String getUrlpath() {
		return urlpath;
	}

	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}

	@Column(name = "url_version", unique = true)
	@NotBlank(message = "Missing version")
	@Size(max = 20, message = "Max length of version is {max} characters")
	public String getUrlversion() {
		return urlversion;
	}

	public void setUrlversion(String urlversion) {
		this.urlversion = urlversion;
	}

	@Column(name = "url_type", unique = true)
	@NotBlank(message = "Missing type")
	@Size(max = 20, message = "Max length of type is {max} characters")
	public String getUrltype() {
		return urltype;
	}

	public void setUrltype(String urltype) {
		this.urltype = urltype;
	}

	@Column(name = "site_id", unique = true)
	@NotNull(message = "Missing site id")
	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}
		
	@Column(name = "url_file_size")
	@Min(value=0, message="File size must be greater than or equal 0")	
	public Integer getUrlfilesize() {
		return urlfilesize;
	}

	public void setUrlfilesize(Integer urlfilesize) {
		this.urlfilesize = urlfilesize;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", nullable = false, insertable = false, updatable = false)	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public HashMap<String, Object> ZipInfo(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("url", this.urlpath);
		map.put("version", this.urlversion);
		map.put("fileType", this.urltype);
		map.put("fileSize", this.urlfilesize);
		return map;
	}		
}
