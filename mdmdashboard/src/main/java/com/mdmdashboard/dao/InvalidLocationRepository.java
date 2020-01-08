package com.mdmdashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdmdashboard.entity.InvalidLocation;

public interface InvalidLocationRepository extends JpaRepository<InvalidLocation, Long>{
	
}