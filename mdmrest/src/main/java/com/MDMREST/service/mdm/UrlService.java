package com.MDMREST.service.mdm;

import java.net.HttpURLConnection;
import java.net.URL;
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

import com.MDMREST.dao.mdm.UrlRepository;
import com.MDMREST.entity.mdm.LinkAddress;
import com.MDMREST.entity.mdm.Url;
import com.MDMREST.entity.search.SearchObject;
import com.MDMREST.util.RestAction;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@Service
public class UrlService {
	
	String strUrlType = "DRIVERPORTAL,MAPS,OSMAND,OSMANDMAP,DPAPI";
	String valid_urltype_regexp = String.format("^(%s)$", strUrlType.replace(",", "|"));

	@Autowired
	UrlRepository urlRepo;

	public Page<Url> Get(Pageable pageRequest) {
		return urlRepo.findAll(pageRequest);
	}

	public ActionReturn Update(Url url) {
		String flag = CommonMessage.False;
		try {

			String message = CheckValidate(url, RestAction.Update);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Url updated = urlRepo.saveAndFlush(url);
			flag = String.valueOf(updated.getUrlid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Add(Url url) {
		String flag = CommonMessage.False;
		try {
			
			String message = CheckValidate(url, RestAction.Add);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			Url added = urlRepo.saveAndFlush(url);
			flag = String.valueOf(added.getUrlid());

		} catch (ConstraintViolationException v) {

			return new ActionReturn(CommonMessage.False, v);

		} catch (DataIntegrityViolationException d) {

			return new ActionReturn(CommonMessage.False, CheckDataIntergation(d));

		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public ActionReturn Delete(Integer urlid) {
		String flag = CommonMessage.False;

		try {

			String message = CheckValidate(new Url(urlid), RestAction.Delete);

			if (!message.equals(""))
				return new ActionReturn(CommonMessage.False, message);

			urlRepo.delete(urlid);
			flag = String.valueOf(urlid);

		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	public String CheckValidate(Url url, RestAction action) {

		String message = "";

		if(url.getUrltype() != null)
			url.setUrltype(url.getUrltype().toUpperCase());
		
		if (action == RestAction.Add || action == RestAction.Update) {
			if (url.getUrltype() != null && !url.getUrltype().matches(valid_urltype_regexp)) {
				message = "Url type must be one of these values " + strUrlType;
			}
		} 
		
		if(message.isEmpty())
		{
			switch (action) {
			case Add:
				if (url.getUrltype() != null && url.getSiteid() != null
						&& urlRepo.CheckSiteType(url.getUrltype(), url.getSiteid()) != null) {
					message = "Url type is added for this site";
				}
				break;
			case Update:
				if (urlRepo.findOne(url.getUrlid()) == null) {
					message = "Url is not exist to update";
				} else if (url.getUrltype() != null && url.getSiteid() != null
						&& urlRepo.CheckSiteType(url.getUrlid(), url.getUrltype(), url.getSiteid()) != null) {
					message = "Url type is added for this site";
				}
				break;
			case Delete:
				if (urlRepo.findOne(url.getUrlid()) == null) {
					message = "Url does not exist to delete";
				}
				break;
			}
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
	
	public Page<Url> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		return urlRepo.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<Url> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<Url>() {
				@Override
				public Predicate toPredicate(Root<Url> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<Url>() {
			@Override
			public Predicate toPredicate(Root<Url> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
	
	public ActionReturn Check(LinkAddress urlpath) {
		String flag = CommonMessage.False;
		try {
			flag = String.valueOf(checkURL(urlpath.geturl()));
			
		} catch (Exception e) {
			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}

	private boolean checkURL(String urlpath)
	{		
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(urlpath).openConnection();
			con.setRequestMethod("HEAD");
			con.addRequestProperty("user-agent", "PostmanRuntime/7.15.2");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		catch (Exception e) {
		   e.printStackTrace();
		   return false;
		}	
	}

}