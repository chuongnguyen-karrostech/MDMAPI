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

import com.mdmdashboard.service.PunchLogService;
import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;
import com.mdmdashboard.entity.PunchLog;
import com.mdmdashboard.entity.PunchOffline;
import com.mdmdashboard.entity.PunchOfflineDetail;
import com.mdmdashboard.entity.PunchDifferent;
import com.mdmdashboard.entity.PunchDifferentDetail;

@RestController
@RequestMapping(value = "/dashboard")
@CrossOrigin(origins = "*")

public class PunchLogController{
	@Autowired
	PunchLogService punchService;
	
	@RequestMapping(value = "/punchlog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addpunchlog(@RequestBody List<PunchLog> data) {
		ActionReturn ar = punchService.addpunchlogs(data);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/punchlog/onoffline/{fromdate}/{todate}", method = RequestMethod.GET)
	@ResponseBody
	public List<PunchOffline> countonoff(@PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) throws ParseException {
		return punchService.countonoffs(fromdate, todate);
	}
	
	@RequestMapping(value = "/punchlog/different/{fromdate}/{todate}", method = RequestMethod.GET)
	@ResponseBody
	public List<PunchDifferent> countdiff(@PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) throws ParseException {
		return punchService.countdiff(fromdate, todate);
	}
	
	@RequestMapping(value = "/punchlog/onoffline/detail/{onoff}/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public Page<PunchOfflineDetail> listonoffdetail(@PathVariable("onoff") Integer onoff, @PathVariable("repdate") String repdate, Pageable pageRequest) throws ParseException{
		return punchService.listonoffdetail(onoff, repdate, pageRequest);
	}
	
	@RequestMapping(value = "/punchlog/different/detail/{level}/{repdate}", method = RequestMethod.GET)
	@ResponseBody
	public Page<PunchDifferentDetail> listdiffdetail(@PathVariable("level") Integer level, @PathVariable("repdate") String repdate, Pageable pageRequest) throws ParseException {
		return punchService.listdiffdetail(level, repdate, pageRequest);
	}
	
}
