package com.MDMREST.service.mdm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.transaction.annotation.Transactional;

import com.MDMREST.dao.mdm.TabletRepository;
import com.MDMREST.entity.mdm.Tablet;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.util.RestAction;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class TabletService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	TabletRepository tabletRepo;

	public Page<Tablet> Get(Pageable pageRequest) {
		return tabletRepo.findAll(pageRequest);
	}
	
	public Page<Tablet> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return tabletRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}

	public ActionReturn Update(Tablet tablet) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(tablet, RestAction.Update);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Tablet updated = tabletRepo.saveAndFlush(tablet);
			flag = String.valueOf(updated.getTabletid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Add(Tablet tablet) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(tablet, RestAction.Add);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Tablet added = tabletRepo.saveAndFlush(tablet);
			flag = String.valueOf(added.getTabletid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Delete(Integer tabletid) {
		String flag = CommonMessage.False;

		try {

			String message = CheckValidate(new Tablet(tabletid), RestAction.Delete);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			tabletRepo.delete(tabletid);
			flag = String.valueOf(tabletid);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public String CheckValidate(Tablet tablet, RestAction action) {

		String message = "";

		switch (action) {
		case Add:
			if (tablet.getTabletserialno() != null && tabletRepo.findBySerialNo(tablet.getTabletserialno()) != null) {
				message = "Tablet serial number already exists";
			}
			break;
		case Update:
			if (tabletRepo.findOne(tablet.getTabletid()) == null) {
				message = "Tablet does not exist to update";
			} else if (tablet.getTabletserialno() != null
					&& tabletRepo.findBySerialNo(tablet.getTabletserialno(), tablet.getTabletid()) != null) {
				message = "Tablet serial number already exists";
			}
			break;
		case Delete:
			if (tabletRepo.findOne(tablet.getTabletid()) == null) {
				message = "Tablet does not exist to delete";
			}
			break;
		}

		return message.trim();
	}
	
	public String CheckDataIntergation(DataIntegrityViolationException d) {

		PSQLException a = (PSQLException) d.getCause().getCause();
		String error = a.getMessage();
		if (error.toLowerCase().contains("site_id") && error.toLowerCase().contains("is not present in table \"site\"")) {
			error = "Site id does not exist";			
		}
		return error;
	}
	
	public Specification<Tablet> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<Tablet>() {
				@Override
				public Predicate toPredicate(Root<Tablet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<Tablet>() {
			@Override
			public Predicate toPredicate(Root<Tablet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
	
	@Transactional
	public ActionReturn moveBatch(Integer siteid, List<Integer> lsttabletid) {
		String flag = CommonMessage.True;
		Integer index = 0;
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (Integer tabletid : lsttabletid) {
			try {

				em.createNamedQuery("update_tablet")
						.setParameter("siteid", siteid)				
						.setParameter("tabletid", tabletid)						                
						.executeUpdate();
				//map.put(index, String.format(CommonMessage.getMessage(""), o));

			} catch (ConstraintViolationException v) {

				map.put(index, (new ActionReturn(CommonMessage.False, v)).object.toString());
				v.printStackTrace();

			} catch (Exception e) {

				map.put(index, (new ActionReturn(CommonMessage.False, e)).object.toString());
				e.printStackTrace();
			}
			//index++;
		}

		return new ActionReturn(flag, map);
	}
}