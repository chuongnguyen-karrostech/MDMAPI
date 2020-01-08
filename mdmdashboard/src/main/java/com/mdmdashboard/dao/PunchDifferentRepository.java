package com.mdmdashboard.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mdmdashboard.entity.PunchDifferent;

public interface PunchDifferentRepository extends JpaRepository<PunchDifferent, Date>{
	
	List<PunchDifferent> findByReportdateBetween(@Param("fromdate") Date fromdate, @Param("todate") Date todate);

}