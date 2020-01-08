package com.MDMREST.dao.mdm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.MDMREST.entity.mdm.OSMAPK;

public interface OSMAPKRepository extends JpaRepository<OSMAPK, Long> {	
	@Query("select o from OSMAPK as o where o.trkinfo.trktabletserial = :trktabletserial")
	OSMAPK GetTrackingBySerailNo(@Param("trktabletserial") String trktabletserial);
	
	@Query("select o from OSMAPK as o where o.trkinfo.trktabletserial = :trktabletserial")
	Page<OSMAPK> findAllByTabletSerial(@Param("trktabletserial") String trktabletserial, Pageable pageable);	
}