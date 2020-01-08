package com.MDMREST.service.mdm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.MDMREST.dao.mdm.WifiRepository;
import com.MDMREST.entity.mdm.Wifi;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.util.RestAction;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class WifiService {

	@Autowired
	WifiRepository wifiRepo;

	public Page<Wifi> Get(Pageable pageRequest) {
		return wifiRepo.findAll(pageRequest);
	}

	public ActionReturn Update(Wifi wifi) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(wifi, RestAction.Update);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Wifi updated = wifiRepo.saveAndFlush(wifi);
			flag = String.valueOf(updated.getWifiid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Add(Wifi wifi) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(wifi, RestAction.Add);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Wifi added = wifiRepo.saveAndFlush(wifi);
			flag = String.valueOf(added.getWifiid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Delete(Integer wifiid) {
		String flag = CommonMessage.False;

		try {

			String message = CheckValidate(new Wifi(wifiid), RestAction.Delete);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			wifiRepo.delete(wifiid);
			flag = String.valueOf(wifiid);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}
		
		return new ActionReturn(flag);
	}

	public String CheckValidate(Wifi wifi, RestAction action) {

		String message = "";

		switch (action) {
		case Add:
			if (wifi.getSiteid() != null && wifiRepo.findBySiteId(wifi.getSiteid(), wifi.getWifissid()).size() > 0) {
				message = "SSID already exists for selected site.";
			} else
			if(!wifi.getWifisecuritytype().toLowerCase().equals("open") && wifi.getWifipassword().trim().isEmpty()) {				
				message = "Missing wifi password.";
			} else if(wifi.getWifisecuritytype().toLowerCase().equals("open") && !wifi.getWifipassword().trim().isEmpty()) {				
				message = "Please clear password when security type is Open.";
			}
			break;
		case Update:
			if (wifiRepo.findOne(wifi.getWifiid()) == null) {
				message = "Wifi configuration does not exist to update";
			} else 
			if (wifi.getSiteid() != null && wifiRepo.findBySiteId(wifi.getSiteid(), wifi.getWifiid(), wifi.getWifissid()).size() > 0) {
				message = "SSID already exists for selected site.";
			} else 
			if(!wifi.getWifisecuritytype().toLowerCase().equals("open") && wifi.getWifipassword().trim().isEmpty()) {				
				message = "Missing wifi password.";
			} else if(wifi.getWifisecuritytype().toLowerCase().equals("open") && !wifi.getWifipassword().trim().isEmpty()) {				
				message = "Please clear password when security type is Open.";
			}
			break;
		case Delete:
			if (wifiRepo.findOne(wifi.getWifiid()) == null) {
				message = "Wifi configuration does not exist to delete";
			}
			break;
		}

		return message.trim();
	}

	public String CheckDataIntergation(DataIntegrityViolationException d) {

		PSQLException a = (PSQLException) d.getCause().getCause();
		String error = a.getMessage();
		if (error.toLowerCase().contains("site_id")
				&& error.toLowerCase().contains("is not present in table \"site\"")) {
			error = "Site id does not exist";
		}
		return error;
	}
	
	public Page<Wifi> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return wifiRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<Wifi> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<Wifi>() {
				@Override
				public Predicate toPredicate(Root<Wifi> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<Wifi>() {
			@Override
			public Predicate toPredicate(Root<Wifi> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject search : lstsearch) {
					Predicate condition = search.BuildCondition(root, cb);
					if (condition != null)
						predicates.add(condition);
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}

}