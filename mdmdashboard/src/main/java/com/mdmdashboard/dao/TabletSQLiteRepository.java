package com.mdmdashboard.dao;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mdmdashboard.entity.TabletSQLite;
import com.mdmdashboard.entity.TabletTotalDetail;

public interface TabletSQLiteRepository extends JpaRepository<TabletSQLite, Long>{
	@Query("select distinct new com.mdmdashboard.entity.TabletTotalDetail(o.devserialnumber) from TabletSQLite as o where date(o.updatetime) = :reportdate")
	Page<TabletTotalDetail> findTabletsByDate(@Param("reportdate") Date reportdate, Pageable pageRequest);
	@Query("select distinct new com.mdmdashboard.entity.TabletTotalDetail(o.devserialnumber) from TabletSQLite as o where date(o.updatetime) < CURRENT_DATE - 14")
	Page<TabletTotalDetail> findTabletsByOtherDate(Pageable pageRequest);

}