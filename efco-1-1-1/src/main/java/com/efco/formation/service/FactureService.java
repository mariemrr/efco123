package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.Facture;
@Repository
public interface FactureService extends JpaRepository<Facture,Long>{

}
