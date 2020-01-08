package com.MDMREST.entity.mdm;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Immutable
@Subselect(
    "SELECT 'DRIVERPORTAL' as trktype, concat('A', trk_id::varchar(7)) as trkid, trk_id as orgtrkid, trk_tabletserial as trktabletserial, trk_updateavailable as trkupdateavailable, trk_currentversion as trkcurrentversion, " +
    "trk_downattemp as trkdownattemp, trk_timeattemp as trktimeattemp, trk_downloaded as trkdownloaded, trk_upgraded as trkupgraded, trk_wificonnect as trkwificonnect, url_version as serverversion, site_name as sitename, c1.site_id as siteid " +
    "FROM public.trkdpapk a1 JOIN public.tablet b1 ON a1.trk_tabletserial=b1.tablet_serialno JOIN public.site c1 on b1.site_id=c1.site_id LEFT JOIN public.url d1 ON c1.site_id=d1.site_id and d1.url_type='DRIVERPORTAL'" + " UNION " +
    "SELECT 'MAPS' as trktype, concat('B', trk_id::varchar(7)) as trkid, trk_id as orgtrkid, trk_tabletserial as trktabletserial, trk_updateavailable as trkupdateavailable, trk_currentversion as trkcurrentversion, " +
    "trk_downattemp as trkdownattemp, trk_timeattemp as trktimeattemp, trk_downloaded as trkdownloaded, trk_upgraded as trkupgraded, trk_wificonnect as trkwificonnect, url_version as serverversion, site_name as sitename, c2.site_id as siteid " +
    "FROM public.trkdpmap a2 JOIN public.tablet b2 ON a2.trk_tabletserial=b2.tablet_serialno JOIN public.site c2 on b2.site_id=c2.site_id LEFT JOIN public.url d2 ON c2.site_id=d2.site_id and d2.url_type='MAPS'" + " UNION " +
    "SELECT 'OSMAND' as trktype, concat('C', trk_id::varchar(7)) as trkid, trk_id as orgtrkid, trk_tabletserial as trktabletserial, trk_updateavailable as trkupdateavailable, trk_currentversion as trkcurrentversion, " +
    "trk_downattemp as trkdownattemp, trk_timeattemp as trktimeattemp, trk_downloaded as trkdownloaded, trk_upgraded as trkupgraded, trk_wificonnect as trkwificonnect, url_version as serverversion, site_name as sitename, c3.site_id as siteid " +
    "FROM public.trkosmapk a3 JOIN public.tablet b3 ON a3.trk_tabletserial=b3.tablet_serialno JOIN public.site c3 on b3.site_id=c3.site_id LEFT JOIN public.url d3 ON c3.site_id=d3.site_id and d3.url_type='OSMAND'" + " UNION " +
    "SELECT 'OSMANDMAP' as trktype, concat('D', trk_id::varchar(7)) as trkid, trk_id as orgtrkid, trk_tabletserial as trktabletserial, trk_updateavailable as trkupdateavailable, trk_currentversion as trkcurrentversion, " +
    "trk_downattemp as trkdownattemp, trk_timeattemp as trktimeattemp, trk_downloaded as trkdownloaded, trk_upgraded as trkupgraded, trk_wificonnect as trkwificonnect, url_version as serverversion, site_name as sitename, c4.site_id as siteid " +
    "FROM public.trkosmmap a4 JOIN public.tablet b4 ON a4.trk_tabletserial=b4.tablet_serialno JOIN public.site c4 on b4.site_id=c4.site_id LEFT JOIN public.url d4 ON c4.site_id=d4.site_id and d4.url_type='OSMANDMAP'"
)
public class TrackingView {

	private String trkid;
	private Integer orgtrkid;
	private String trktype;
	private String trktabletserial;
	private String trkcurrentversion;
	private Integer trkdownattemp;
	private Boolean trkupdateavailable;
	private Boolean trkdownloaded;
	private Boolean trkupgraded;
	private Date trktimeattemp;
	private Date trkwificonnect;
	private Tablet tablet;
	private String serverversion;
	//private Site site;
	private String sitename;
	private Integer siteid;

	public TrackingView() {
		super();
	}

