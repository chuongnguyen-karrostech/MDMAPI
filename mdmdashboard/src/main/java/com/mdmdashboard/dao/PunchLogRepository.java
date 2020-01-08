package com.mdmdashboard.dao;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mdmdashboard.entity.PunchDifferentDetail;
import com.mdmdashboard.entity.PunchLog;
import com.mdmdashboard.entity.PunchOfflineDetail;

public interface PunchLogRepository extends JpaRepository<PunchLog, Long>{

	@Query("select new com.mdmdashboard.entity.PunchOfflineDetail(o.devserialnumber, o.punchtime, o.sendtime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.sendtime)<=60")
	Page<PunchOfflineDetail> findOnlineTablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchOfflineDetail(o.devserialnumber, o.punchtime, o.sendtime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.sendtime)>60")
	Page<PunchOfflineDetail> findOfflineTablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchDifferentDetail(o.devserialnumber, o.punchtime, o.internaltime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)<=60")
	Page<PunchDifferentDetail> findLevel1Tablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchDifferentDetail(o.devserialnumber, o.punchtime, o.internaltime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)>60 and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)<=300")
	Page<PunchDifferentDetail> findLevel2Tablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchDifferentDetail(o.devserialnumber, o.punchtime, o.internaltime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)>300 and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)<=1800")
	Page<PunchDifferentDetail> findLevel3Tablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchDifferentDetail(o.devserialnumber, o.punchtime, o.internaltime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)>1800 and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)<=3600")
	Page<PunchDifferentDetail> findLevel4Tablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
	
	@Query("select new com.mdmdashboard.entity.PunchDifferentDetail(o.devserialnumber, o.punchtime, o.internaltime) from PunchLog as o where date(o.punchtime) = :reportdate and extract(EPOCH from o.punchtime) - extract(EPOCH from o.internaltime)>3600")
	Page<PunchDifferentDetail> findLevel5Tablets(@Param("reportdate") Date reportdate, Pageable pageRequest);
}