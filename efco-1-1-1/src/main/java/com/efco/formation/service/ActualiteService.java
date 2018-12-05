package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.Actualite;


@Repository
public interface ActualiteService extends JpaRepository<Actualite, Long> {

}
