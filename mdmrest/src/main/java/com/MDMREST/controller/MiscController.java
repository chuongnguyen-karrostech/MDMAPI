package com.MDMREST.controller;

import java.util.Collections;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.MDMREST.entity.mdm.LinkAddress;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@RequestMapping(value = "/")
@CrossOrigin(origins = "*")

public class MiscController {
	@RequestMapping(value = "/callapi", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Callapi(@RequestBody LinkAddress apipath) {
		
		// GET request
		if (apipath.getbody().toString().isEmpty()) {
			try {
				UriComponentsBuilder getbuilder = UriComponentsBuilder
				    .fromUriString(apipath.geturl());  
				RestTemplate getrestTemplate = new RestTemplate();
				Object getresponse = getrestTemplate.getForObject(getbuilder.toUriString(), Object.class);
				return new ResponseEntity<>(new ActionReturn(CommonMessage.True, getresponse), HttpStatus.OK);
				
			} catch (ConstraintViolationException v) {
				return new ResponseEntity<>(new ActionReturn(CommonMessage.False, v), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				return new ResponseEntity<>(new ActionReturn(CommonMessage.False, e), HttpStatus.BAD_REQUEST);
			}
		}
		// POST request
		else {
			// create headers
		    HttpHeaders headers = new HttpHeaders();
		    // set `accept` header
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    // build the request
		    HttpEntity<?> entity = new HttpEntity<Object>(apipath.getbody(), headers);

		    // use `exchange` method for HTTP call
		    UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(apipath.geturl());  
			RestTemplate restTemplate = new RestTemplate();
		    
		    ResponseEntity<?> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Object.class);
		    
		    if(response.getStatusCode() == HttpStatus.OK) {
		        return new ResponseEntity<>(new ActionReturn(CommonMessage.True, response.getBody()), HttpStatus.OK);
		    } else {
		    	return new ResponseEntity<>(new ActionReturn(CommonMessage.False, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
		    }
		}
	}

}