package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.Formation;

@Repository
public interface FormationService  extends JpaRepository<Formation, Long> {

}
