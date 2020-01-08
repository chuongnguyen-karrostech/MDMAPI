package com.MDMREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MDMREST.entity.mdm.Tablet;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.service.mdm.TabletService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/tablet")
@CrossOrigin(origins = "*")
public class TabletController {

	@Autowired
	TabletService tabletService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Page<Tablet> Get(Pageable pageRequest) {
		Page<Tablet> searchResultPage = tabletService.Get(pageRequest);
		return searchResultPage;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Add(@RequestBody Tablet tablet) {

		ActionReturn ar = tabletService.Add(tablet);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody Tablet tablet, @PathVariable("id") Integer id) {

		tablet.setTabletid(id);
		ActionReturn ar = tabletService.Update(tablet);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> Delete(@PathVariable("id") Integer id) {

		ActionReturn ar = tabletService.Delete(id);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<Tablet> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<Tablet> searchResultPage = tabletService.Search(search, pageRequest);
		return searchResultPage;
	}
	
	@RequestMapping(value = "/move/{siteid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> Move(@PathVariable("siteid") Integer siteid, @RequestBody List<Integer> lsttabletid) {

		ActionReturn ar = tabletService.moveBatch(siteid, lsttabletid);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
}
