package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.SessionFormation;


public interface SessionFormationService extends JpaRepository<SessionFormation, Long> {
	//	List<SessionFormation> getAllFormationSession();
}
