package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.Stagiaire;

@Repository
public interface StagiareService extends JpaRepository<Stagiaire, Long> {


}
