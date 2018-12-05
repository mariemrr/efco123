package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.TypeFormation;
@Repository
public interface TypeFormationService extends JpaRepository<TypeFormation, Long> {

}
