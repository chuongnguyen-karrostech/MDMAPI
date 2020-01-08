package com.mdmdashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mdmdashboard.util.message.ActionReturn;
import com.mdmdashboard.util.message.CommonMessage;
import com.mdmdashboard.dao.TabletSQLiteRepository;
import com.mdmdashboard.dao.TabletTotalRepository;
import com.mdmdashboard.entity.TabletSQLite;
import com.mdmdashboard.entity.TabletTotal;
import com.mdmdashboard.entity.TabletTotalDetail;

@Service
public class TabletSQLiteService {
	
	@Autowired
	TabletSQLiteRepository tabletRepo;
	
	@Autowired
	TabletTotalRepository tablettotalRepo;
	
	public ActionReturn addtablets(List<TabletSQLite> lst) {
		String flag = CommonMessage.True;
		Integer index = 0;
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (TabletSQLite tablet : lst) {
			try {
				tabletRepo.saveAndFlush(tablet);
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
	
	public List<TabletTotal> counttablets() {
		return tablettotalRepo.findAll();
	}
	
	public Page<TabletTotalDetail> listtablet(String repdate, Pageable pageRequest) throws ParseException{
		if (repdate.equalsIgnoreCase("other"))
			return tabletRepo.findTabletsByOtherDate(pageRequest);
		else
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dtrepdate = formatter.parse(repdate);
			return tabletRepo.findTabletsByDate(dtrepdate, pageRequest);
		}
	}
	
}