package com.MDMREST.entity.mdm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.MDMREST.util.DateFunction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class Tracking {

	private String trktabletserial;
	private String trkcurrentversion;
	private Integer trkdownattemp;
	private Boolean trkupdateavailable;
	private Boolean trkdownloaded;
	private Boolean trkupgraded;
	private Date trktimeattemp;
	private Date trkwificonnect;

	@Transient
	@JsonIgnore
	public String dpapi;

	@JsonIgnore
	public Boolean isReset = false;

	public Tracking() {
		super();
	}

	public Tracking(String trktabletserial, String trkcurrentversion, Integer trkdownattemp, Boolean trkupdateavailable,
			Boolean trkdownloaded, Date trktimeattemp, Date trkwificonnect) {
		super();
		this.trktabletserial = trktabletserial;
		this.trkcurrentversion = trkcurrentversion;
		this.trkdownattemp = trkdownattemp;
		this.trkupdateavailable = trkupdateavailable;
		this.trkdownloaded = trkdownloaded;
		this.trktimeattemp = trktimeattemp;
		this.trkwificonnect = trkwificonnect;
	}

	@Column(name = "trk_tabletserial")
	@NotBlank(message = "Missing tablet serial number")
	@Size(max = 50, message = "Max length of serial number is {max} characters")
	public String getTrktabletserial() {
		return trktabletserial;
	}

	public void setTrktabletserial(String trktabletserial) {
		this.trktabletserial = trktabletserial;
	}

	@Column(name = "trk_currentversion")
	@Size(max = 20, message = "Max length of version is {max} characters")
	public String getTrkcurrentversion() {
		return trkcurrentversion;
	}

	public void setTrkcurrentversion(String trkcurrentversion) {
		this.trkcurrentversion = trkcurrentversion;
	}

	@Column(name = "trk_downattemp")
	public Integer getTrkdownattemp() {
		return trkdownattemp;
	}

	public void setTrkdownattemp(Integer trkdownattemp) {
		this.trkdownattemp = trkdownattemp;
	}

	@Column(name = "trk_timeattemp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	public Date getTrktimeattemp() {
		return trktimeattemp;
	}

	public void setTrktimeattemp(Date trktimeattemp) {
		this.trktimeattemp = trktimeattemp;
	}

	@Column(name = "trk_wificonnect")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	public Date getTrkwificonnect() {
		return trkwificonnect;
	}

	public void setTrkwificonnect(Date trkwificonnect) {
		this.trkwificonnect = trkwificonnect;
	}

	@Column(name = "trk_updateavailable")
	public Boolean getTrkupdateavailable() {
		return trkupdateavailable;
	}

	public void setTrkupdateavailable(Boolean trkupdateavailable) {
		this.trkupdateavailable = trkupdateavailable;
	}

	@Column(name = "trk_downloaded")
	public Boolean getTrkdownloaded() {
		return trkdownloaded;
	}

	public void setTrkdownloaded(Boolean trkdownloaded) {
		this.trkdownloaded = trkdownloaded;
	}

	@Column(name = "trk_upgraded")
	public Boolean getTrkupgraded() {
		return trkupgraded;
	}

	public void setTrkupgraded(Boolean trkupgraded) {
		this.trkupgraded = trkupgraded;
	}

	public void UpdateTrackingInfo(Tracking tracking) {

		this.dpapi = tracking.dpapi;

		if (tracking.getTrktabletserial() != null || tracking.isReset)
			this.trktabletserial = tracking.getTrktabletserial();

		if (tracking.getTrkupgraded() != null || tracking.isReset)
			this.trkupgraded = tracking.getTrkupgraded();

		if (tracking.getTrkdownloaded() != null || tracking.isReset)
			this.trkdownloaded = tracking.getTrkdownloaded();

		if (tracking.getTrkupdateavailable() != null || tracking.isReset)
			this.trkupdateavailable = tracking.getTrkupdateavailable();

		if (tracking.getTrkcurrentversion() != null || tracking.isReset)
			this.trkcurrentversion = tracking.getTrkcurrentversion();

		if (tracking.getTrkdownattemp() != null || tracking.isReset)
			this.trkdownattemp = tracking.getTrkdownattemp();

		if (tracking.isReset) {
			this.trktimeattemp = null;
			this.trkwificonnect = null;
		} else {
			if (tracking.getTrktimeattemp() != null)
				this.trktimeattemp = DateFunction.getVehicleTime(dpapi);

			if (tracking.getTrkwificonnect() != null)
				this.trkwificonnect = DateFunction.getVehicleTime(dpapi);
		}
	}
}
