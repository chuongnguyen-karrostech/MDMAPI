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

import com.MDMREST.dao.mdm.LogRepository;
import com.MDMREST.entity.mdm.Log;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.util.DateFunction;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class LogService {
	
	
	@Autowired
	LogRepository logRepo;

	public Page<Log> Get(Pageable pageRequest) {
		return logRepo.findAll(pageRequest);
	}
	
	public ActionReturn Add(Log log) {
		String flag = CommonMessage.False;
		try {			
			log.setLogcreateddate(DateFunction.getCurrentDateTime());
			Log added = logRepo.saveAndFlush(log);
			flag = String.valueOf(added.getLogid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public String CheckDataIntergation(DataIntegrityViolationException d) {
		PSQLException a = (PSQLException) d.getCause().getCause();
		String error = a.getMessage();		
		return error;
	}

	public ActionReturn Delete(Long logid) {
		String flag = CommonMessage.False;

		try {

			if (logRepo.findOne(logid) == null) {
				return new ActionReturn(CommonMessage.False, "Log is not exist to delete");
			}
			
			logRepo.delete(logid);
			flag = String.valueOf(logid);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}
	
	public Page<Log> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return logRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<Log> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<Log>() {
				@Override
				public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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