	public TrackingView(String trktype, Integer orgtrkid, String trktabletserial, String trkcurrentversion, Integer trkdownattemp, Boolean trkupdateavailable,
			Boolean trkdownloaded, Date trktimeattemp, Date trkwificonnect) {
		//super();
		this.trktype = trktype;
		this.orgtrkid = orgtrkid;
		this.trktabletserial = trktabletserial;
		this.trkcurrentversion = trkcurrentversion;
		this.trkdownattemp = trkdownattemp;
		this.trkupdateavailable = trkupdateavailable;
		this.trkdownloaded = trkdownloaded;
		this.trktimeattemp = trktimeattemp;
		this.trkwificonnect = trkwificonnect;
	}

	@Id
	@Column(name = "trkid")
	@JsonIgnore
	public String getTrkid() {
		return trkid;
	}
	public void setTrkid(String trkid) {
		this.trkid = trkid;
	}
	
	@Column(name = "orgtrkid")
	public Integer getOrgtrkid() {
		return orgtrkid;
	}
	public void setOrgtrkid(Integer orgtrkid) {
		this.orgtrkid = orgtrkid;
	}
	
	@Column(name = "trktype")
	public String getTrktype() {
		return trktype;
	}
	public void setTrktype(String trktype) {
		this.trktype = trktype;
	}
	
	@Column(name = "trktabletserial")
	public String getTrktabletserial() {
		return trktabletserial;
	}
	public void setTrktabletserial(String trktabletserial) {
		this.trktabletserial = trktabletserial;
	}

	@Column(name = "trkcurrentversion")
	public String getTrkcurrentversion() {
		return trkcurrentversion;
	}

	public void setTrkcurrentversion(String trkcurrentversion) {
		this.trkcurrentversion = trkcurrentversion;
	}

	@Column(name = "trkdownattemp")
	public Integer getTrkdownattemp() {
		return trkdownattemp;
	}

	public void setTrkdownattemp(Integer trkdownattemp) {
		this.trkdownattemp = trkdownattemp;
	}

	@Column(name = "trktimeattemp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	public Date getTrktimeattemp() {
		return trktimeattemp;
	}

	public void setTrktimeattemp(Date trktimeattemp) {
		this.trktimeattemp = trktimeattemp;
	}

	@Column(name = "trkwificonnect")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	public Date getTrkwificonnect() {
		return trkwificonnect;
	}

	public void setTrkwificonnect(Date trkwificonnect) {
		this.trkwificonnect = trkwificonnect;
	}

	@Column(name = "trkupdateavailable")
	public Boolean getTrkupdateavailable() {
		return trkupdateavailable;
	}

	public void setTrkupdateavailable(Boolean trkupdateavailable) {
		this.trkupdateavailable = trkupdateavailable;
	}

	@Column(name = "trkdownloaded")
	public Boolean getTrkdownloaded() {
		return trkdownloaded;
	}

	public void setTrkdownloaded(Boolean trkdownloaded) {
		this.trkdownloaded = trkdownloaded;
	}

	@Column(name = "trkupgraded")
	public Boolean getTrkupgraded() {
		return trkupgraded;
	}

	public void setTrkupgraded(Boolean trkupgraded) {
		this.trkupgraded = trkupgraded;
	}
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "trktabletserial", referencedColumnName = "tablet_serialno", nullable = true, insertable = false, updatable = false)
	@JsonIgnore
	public Tablet getTablet() {
		return tablet;
	}

	public void setTablet(Tablet tablet) {
		this.tablet = tablet;
	}

	/*
	@Transient
	public String getserverversion() {
		if (this.tablet != null && this.tablet.getSite() != null && this.tablet.getSite().getUrls() != null
				&& this.tablet.getSite().getUrls().size() > 0) {
			for (Url url : this.tablet.getSite().getUrls()) {
				if (url.getUrltype() != null
						&& url.getUrltype().equalsIgnoreCase(this.trktype))
					return url.getUrlversion();
			}
		}
		return null;
	}
	*/
	//public void setSite (Site st) {
		//this.site = st;
	//}
	
	
	@Transient
	public Site getSite() {
		return this.tablet.getSite();
	}
//	public void setSite(Site st) {
//		this.site = st;
//	}
	
	@Column(name = "serverversion")
	public void setserverversion(String ver) {
		this.serverversion = ver;
	}
	public String getserverversion() {
		return this.serverversion;
	}
	
	@Column(name = "sitename")
	public void setsitename(String sitename) {
		this.sitename = sitename;
	}
	public String getsitename() {
		return this.sitename;
	}
	
	@Column(name = "siteid")
	public void setsiteid(Integer siteid) {
		this.siteid = siteid;
	}
	public Integer getsiteid() {
		return this.siteid;
	}
	
	
}
