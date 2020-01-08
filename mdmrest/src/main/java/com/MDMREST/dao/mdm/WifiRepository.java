package com.MDMREST.dao.mdm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MDMREST.entity.mdm.Wifi;

public interface WifiRepository extends JpaRepository<Wifi, Integer>, JpaSpecificationExecutor<Wifi> {
	
	@Query("select o from Wifi as o where o.siteid = :siteid and o.wifissid = :wifissid")
	List<Wifi> findBySiteId(@Param("siteid") Integer siteid, @Param("wifissid") String wifissid);
	
	@Query("select o from Wifi as o where o.wifiid <> :wifiid and o.siteid = :siteid and o.wifissid = :wifissid")
	List<Wifi> findBySiteId(@Param("siteid") Integer siteid, @Param("wifiid") Integer wifiid, @Param("wifissid") String wifissid);
}