package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.SousTypeFormation;


public interface SousTypeFormationServiceCrud extends JpaRepository<SousTypeFormation, Long>{

}
