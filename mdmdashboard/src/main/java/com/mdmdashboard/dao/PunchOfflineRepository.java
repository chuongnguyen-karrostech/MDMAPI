package com.mdmdashboard.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mdmdashboard.entity.PunchOffline;

public interface PunchOfflineRepository extends JpaRepository<PunchOffline, Date>{
	
	List<PunchOffline> findByReportdateBetween(@Param("fromdate") Date fromdate, @Param("todate") Date todate);

}