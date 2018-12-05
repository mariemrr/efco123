package com.efco.formation.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.User;


public interface UserServiceCrud  extends JpaRepository<User, Long> {

}
