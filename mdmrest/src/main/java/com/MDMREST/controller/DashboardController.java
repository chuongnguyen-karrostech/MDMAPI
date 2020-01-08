package com.MDMREST.controller;

import java.util.Collections;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.MDMREST.dao.mdm.SiteRepository;
import com.MDMREST.entity.mdm.DashboardData;
import com.MDMREST.entity.mdm.Site;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value="/dashboard")
@CrossOrigin(origins = "*")

public class DashboardController {
	@Autowired
	SiteRepository siteRepo;
	
	@RequestMapping(value = "/tablet", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addtablet(@RequestBody DashboardData data) {
		
		Site site = siteRepo.findOneBySiteid(data.getSiteid());
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/tablet";
		// create headers
	    HttpHeaders headers = new HttpHeaders();
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    // build the request
	    HttpEntity<?> entity = new HttpEntity<Object>(data.getData(), headers);

	    // use `exchange` method for HTTP call
	    UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(url);  
		RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<?> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Object.class);
	    
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return new ResponseEntity<>(new ActionReturn(CommonMessage.True, response.getBody()), HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value = "/tablet/count/{siteid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> counttablet(@PathVariable("siteid") Integer siteid) {
		
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/tablet/count";
		
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);  
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/tablet/detail/{siteid}/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> listtablet(@PathVariable("siteid") Integer siteid, @PathVariable("repdate") String repdate) {
		
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/tablet/detail/" + repdate;
		
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/punchlog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addpunchlog(@RequestBody DashboardData data) {
		
		Site site = siteRepo.findOneBySiteid(data.getSiteid());
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/punchlog";
		// create headers
	    HttpHeaders headers = new HttpHeaders();
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    // build the request
	    HttpEntity<?> entity = new HttpEntity<Object>(data.getData(), headers);

	    // use `exchange` method for HTTP call
	    UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(url);  
		RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<?> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Object.class);
	    
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return new ResponseEntity<>(new ActionReturn(CommonMessage.True, response.getBody()), HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value = "/punchlog/onoffline/{siteid}/{fromdate}/{todate}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> reportoffline(@PathVariable("siteid") Integer siteid, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
		
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/punchlog/onoffline/" + fromdate + "/" + todate ;
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);  
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/punchlog/different/{siteid}/{fromdate}/{todate}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> reportdifferent(@PathVariable("siteid") Integer siteid, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
		
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/punchlog/different/" + fromdate + "/" + todate ;
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);  
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/punchlog/onoffline/detail/{siteid}/{onoff}/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> listonoffline(@PathVariable("siteid") Integer siteid, @PathVariable("onoff") Integer onoff, @PathVariable("repdate") String repdate, Pageable pageRequest) {
	
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/punchlog/onoffline/detail/" + onoff.toString() + "/" + repdate + "?page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		
		if (pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for (String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			url = url + sortRequest;
		}		
		
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);  
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/punchlog/different/detail/{siteid}/{level}/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> listdifferent(@PathVariable("siteid") Integer siteid, @PathVariable("level") Integer level, @PathVariable("repdate") String repdate, Pageable pageRequest) {
		
		Site site = siteRepo.findOneBySiteid(siteid);
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/punchlog/different/detail/" + level.toString() + "/" + repdate + "?page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();;
		
		if (pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for (String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			url = url + sortRequest;
		}		
		
		try {
			UriComponentsBuilder getbuilder = UriComponentsBuilder
			    .fromUriString(url);
			RestTemplate getrestTemplate = new RestTemplate();
			Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
			
		} catch (ConstraintViolationException v) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/invalidlocation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addinvalidlocation(@RequestBody DashboardData data) {
		
		Site site = siteRepo.findOneBySiteid(data.getSiteid());
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/invalidlocation";
		// create headers
	    HttpHeaders headers = new HttpHeaders();
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    // build the request
	    HttpEntity<?> entity = new HttpEntity<Object>(data.getData(), headers);

	    // use `exchange` method for HTTP call
	    UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(url);  
		RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<?> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Object.class);
	    
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return new ResponseEntity<>(new ActionReturn(CommonMessage.True, response.getBody()), HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value = "/wificellular", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addwificellularuse(@RequestBody DashboardData data) {
		
		Site site = siteRepo.findOneBySiteid(data.getSiteid());
		if (site == null) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "Site id does not exist"), HttpStatus.OK);
		if (site.getDashboardurl().isEmpty()) return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "DashboardUrl field is empty!"), HttpStatus.OK);
		
		String url = site.getDashboardurl() + "/dashboard/wificellular";
		// create headers
	    HttpHeaders headers = new HttpHeaders();
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    // build the request
	    HttpEntity<?> entity = new HttpEntity<Object>(data.getData(), headers);

	    // use `exchange` method for HTTP call
	    UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(url);  
		RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<?> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Object.class);
	    
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return new ResponseEntity<>(new ActionReturn(CommonMessage.True, response.getBody()), HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
	    }
	}
	
}