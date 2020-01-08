package com.MDMREST.dao.mdm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.MDMREST.entity.mdm.TrackingView;

public interface TrackingViewRepository extends JpaRepository<TrackingView, Long>, JpaSpecificationExecutor<TrackingView> {	
		
}