package com.MDMREST.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MDMREST.entity.mdm.Tracking;
import com.MDMREST.entity.mdm.TrackingView;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.service.mdm.TrackingService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/tracking")
@CrossOrigin(origins = "*")
public class TrakingController {

	@Autowired
	TrackingService trkService;

	@RequestMapping(value = {"/{type}", "/{type}/{tabletserialno}"}, method = RequestMethod.GET)
	@ResponseBody
	public Page<?> Get(@PathVariable("type") String type, @PathVariable("tabletserialno") Optional<String> tabletserialno, Pageable pageRequest) {

		Page<?> searchResultPage = trkService.Get(type, pageRequest, tabletserialno);
		return searchResultPage;
	}

	@RequestMapping(value = "/{type}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(HttpServletRequest request, @PathVariable("type") String type, @RequestBody Tracking trackingobject) {

		String userAgent = request.getHeader("user-agent");
		
		ActionReturn ar = trkService.Add(userAgent, type, trackingobject);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{type}/{tabletserialno}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("type") String type,@PathVariable("tabletserialno") String tabletserialno) {

		Long deleteId = trkService.DeleteTracking(type, tabletserialno);
		if(deleteId != null && deleteId.equals(Long.MAX_VALUE))
			return new ResponseEntity<>(new ActionReturn(CommonMessage.False), HttpStatus.BAD_REQUEST);		
		else
			return new ResponseEntity<>(new ActionReturn(CommonMessage.True, deleteId), HttpStatus.OK);
			
	}
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public Page<TrackingView> Get(Pageable pageRequest) {
		Page<TrackingView> searchResultPage = trkService.GetAll(pageRequest);
		return searchResultPage;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<TrackingView> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<TrackingView> searchResultPage = trkService.Search(search, pageRequest);
		return searchResultPage;
	}
}
