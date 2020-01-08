package com.MDMREST.dao.mdm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.SiteStatictics;
import com.MDMREST.entity.mdm.Summary;

public interface SiteStaticticsRepository extends JpaRepository<SiteStatictics, Integer> {
	
	@Query("select new com.MDMREST.entity.mdm.Summary(sum(o.total_downloaded), sum(o.total_upgraded)) from SiteStatictics as o where o.siteid = :siteid and o.trktype = :trktype")
	Summary makeSumOnTrkType(@Param("siteid") Integer siteid, @Param("trktype") String trktype);
	
	@Query("select new com.MDMREST.entity.mdm.Summary(sum(o.total_downloaded), sum(o.total_upgraded)) from SiteStatictics as o where o.siteid = :siteid")
	Summary makeSumOnAllType(@Param("siteid") Integer siteid);
}