package com.MDMREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MDMREST.entity.mdm.Wifi;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.service.mdm.WifiService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/wifi")
@CrossOrigin(origins = "*")
public class WifiController {

	@Autowired
	WifiService wifiService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Page<Wifi> Get(Pageable pageRequest) {
		Page<Wifi> searchResultPage = wifiService.Get(pageRequest);
		return searchResultPage;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(@RequestBody Wifi wifi) {

		ActionReturn ar = wifiService.Add(wifi);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody Wifi wifi, @PathVariable("id") Integer id) {

		wifi.setWifiid(id);
		ActionReturn ar = wifiService.Update(wifi);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("id") Integer id) {

		ActionReturn ar = wifiService.Delete(id);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<Wifi> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<Wifi> searchResultPage = wifiService.Search(search, pageRequest);
		return searchResultPage;
	}
}
