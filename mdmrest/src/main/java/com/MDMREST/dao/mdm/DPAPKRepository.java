package com.MDMREST.dao.mdm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.DPAPK;

public interface DPAPKRepository extends JpaRepository<DPAPK, Long> {	
	@Query("select o from DPAPK as o where o.trkinfo.trktabletserial = :trktabletserial")
	DPAPK GetTrackingBySerailNo(@Param("trktabletserial") String trktabletserial);
	
	@Query("select o from DPAPK as o where o.trkinfo.trktabletserial = :trktabletserial")
	Page<DPAPK> findAllByTabletSerial(@Param("trktabletserial") String trktabletserial, Pageable pageable);	
}