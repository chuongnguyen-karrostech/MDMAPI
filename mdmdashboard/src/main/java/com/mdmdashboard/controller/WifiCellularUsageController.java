package com.mdmdashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mdmdashboard.entity.WifiCellularUsage;
import com.mdmdashboard.service.WifiCellularUsageService;
import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/dashboard")
@CrossOrigin(origins = "*")

public class WifiCellularUsageController {
	@Autowired
	WifiCellularUsageService mcuseService;
	
	@RequestMapping(value = "/wificellular", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addwificellularuse(@RequestBody List<WifiCellularUsage> data) {
		ActionReturn ar = mcuseService.addwificellularuse(data);

		if (ar.result.equals(CommonMessage.False))
			return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(ar, HttpStatus.OK);
	}
}