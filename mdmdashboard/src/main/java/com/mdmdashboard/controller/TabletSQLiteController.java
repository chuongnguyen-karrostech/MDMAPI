package com.mdmdashboard.controller;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mdmdashboard.service.TabletSQLiteService;
import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;
import com.mdmdashboard.entity.TabletSQLite;
import com.mdmdashboard.entity.TabletTotal;
import com.mdmdashboard.entity.TabletTotalDetail;

@RestController
@RequestMapping(value = "/dashboard")
@CrossOrigin(origins = "*")

public class TabletSQLiteController{
	@Autowired
	TabletSQLiteService tabletService;
	
	@RequestMapping(value = "/tablet", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addtablet(@RequestBody List<TabletSQLite> data) {
		ActionReturn ar = tabletService.addtablets(data);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tablet/count", method = RequestMethod.GET)
	@ResponseBody
	public List<TabletTotal> counttablet() {
		return tabletService.counttablets();
	}
	
	@RequestMapping(value = "/tablet/detail/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public Page<TabletTotalDetail> listtablet(@PathVariable("repdate") String repdate, Pageable pageRequest) throws ParseException {
		return tabletService.listtablet(repdate, pageRequest);
	}
}
