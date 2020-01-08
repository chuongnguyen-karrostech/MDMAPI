package com.mdmdashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdmdashboard.entity.WifiCellularUsage;

public interface WifiCellularUsageRepository extends JpaRepository<WifiCellularUsage, Long>{
	
}