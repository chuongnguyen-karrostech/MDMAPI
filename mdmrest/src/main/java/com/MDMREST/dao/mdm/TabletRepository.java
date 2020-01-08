package com.MDMREST.dao.mdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.Tablet;

public interface TabletRepository extends JpaRepository<Tablet, Integer>, JpaSpecificationExecutor<Tablet> {

	@Query("select o from Tablet as o where o.tabletserialno = :tabletserialno")
	Tablet findBySerialNo(@Param("tabletserialno") String tabletserialno);

	@Query("select o from Tablet as o where o.tabletid <> :tabletid and o.tabletserialno = :tabletserialno")
	Tablet findBySerialNo(@Param("tabletserialno") String tabletserialno, @Param("tabletid") Integer tabletid);

}