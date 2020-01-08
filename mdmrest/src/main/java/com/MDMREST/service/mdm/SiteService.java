package com.MDMREST.service.mdm;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.dao.mdm.SiteRepository;
import com.MDMREST.dao.mdm.SiteStaticticsRepository;
import com.MDMREST.dao.mdm.TabletRepository;
import com.MDMREST.dao.mdm.UrlRepository;
import com.MDMREST.dao.mdm.WifiRepository;
import com.MDMREST.entity.mdm.CommonSite;
import com.MDMREST.entity.mdm.Site;
import com.MDMREST.entity.mdm.Summary;
import com.MDMREST.entity.mdm.Tablet;
import com.MDMREST.entity.mdm.Tracking;
import com.MDMREST.entity.mdm.Url;
import com.MDMREST.entity.mdm.UrlData;
import com.MDMREST.entity.mdm.Wifi;
import com.MDMREST.entity.mdm.WifiData;
import com.MDMREST.util.RestAction;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class SiteService {

	private static final Logger log = LoggerFactory.getLogger(SiteService.class);

	@Autowired
	SiteRepository siteRepo;

	@Autowired
	TabletRepository tabletRepo;

	@Autowired
	UrlRepository urlRepo;

	@Autowired
	TrackingService trackService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	SiteStaticticsRepository sitestaticticsRepo;
	
	@Autowired
	WifiRepository wifiRepo;

	public Page<Site> Get(Pageable pageRequest) {
		return siteRepo.findAll(pageRequest);
	}

	public ActionReturn GetUpdate(String userAgent, String type, String version, String tabletserialno) {
		try {
			Tablet tablet = tabletRepo.findBySerialNo(tabletserialno);

			if (tablet == null) {
				return new ActionReturn(CommonMessage.False, "Tablet id does not exist");
			}
			
			Site site = tablet.getSite();
			
			if (!site.getforwardurl().isEmpty() && ContainsPattern(userAgent, env.getProperty("forwarding.useragentallowed", "NOFORWARDING"))) {
			//if (userAgent.toLowerCase().contains("android") && !site.getforwardurl().isEmpty()) {
				String transactionUrl = site.getforwardurl();
				
				transactionUrl = transactionUrl + "update/" + tabletserialno + "/" + type + "/" + version;
				
				UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(transactionUrl);  
				RestTemplate restTemplate = new RestTemplate();
				return restTemplate.getForObject(builder.toUriString(), ActionReturn.class);
			}
			else {

				Url url = urlRepo.GetLastVersionUrlOfSite(type, tablet.getSiteid());
				Tracking tracking = trackService.GetTableTracking(type, tabletserialno);
	
				if (url != null && url.getUrlversion().compareTo(version) > 0) {
					if ((url != null && tracking == null)
							|| (url != null && tracking.getTrkcurrentversion() == null)
							|| (url != null && tracking.getTrkcurrentversion() != null && url.getUrlversion().compareTo(tracking.getTrkcurrentversion()) > 0)
							|| (url != null && tracking.getTrkcurrentversion() != null && url.getUrlversion().compareTo(tracking.getTrkcurrentversion()) == 0
									&& (tracking.getTrkdownloaded() == null || tracking.getTrkdownloaded() == false))) {
	
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("url", url.ZipInfo());
											
						// reset tablet tracking when update available 
						if(tracking != null && (tracking.getTrkcurrentversion() == null || url.getUrlversion().compareTo(tracking.getTrkcurrentversion()) > 0))
						{
							tracking.setTrkupdateavailable(true);						
							tracking.setTrkdownloaded(null);
							tracking.setTrkupgraded(null);
							tracking.setTrkdownattemp(null);
							tracking.setTrkwificonnect(null);
							tracking.setTrktimeattemp(null);
							tracking.setTrkcurrentversion(null);
							tracking.isReset = true;
							ActionReturn ar = trackService.Add(userAgent, type, tracking);
							if(ar.resultIsId())
								log.info(String.format("Reset tracking of tablet serial %s successfully.", tablet.getTabletserialno()));
							else
								log.error(String.format("Reset tracking of tablet serial %s fail.", tablet.getTabletserialno()));							
						}
	
						return new ActionReturn(CommonMessage.True, url.getUrlversion());
	
					} else {
						return new ActionReturn(CommonMessage.False, url.getUrlversion());
					}
				} else {
					if(url != null)
						return new ActionReturn(CommonMessage.False, url.getUrlversion());
					else 
						return new ActionReturn(CommonMessage.False);
				}
			}
		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}
	}

	public ActionReturn GetConfig(String userAgent, String tabletserialno, Boolean multiwifi) {

		try {
			
			Tablet tablet = tabletRepo.findBySerialNo(tabletserialno);

			if (tablet == null) {
				return new ActionReturn(CommonMessage.False, "Tablet id does not exist");
			}

			Site site = tablet.getSite();
			
			if (!site.getforwardurl().isEmpty() && ContainsPattern(userAgent, env.getProperty("forwarding.useragentallowed", "NOFORWARDING"))) {
			//if (userAgent.toLowerCase().contains("android") && !site.getforwardurl().isEmpty()) {
				String transactionUrl = site.getforwardurl();
				if (multiwifi)
					transactionUrl = transactionUrl + "config/wifis/" + tabletserialno;
				else
					transactionUrl = transactionUrl + "config/" + tabletserialno;
				
				UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(transactionUrl);  
				RestTemplate restTemplate = new RestTemplate();
				return restTemplate.getForObject(builder.toUriString(), ActionReturn.class);
			}
			else {
			
				List<Wifi> wifis = site.getWifis();
				List<Object> wifis_zipinfo = new ArrayList<>();
				for (Wifi wifi : wifis) {
					wifis_zipinfo.add(wifi.ZipInfo());
				}
				
				List<Url> urls = site.getUrls();
				List<Object> urls_zipinfo = new ArrayList<>();
				for (Url url : urls) {
					urls_zipinfo.add(url.ZipInfo());
				}			
	
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("site", site != null ? site.ZipInfo() : null);
				if(multiwifi == false)
					map.put("wifi", wifis.size() > 0 ? wifis_zipinfo.get(0) : new ArrayList<Wifi>());
				else
					map.put("wifi", wifis.size() > 0 ? wifis_zipinfo : new ArrayList<Wifi>());
				map.put("urls", urls.size() > 0 ? urls_zipinfo : new ArrayList<Url>());
				return new ActionReturn(CommonMessage.True, map);
			}

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}
	}

	public ActionReturn Update(Site site) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(site, RestAction.Update);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			//Site s = siteRepo.findOne(site.getSiteid());

			//if (site.getWifi() != null && s.getWifi() != null)
			//	site.getWifi().setWifiid(s.getWifi().getWifiid());

			Site updated = siteRepo.saveAndFlush(site);
			flag = String.valueOf(updated.getSiteid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Add(Site site) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(site, RestAction.Add);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Site added = siteRepo.saveAndFlush(site);

			//if (site.getWifi() != null)
			//	added.getWifi().setSiteid(added.getSiteid());

			added = siteRepo.saveAndFlush(added);

			flag = String.valueOf(added.getSiteid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Delete(Integer siteid) {
		String flag = CommonMessage.False;

		try {

			String message = CheckValidate(new Site(siteid), RestAction.Delete);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			siteRepo.delete(siteid);
			flag = String.valueOf(siteid);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public String CheckValidate(Site site, RestAction action) {

		String message = "";

		switch (action) {
		case Add:
			if (site.getSitetenanid() != null && siteRepo.findOneByTenan(site.getSitetenanid()).size() > 0) {
				message = "Tenant id already exists";
			} else if (site.getSitename() != null && siteRepo.findOneByName(site.getSitename()).size() > 0) {
				message = "Site name already exists";
			}
			break;
		case Update:
			if (siteRepo.findOne(site.getSiteid()) == null) {
				message = "Site does not exist to update";
			} else if (site.getSitetenanid() != null
					&& siteRepo.findOneByTenan(site.getSitetenanid(), site.getSiteid()).size() > 0) {
				message = "Tenant id already exists";
			} else if (site.getSitename() != null
					&& siteRepo.findOneByName(site.getSitename(), site.getSiteid()).size() > 0) {
				message = "Site name already exists";
			}
			break;
		case Delete:
			if (siteRepo.findOne(site.getSiteid()) == null) {
				message = "Site does not exist to delete";
			}
			break;
		}

		return message.trim();
	}
	
	public Page<Site> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return siteRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<Site> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<Site>() {
				@Override
				public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
	
	public ActionReturn Get(Integer siteid, Optional<String> trktype) {
		
		try {

			if (siteRepo.findOne(siteid) == null) {
				return new ActionReturn(CommonMessage.False, "Site does not exist");
			}
			
			Summary sum = null; 
			
			if (trktype.isPresent()) {
				
				String TrackingType = trktype.get().toUpperCase();
				if (TrackingType.equalsIgnoreCase("DRIVERPORTAL") || TrackingType.equalsIgnoreCase("MAPS") || TrackingType.equalsIgnoreCase("OSMAND") || TrackingType.equalsIgnoreCase("OSMANDMAP")) {
					sum = sitestaticticsRepo.makeSumOnTrkType(siteid, TrackingType);
				}
				else {
					return new ActionReturn(CommonMessage.False, "Tracking type is not valid");
				}
			}
			else {
				sum = sitestaticticsRepo.makeSumOnAllType(siteid);
			}
			
			HashMap<String, Long> map = new HashMap<String, Long>();
			
			map.put("total_downloaded", sum.gettotal_downloaded() != null ? sum.gettotal_downloaded() : 0);
			map.put("total_upgraded", sum.gettotal_upgraded() != null ? sum.gettotal_upgraded() : 0);
			

			return new ActionReturn(CommonMessage.True, map);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}
		
		
	}
	
	public boolean isValidURL(String url) {  

	    URL u = null;

	    try {  
	        u = new URL(url);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }

	    try {  
	        u.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  

	    return true;  
	} 
	
	public ActionReturn CheckSite(CommonSite site) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// Check main site info.
		String siteName = site.getSite().getData().getSitename();
		String siteTenanid = site.getSite().getData().getSitetenanid();
		
		if (siteTenanid != null && siteRepo.findOneByTenan(siteTenanid).size() > 0) {
			map.put("id", site.getSite().getData().getTempIndex());
			map.put("type", "site");
			map.put("message", "Tenant id already exists");
			return new ActionReturn(CommonMessage.False, map);
		} else if (siteName != null && siteRepo.findOneByName(siteName).size() > 0) {
			map.put("id", site.getSite().getData().getTempIndex());
			map.put("type", "site");
			map.put("message", "Site name already exists");
			return new ActionReturn(CommonMessage.False, map);
		}
		
		// Check url
		List<UrlData> urlList = site.getUrl().getData();
		final Set<String> urlTypeList = new HashSet<String>();
		for (UrlData urlData : urlList) {
			
			if (!isValidURL(urlData.getUrlpath())) {
				map.put("id", urlData.getTempIndex());
				map.put("type", "url");
				map.put("message", "Invalid url path");
				return new ActionReturn(CommonMessage.False, map);
			}
			
			if (!urlTypeList.add(urlData.getUrltype().toUpperCase().trim())) {
				map.put("id", urlData.getTempIndex());
				map.put("type", "url");
				map.put("message", "Duplicate url type");
				return new ActionReturn(CommonMessage.False, map);
			}
		}
		 
		// Check wifi
		List<WifiData> wifiList = site.getWifi().getData();
		final Set<String> wifiSsidList = new HashSet<String>();
		for (WifiData wifiData : wifiList) {
			if (!wifiSsidList.add(wifiData.getWifissid().toUpperCase().trim())) {
				map.put("id", wifiData.getTempIndex());
				map.put("type", "wifi");
				map.put("message", "Duplicate wifi SSID");
				return new ActionReturn(CommonMessage.False, map);
			}
			if (wifiData.getWifisecuritytype().toUpperCase().equals("OPEN")) {
				if (wifiData.getWifipassword().trim().length()>0) {
					map.put("id", wifiData.getTempIndex());
					map.put("type", "wifi");
					map.put("message", "Please clear password when security type is Open.");
					return new ActionReturn(CommonMessage.False, map);
				}
			}
			else {
				if (wifiData.getWifipassword().trim().length()==0) {
					map.put("id", wifiData.getTempIndex());
					map.put("type", "wifi");
					map.put("message", "Missing wifi password.");
					return new ActionReturn(CommonMessage.False, map);
				}
			}
			if (wifiData.getWifilongitude()<-180 || wifiData.getWifilongitude()>180) {
				map.put("id", wifiData.getTempIndex());
				map.put("type", "wifi");
				map.put("message", "Longitude must be between -180 and 180");
				return new ActionReturn(CommonMessage.False, map);
			}
			if (wifiData.getWifilatitude()<-90 || wifiData.getWifilatitude()>90) {
				map.put("id", wifiData.getTempIndex());
				map.put("type", "wifi");
				map.put("message", "Latitude must be between -90 and 90");
				return new ActionReturn(CommonMessage.False, map);
			}
		}
		
		return new ActionReturn(CommonMessage.True);
	}
	
	@Transactional
	public ActionReturn Clone(CommonSite site) {

		try {

			ActionReturn ar = CheckSite(site);
			if (ar.result.equals("False")){
				return ar;
			}
				
			Site st = new Site();
			st.setSitename(site.getSite().getData().getSitename());
			st.setSitetenanid(site.getSite().getData().getSitetenanid());
			
			Site added = siteRepo.saveAndFlush(st);
			
			Integer siteId = added.getSiteid();
			
			List<WifiData> wifiList = site.getWifi().getData();
			
			for (WifiData wifiData : wifiList){
				Wifi wifi = new Wifi();
				wifi.setSiteid(siteId);
				wifi.setWifilatitude(wifiData.getWifilatitude());
				wifi.setWifilongitude(wifiData.getWifilongitude());
				wifi.setWifipassword(wifiData.getWifipassword());
				wifi.setWifiradius(wifiData.getWifiradius());
				wifi.setWifisecuritytype(wifiData.getWifisecuritytype());;
				wifi.setWifissid(wifiData.getWifissid());
				wifiRepo.saveAndFlush(wifi);
			}
			
			List<UrlData> urlList = site.getUrl().getData();
			
			for (UrlData urlData : urlList) {
				Url url = new Url();
				url.setSiteid(siteId);
				url.setUrlfilesize(urlData.getUrlfilesize());
				url.setUrlpath(urlData.getUrlpath());
				url.setUrltype(urlData.getUrltype());
				url.setUrlversion(urlData.getUrlversion());
				urlRepo.saveAndFlush(url);
			}

			return new ActionReturn(String.valueOf(siteId));

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}
	}

}