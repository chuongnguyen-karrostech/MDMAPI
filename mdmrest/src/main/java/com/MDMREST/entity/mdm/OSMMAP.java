package com.MDMREST.entity.mdm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.MDMREST.service.mdm.TrackingService;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trkosmmap", schema = "public")
public class OSMMAP {
	private Long trkid;
	private Tracking trkinfo;
	private Tablet tablet;

	public OSMMAP() {
		super();
		this.trkinfo = new Tracking();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trk_id")
	public Long getTrkid() {
		return trkid;
	}

	public void setTrkid(Long trkid) {
		this.trkid = trkid;
	}

	public Tracking getTrkinfo() {
		return trkinfo;
	}

	public void setTrkinfo(Tracking trkinfo) {
		this.trkinfo = trkinfo;
	}
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "trk_tabletserial", referencedColumnName = "tablet_serialno", nullable = true, insertable = false, updatable = false)
	@JsonIgnore
	public Tablet getTablet() {
		return tablet;
	}

	public void setTablet(Tablet tablet) {
		this.tablet = tablet;
	}
	
	@Transient
	public String getServerversion()
	{
		if(this.tablet != null && this.tablet.getSite() != null && this.tablet.getSite().getUrls() != null && this.tablet.getSite().getUrls().size() > 0)
		{			
			for (Url url : this.tablet.getSite().getUrls()) {
				if(url.getUrltype() != null && url.getUrltype().equalsIgnoreCase(TrackingService.OSMANDMAP.toLowerCase()))
					return url.getUrlversion();	
			}	
		}
		return null;
	}
}
