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
import com.mdmdashboard.dao.PunchDifferentRepository;
import com.mdmdashboard.dao.PunchLogRepository;
import com.mdmdashboard.dao.PunchOfflineRepository;
import com.mdmdashboard.entity.PunchDifferent;
import com.mdmdashboard.entity.PunchDifferentDetail;
import com.mdmdashboard.entity.PunchLog;
import com.mdmdashboard.entity.PunchOffline;
import com.mdmdashboard.entity.PunchOfflineDetail;

@Service
public class PunchLogService {
	
	@Autowired
	PunchLogRepository punchRepo;
	
	@Autowired
	PunchOfflineRepository punchoffRepo;
	
	@Autowired
	PunchDifferentRepository punchdiffRepo;
	
	public ActionReturn addpunchlogs(List<PunchLog> lst) {
		String flag = CommonMessage.True;
		Integer index = 0;
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (PunchLog punch : lst) {
			try {
				punchRepo.saveAndFlush(punch);
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
	
	public List<PunchOffline> countonoffs(String fromdate, String todate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dtFrdate = formatter.parse(fromdate);
		Date dtTodate = formatter.parse(todate);
		return punchoffRepo.findByReportdateBetween(dtFrdate, dtTodate);
	}
	
	public List<PunchDifferent> countdiff(String fromdate, String todate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dtFrdate = formatter.parse(fromdate);
		Date dtTodate = formatter.parse(todate);
		return punchdiffRepo.findByReportdateBetween(dtFrdate, dtTodate);
	}
	
	public Page<PunchOfflineDetail> listonoffdetail(Integer onoff, String repdate, Pageable pageRequest)  throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dtrepdate = formatter.parse(repdate);
		
		if (onoff==1) {
			return punchRepo.findOnlineTablets(dtrepdate, pageRequest);
		}
		return punchRepo.findOfflineTablets(dtrepdate, pageRequest);
	}
	
	public Page<PunchDifferentDetail> listdiffdetail(Integer level, String repdate, Pageable pageRequest)  throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dtrepdate = formatter.parse(repdate);
		
		if (level==1) {
			return punchRepo.findLevel1Tablets(dtrepdate, pageRequest);
		} else if (level==2) {
			return punchRepo.findLevel2Tablets(dtrepdate, pageRequest);
		} else if (level==3) {
			return punchRepo.findLevel3Tablets(dtrepdate, pageRequest);
		} else if (level==4) {
			return punchRepo.findLevel4Tablets(dtrepdate, pageRequest);
		} else {
			return punchRepo.findLevel5Tablets(dtrepdate, pageRequest);
			
		}
	}
	
}