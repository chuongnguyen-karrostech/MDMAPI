package com.MDMREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MDMREST.entity.mdm.LinkAddress;
import com.MDMREST.entity.mdm.Url;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.service.mdm.UrlService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/url")
@CrossOrigin(origins = "*")
public class UrlController {

	@Autowired
	UrlService urlService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Page<Url> Get(Pageable pageRequest) {
		Page<Url> searchResultPage = urlService.Get(pageRequest);
		return searchResultPage;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(@RequestBody Url url) {

		ActionReturn ar = urlService.Add(url);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody Url url, @PathVariable("id") Integer id) {

		url.setUrlid(id);
		ActionReturn ar = urlService.Update(url);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("id") Integer id) {

		ActionReturn ar = urlService.Delete(id);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<Url> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<Url> searchResultPage = urlService.Search(search, pageRequest);
		return searchResultPage;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Check(@RequestBody LinkAddress urlpath) {

		ActionReturn ar = urlService.Check(urlpath);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
}
