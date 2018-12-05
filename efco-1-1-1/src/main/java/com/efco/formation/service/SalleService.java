package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.Salle;

public interface SalleService extends JpaRepository<Salle, Long> {

}
