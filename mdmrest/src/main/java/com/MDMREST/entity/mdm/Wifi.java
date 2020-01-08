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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "wifi", schema = "public")
public class Wifi {

	private Integer wifiid;
	private String wifissid;
	private String wifipassword;
	private String wifisecuritytype;
	private Integer siteid;
	private Float wifilongitude;
	private Float wifilatitude;
	private Float wifiradius;
	private Site site;

	public Wifi() {
		super();
	}

	public Wifi(Integer wifiid) {
		super();
		this.wifiid = wifiid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wifi_id")
	public Integer getWifiid() {
		return wifiid;
	}

	public void setWifiid(Integer wifiid) {
		this.wifiid = wifiid;
	}

	@Column(name = "wifi_ssid")
	@NotBlank(message = "Missing wifi SSID")
	@Size(max = 50, message = "Max length of wifi SSID is {max} characters")
	public String getWifissid() {
		return wifissid;
	}

	public void setWifissid(String wifissid) {
		this.wifissid = wifissid;
	}

	@Column(name = "wifi_password")	
	@Size(max = 50, message = "Max length of wifi password is {max} characters")
	public String getWifipassword() {
		return wifipassword;
	}

	public void setWifipassword(String wifipassword) {
		this.wifipassword = wifipassword;
	}

	@Column(name = "wifi_securitytype")
	@NotBlank(message = "Missing wifi security type password")
	@Size(max = 50, message = "Max length of wifi security type is {max} characters")
	public String getWifisecuritytype() {
		return wifisecuritytype;
	}

	public void setWifisecuritytype(String wifisecuritytype) {
		this.wifisecuritytype = wifisecuritytype;
	}

	@Column(name = "site_id")
	@NotNull(message = "Missing site id")
	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}
	
	@Column(name = "wifi_longitude")	
	@NotNull(message = "Missing wifi longitude")	
	@DecimalMax(value = "180", message = "Longitude must be between -180 to 180")
	@DecimalMin(value = "-180", message = "Longitude must be between -180 to 180")
	public Float getWifilongitude() {
		return wifilongitude;
	}

	public void setWifilongitude(Float wifilongitude) {
		this.wifilongitude = wifilongitude;
	}

	@Column(name = "wifi_latitude")
	@NotNull(message = "Missing wifi latitude")	
	@DecimalMax(value = "90", message = "Latitude must be between -90 to 90")
	@DecimalMin(value = "-90", message = "Latitude must be between -90 to 90")	
	public Float getWifilatitude() {
		return wifilatitude;
	}

	public void setWifilatitude(Float wifilatitude) {
		this.wifilatitude = wifilatitude;
	}

	@Column(name = "wifi_radius")
	@NotNull(message = "Missing wifi radius")
	public Float getWifiradius() {
		return wifiradius;
	}

	public void setWifiradius(Float wifiradius) {
		this.wifiradius = wifiradius;
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
		map.put("wifissid", this.wifissid);
		map.put("wifipassword", this.wifipassword);
		map.put("wifisecuritytype", this.wifisecuritytype);		
		map.put("wifilatitude", this.wifilatitude);
		map.put("wifilongitude", this.wifilongitude);
		map.put("wifiradius", this.wifiradius);
		return map;
	}
}
