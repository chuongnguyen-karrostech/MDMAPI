package com.MDMREST.entity.mdm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Immutable
@Subselect(
    "SELECT 'DRIVERPORTAL' AS trktype, CONCAT('A', a1.trk_id::VARCHAR(7)) AS trkid, b1.site_id AS siteid, CASE WHEN trk_downloaded = true THEN 1 ELSE 0 END AS total_downloaded, CASE WHEN trk_upgraded = true THEN 1 ELSE 0 END AS total_upgraded FROM public.trkdpapk a1 JOIN public.tablet b1 ON a1.trk_tabletserial = b1.tablet_serialno" + " UNION ALL " + 
    "SELECT 'MAPS' AS trktype, CONCAT('B', a2.trk_id::VARCHAR(7)) AS trkid, b2.site_id AS siteid, CASE WHEN trk_downloaded = true THEN 1 ELSE 0 END AS total_downloaded, CASE WHEN trk_upgraded = true THEN 1 ELSE 0 END AS total_upgraded FROM public.trkdpmap a2 JOIN public.tablet b2 ON a2.trk_tabletserial = b2.tablet_serialno" + " UNION ALL " +
    "SELECT 'OSMAND' AS trktype, CONCAT('C', a3.trk_id::VARCHAR(7)) AS trkid, b3.site_id as siteid, CASE WHEN trk_downloaded = true THEN 1 ELSE 0 END AS total_downloaded, CASE WHEN trk_upgraded = true THEN 1 ELSE 0 END AS total_upgraded FROM public.trkosmapk a3 JOIN public.tablet b3 ON a3.trk_tabletserial = b3.tablet_serialno" + " UNION ALL " +
    "SELECT 'OSMANDMAP' AS trktype, CONCAT('D', a4.trk_id::VARCHAR(7)) AS trkid, b4.site_id as siteid, CASE WHEN trk_downloaded = true THEN 1 ELSE 0 END AS total_downloaded, CASE WHEN trk_upgraded = true THEN 1 ELSE 0 END AS total_upgraded FROM public.trkosmmap a4 JOIN public.tablet b4 ON a4.trk_tabletserial = b4.tablet_serialno"
)

public class SiteStatictics{
	private String trkid;
	private Integer siteid;
	private String trktype;
	private Integer total_downloaded;
	private Integer total_upgraded;
	
	public SiteStatictics() {
		super();
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
	
	@Column(name = "trktype")
	public String getTrktype() {
		return trktype;
	}
	public void setTrktype(String trktype) {
		this.trktype = trktype;
	}
	
	@Column(name = "siteid")
	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}
	
	@Column(name = "total_downloaded")
	public Integer gettotal_downloaded() {
		return this.total_downloaded;
	}

	public void settotal_downloaded(Integer num) {
		this.total_downloaded = num;
	}
	
	@Column(name = "total_upgraded")
	public Integer gettotal_upgraded() {
		return this.total_upgraded;
	}

	public void settotal_upgraded(Integer num) {
		this.total_upgraded = num;
	}
}