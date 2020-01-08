package com.mdmdashboard.service;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdmdashboard.dao.InvalidLocationRepository;
import com.mdmdashboard.entity.InvalidLocation;
import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;

@Service
public class InvalidLocationService {
	@Autowired
	InvalidLocationRepository invlocRepo;
	
	public ActionReturn addinvalidlocation (List<InvalidLocation> lst) {
		String flag = CommonMessage.True;
		Integer index = 0;
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (InvalidLocation invloc : lst) {
			try {
				invlocRepo.saveAndFlush(invloc);
				//map.put(index, String.format(CommonMessage.getMessage(""), o));
			} catch (ConstraintViolationException v) {

				map.put(index, (new ActionReturn(CommonMessage.False, v)).object.toString());
				v.printStackTrace();

			} catch (Exception e) {

				map.put(index, (new ActionReturn(CommonMessage.False, e)).object.toString());
				e.printStackTrace();
			}
			index++;
		}

		return new ActionReturn(flag, map);
	}
}