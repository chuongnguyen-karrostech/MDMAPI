package com.MDMREST.dao.mdm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.OSMMAP;

public interface OSMMAPAPKRepository extends JpaRepository<OSMMAP, Long> {
	@Query("select o from OSMMAP as o where o.trkinfo.trktabletserial = :trktabletserial")
	OSMMAP GetTrackingBySerailNo(@Param("trktabletserial") String trktabletserial);
	
	@Query("select o from OSMMAP as o where o.trkinfo.trktabletserial = :trktabletserial")
	Page<OSMMAP> findAllByTabletSerial(@Param("trktabletserial") String trktabletserial, Pageable pageable);
}