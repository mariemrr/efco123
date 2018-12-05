package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.Devis;

public interface DevisService extends JpaRepository<Devis, Long> {

}
