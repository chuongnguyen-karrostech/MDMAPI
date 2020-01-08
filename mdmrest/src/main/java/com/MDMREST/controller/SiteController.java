package com.MDMREST.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.entity.mdm.CommonSite;
import com.MDMREST.entity.mdm.Site;
import com.MDMREST.service.mdm.SiteService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@CrossOrigin(origins = "*")
public class SiteController {
	
		
	@Value("${build.version}")
    private String buildVersion;
	
	@Value("${build.description}")
    private String buildDescription;
	
	@Autowired
	SiteService siteService;

	@RequestMapping(value = "/update/{tabletserialno}/{type}/{version}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetConfig(HttpServletRequest request, @PathVariable("tabletserialno") String tabletserialno,
			@PathVariable("type") String type, @PathVariable("version") String version) {
		
		String userAgent = request.getHeader("user-agent");
		ActionReturn ar = siteService.GetUpdate(userAgent, type, version, tabletserialno);
		return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/config/{tabletserialno}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetConfig(HttpServletRequest request, @PathVariable("tabletserialno") String tabletserialno) {
		String userAgent = request.getHeader("user-agent");
		
		ActionReturn ar = siteService.GetConfig(userAgent, tabletserialno, false);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	@RequestMapping(value = "/config/wifis/{tabletserialno}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetConfigWithMultipleWifi(HttpServletRequest request, @PathVariable("tabletserialno") String tabletserialno) {
		String userAgent = request.getHeader("user-agent");
		
		ActionReturn ar = siteService.GetConfig(userAgent, tabletserialno, true);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetVersion() {		
		return new ResponseEntity<>(buildDescription, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/site", method = RequestMethod.GET)
	@ResponseBody
	public Page<Site> Get(Pageable pageRequest) {
		Page<Site> searchResultPage = siteService.Get(pageRequest);
		return searchResultPage;
	}

	@RequestMapping(value = "/site", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(@RequestBody Site site) {

		ActionReturn ar = siteService.Add(site);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody Site site, @PathVariable("id") Integer id) {

		site.setSiteid(id);
		ActionReturn ar = siteService.Update(site);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("id") Integer id) {

		ActionReturn ar = siteService.Delete(id);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/site/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<Site> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<Site> searchResultPage = siteService.Search(search, pageRequest);
		return searchResultPage;
	}
	
	@RequestMapping(value = {"/site/counttracking/{siteid}", "/site/counttracking/{siteid}/{trktype}"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> Get(@PathVariable("siteid") Integer siteid, @PathVariable("trktype") Optional<String> trktype) {
		
		ActionReturn ar = siteService.Get(siteid, trktype);
		
		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/clonesite", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Clone(@RequestBody CommonSite site) {

		ActionReturn ar = siteService.Clone(site);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
}
