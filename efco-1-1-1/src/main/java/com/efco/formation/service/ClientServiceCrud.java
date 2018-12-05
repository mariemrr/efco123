package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efco.formation.model.Client;
@Repository
public interface ClientServiceCrud extends JpaRepository<Client, Long>{

}
