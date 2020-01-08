package com.MDMREST.service.mdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.MDMREST.dao.mdm.DPAPKRepository;
import com.MDMREST.dao.mdm.DPMAPAPKRepository;
import com.MDMREST.dao.mdm.OSMAPKRepository;
import com.MDMREST.dao.mdm.OSMMAPAPKRepository;
import com.MDMREST.dao.mdm.TabletRepository;
import com.MDMREST.dao.mdm.TrackingViewRepository;
import com.MDMREST.dao.mdm.UrlRepository;
import com.MDMREST.entity.mdm.DPAPK;
import com.MDMREST.entity.mdm.DPMAP;
import com.MDMREST.entity.mdm.OSMAPK;
import com.MDMREST.entity.mdm.OSMMAP;
import com.MDMREST.entity.mdm.Site;
import com.MDMREST.entity.mdm.Tablet;
import com.MDMREST.entity.mdm.Tracking;
import com.MDMREST.entity.mdm.TrackingView;
import com.MDMREST.entity.mdm.Url;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class TrackingService {

	private static final Logger log = LoggerFactory.getLogger(TrackingService.class);

	public final static String DRIVERPORTAL = "driverportal";
	public final static String MAPS = "maps";
	public final static String OSMAND = "osmand";
	public final static String OSMANDMAP = "osmandmap";

	@Autowired
	UrlRepository urlRepo;
	@Autowired
	TabletRepository tabletRepo;
	@Autowired
	DPAPKRepository dpapkRepo;
	@Autowired
	DPMAPAPKRepository dpmapRepo;
	@Autowired
	OSMAPKRepository osmapkRepo;
	@Autowired
	OSMMAPAPKRepository osmmapRepo;
	@Autowired
	TrackingViewRepository trkviewRepo;
	
	@Autowired
	private Environment env;

	public Page<?> Get(String type, Pageable pageRequest, Optional<String> tabletserialno) {
		switch (type.toLowerCase()) {
		case DRIVERPORTAL:
			if (tabletserialno.isPresent())
				return dpapkRepo.findAllByTabletSerial(tabletserialno.get(), pageRequest);
			else
				return dpapkRepo.findAll(pageRequest);
		case MAPS:

			if (tabletserialno.isPresent())
				return dpmapRepo.findAllByTabletSerial(tabletserialno.get(), pageRequest);
			else
				return dpmapRepo.findAll(pageRequest);
		case OSMAND:

			if (tabletserialno.isPresent())
				return osmapkRepo.findAllByTabletSerial(tabletserialno.get(), pageRequest);
			else
				return osmapkRepo.findAll(pageRequest);
		case OSMANDMAP:

			if (tabletserialno.isPresent())
				return osmmapRepo.findAllByTabletSerial(tabletserialno.get(), pageRequest);
			else
				return osmmapRepo.findAll(pageRequest);
		default:
			break;
		}
		return null;
	}

	public Long DeleteTracking(String type, String tabletserialno) {
		try {

			switch (type.toLowerCase()) {

			case DRIVERPORTAL:
				DPAPK a0 = dpapkRepo.GetTrackingBySerailNo(tabletserialno);
				if (a0 != null) {
					dpapkRepo.delete(a0);
					return a0.getTrkid();
				}
				break;
			case MAPS:
				DPMAP a1 = dpmapRepo.GetTrackingBySerailNo(tabletserialno);
				if (a1 != null) {
					dpmapRepo.delete(a1);
					return a1.getTrkid();
				}
				break;
			case OSMAND:
				OSMAPK a2 = osmapkRepo.GetTrackingBySerailNo(tabletserialno);
				if (a2 != null) {
					osmapkRepo.delete(a2);
					return a2.getTrkid();
				}
				break;
			case OSMANDMAP:
				OSMMAP a3 = osmmapRepo.GetTrackingBySerailNo(tabletserialno);
				if (a3 != null) {
					osmmapRepo.delete(a3);
					return a3.getTrkid();
				}
				break;
			default:
				return 0l;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Long.MAX_VALUE;
		}
		return 0l;
	}

	public Tracking GetTableTracking(String type, String tabletserialno) {

		Tracking tracking = null;

		try {

			switch (type.toLowerCase()) {

			case DRIVERPORTAL:
				DPAPK a0 = dpapkRepo.GetTrackingBySerailNo(tabletserialno);
				if (a0 != null)
					tracking = a0.getTrkinfo();
				break;
			case MAPS:
				DPMAP a1 = dpmapRepo.GetTrackingBySerailNo(tabletserialno);
				if (a1 != null)
					tracking = a1.getTrkinfo();
				break;
			case OSMAND:
				OSMAPK a2 = osmapkRepo.GetTrackingBySerailNo(tabletserialno);
				if (a2 != null)
					tracking = a2.getTrkinfo();
				break;
			case OSMANDMAP:
				OSMMAP a3 = osmmapRepo.GetTrackingBySerailNo(tabletserialno);
				if (a3 != null)
					tracking = a3.getTrkinfo();
				break;
			default:
				tracking = null;
				break;
			}

		} catch (Exception e) {
			tracking = null;
			log.error(e.getMessage(), e);
		}
		return tracking;
	}

	public ActionReturn Add(String userAgent, String type, Tracking tracking) {
		String flag = CommonMessage.False;
		try {

			if (tracking.getTrktabletserial() == null || tracking.getTrktabletserial().isEmpty()) {
				return new ActionReturn(CommonMessage.False, "Missing tablet serial number");
			}

			Tablet tablet = tabletRepo.findBySerialNo(tracking.getTrktabletserial());
			if (tablet == null) {
				return new ActionReturn(CommonMessage.False, "Tablet serial does not exist");
			}
			
			Site site = tablet.getSite();
			
			if (!site.getforwardurl().isEmpty() && ContainsPattern(userAgent, env.getProperty("forwarding.useragentallowed", "NOFORWARDING"))) {
			//if (userAgent.toLowerCase().contains("android") && !site.getforwardurl().isEmpty()) {
				String transactionUrl = site.getforwardurl();
				
				transactionUrl = transactionUrl + "tracking/" + type;
				
				UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(transactionUrl);  
				RestTemplate restTemplate = new RestTemplate();
				
				List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
				list.add(new MappingJackson2HttpMessageConverter());
				restTemplate.setMessageConverters(list);	
				return restTemplate.postForObject(builder.toUriString(), tracking, ActionReturn.class);		
			}
			else {

				Url url = urlRepo.GetLastVersionUrlOfSite("dpapi", tablet.getSiteid());
				if (url != null)
					tracking.dpapi = url.getUrlpath();
	
				switch (type.toLowerCase()) {
				case DRIVERPORTAL:
					flag = UpdateDRIVERPORTAL(tracking);
					break;
				case MAPS:
					flag = UpdateMAPS(tracking);
					break;
				case OSMAND:
					flag = UpdateOSMAND(tracking);
					break;
				case OSMANDMAP:
					flag = UpdateOSMANDMAP(tracking);
					break;
				default:
					return new ActionReturn(CommonMessage.False, "Unknow type: " + type);
				}
			}

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public String UpdateDRIVERPORTAL(Tracking tracking) {
		DPAPK a = dpapkRepo.GetTrackingBySerailNo(tracking.getTrktabletserial());
		if (a == null) {
			a = new DPAPK();
		}
		a.getTrkinfo().UpdateTrackingInfo(tracking);

		DPAPK added = dpapkRepo.saveAndFlush(a);
		return String.valueOf(added.getTrkid());
	}

	public String UpdateMAPS(Tracking tracking) {
		DPMAP a = dpmapRepo.GetTrackingBySerailNo(tracking.getTrktabletserial());
		if (a == null) {
			a = new DPMAP();
		}
		a.getTrkinfo().UpdateTrackingInfo(tracking);

		DPMAP added = dpmapRepo.saveAndFlush(a);
		return String.valueOf(added.getTrkid());
	}

	public String UpdateOSMAND(Tracking tracking) {
		OSMAPK a = osmapkRepo.GetTrackingBySerailNo(tracking.getTrktabletserial());
		if (a == null) {
			a = new OSMAPK();
		}
		a.getTrkinfo().UpdateTrackingInfo(tracking);

		OSMAPK added = osmapkRepo.saveAndFlush(a);
		return String.valueOf(added.getTrkid());
	}

	public String UpdateOSMANDMAP(Tracking tracking) {
		OSMMAP a = osmmapRepo.GetTrackingBySerailNo(tracking.getTrktabletserial());
		if (a == null) {
			a = new OSMMAP();
		}
		a.getTrkinfo().UpdateTrackingInfo(tracking);

		OSMMAP added = osmmapRepo.saveAndFlush(a);
		return String.valueOf(added.getTrkid());
	}

	public String CheckDataIntergation(DataIntegrityViolationException d) {

		PSQLException a = (PSQLException) d.getCause().getCause();
		String error = a.getMessage();
		if (error.toLowerCase().contains("trk_tabletserial")
				&& error.toLowerCase().contains("is not present in table \"tablet\"")) {
			error = "Tablet serial number id does not exist";
		}
		return error;
	}
	
	public Page<TrackingView> GetAll(Pageable pageRequest) {
		return trkviewRepo.findAll(pageRequest);
	}
	
	public Page<TrackingView> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return trkviewRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<TrackingView> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<TrackingView>() {
				@Override
				public Predicate toPredicate(Root<TrackingView> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<TrackingView>() {
			@Override
			public Predicate toPredicate(Root<TrackingView> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject search : lstsearch) {
					Predicate condition = search.BuildCondition(root, cb);
					if (condition != null)
						predicates.add(condition);
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
	
	private boolean ContainsPattern(String src, String PatternList) {
		String[] patterns = PatternList.split(",");
		for (String pat : patterns) {
			if (src.toLowerCase().contains(pat.trim().toLowerCase())) return true;
		}
		return false;
	}
}