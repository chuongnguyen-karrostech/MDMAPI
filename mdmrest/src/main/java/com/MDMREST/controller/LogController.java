package com.MDMREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MDMREST.entity.mdm.Log;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.service.mdm.LogService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/log")
@CrossOrigin(origins = "*")
public class LogController {

	@Autowired
	LogService logService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Page<Log> Get(Pageable pageRequest) {
		Page<Log> searchResultPage = logService.Get(pageRequest);
		return searchResultPage;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(@RequestBody Log log) {

		ActionReturn ar = logService.Add(log);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("id") Long id) {

		ActionReturn ar = logService.Delete(id);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<Log> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<Log> searchResultPage = logService.Search(search, pageRequest);
		return searchResultPage;
	}
}
