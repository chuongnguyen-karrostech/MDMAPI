package com.MDMREST.dao.mdm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.DPMAP;

public interface DPMAPAPKRepository extends JpaRepository<DPMAP, Long> {	
	@Query("select o from DPMAP as o where o.trkinfo.trktabletserial = :trktabletserial")
	DPMAP GetTrackingBySerailNo(@Param("trktabletserial") String trktabletserial);
	
	@Query("select o from DPMAP as o where o.trkinfo.trktabletserial = :trktabletserial")
	Page<DPMAP> findAllByTabletSerial(@Param("trktabletserial") String trktabletserial, Pageable pageable);	
}