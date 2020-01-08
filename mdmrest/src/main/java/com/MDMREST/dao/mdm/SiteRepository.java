package com.MDMREST.dao.mdm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.Site;

public interface SiteRepository extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site> {

	@Query("select o from Site as o where o.sitename = :sitename")
	List<Site> findOneByName(@Param("sitename") String sitename);
	
	List<Site> findBySitename(String sitename);

	@Query("select o from Site as o where o.siteid <> :siteid and o.sitename = :sitename")
	List<Site> findOneByName(@Param("sitename") String sitename, @Param("siteid") Integer siteid);
	
	@Query("select o from Site as o where o.sitetenanid = :sitetenanid")
	List<Site> findOneByTenan(@Param("sitetenanid") String sitetenanid);

	@Query("select o from Site as o where o.siteid <> :siteid and o.sitetenanid = :sitetenanid")
	List<Site> findOneByTenan(@Param("sitetenanid") String sitetenanid, @Param("siteid") Integer siteid);
	
	@Query("select o from Site as o where o.siteid = :siteid")
	Site findOneBySiteid(@Param("siteid") Integer siteid);
}