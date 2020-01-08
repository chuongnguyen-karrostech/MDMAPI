package com.mdmdashboard.service;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdmdashboard.dao.WifiCellularUsageRepository;
import com.mdmdashboard.entity.WifiCellularUsage;
import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;

@Service
public class WifiCellularUsageService {
	@Autowired
	WifiCellularUsageRepository wcuseRepo;
	
	public ActionReturn addwificellularuse (List<WifiCellularUsage> lst) {
		String flag = CommonMessage.True;
		Integer index = 0;
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (WifiCellularUsage wcuse : lst) {
			try {
				wcuseRepo.saveAndFlush(wcuse);
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