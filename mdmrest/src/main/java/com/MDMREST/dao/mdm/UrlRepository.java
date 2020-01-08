package com.MDMREST.dao.mdm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.Url;
import java.lang.Integer;
import java.lang.String;

public interface UrlRepository extends JpaRepository<Url, Integer>, JpaSpecificationExecutor<Url> {

	//@Query("select o from Url as o where  o.siteid = :siteid and lower(o.urltype) = lower(:urltype) and o.urlversion > :urlversion")
	//Url GetHigherVersion(@Param("urlversion") String urlversion, @Param("urltype") String urltype, @Param("siteid") Integer siteid);
	
	@Query("select o from Url as o where  o.siteid = :siteid and lower(o.urltype) = lower(:urltype)")
	Url GetLastVersionUrlOfSite(@Param("urltype") String urltype, @Param("siteid") Integer siteid);
	
	
	@Query("select o from Url as o where  o.siteid = :siteid and lower(o.urltype) = lower(:urltype)")
	Url CheckSiteType(@Param("urltype") String urltype, @Param("siteid") Integer siteid);
	
	@Query("select o from Url as o where  o.urlid <> :urlid and o.siteid = :siteid and lower(o.urltype) = lower(:urltype)")
	Url CheckSiteType(@Param("urlid") Integer urlid, @Param("urltype") String urltype, @Param("siteid") Integer siteid);
}