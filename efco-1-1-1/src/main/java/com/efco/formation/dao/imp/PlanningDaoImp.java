package com.efco.formation.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.efco.formation.dao.PlanningDao;
import com.efco.formation.model.Planning;
@Repository
public class PlanningDaoImp extends JdbcDaoSupport implements PlanningDao{

	@Autowired 
	DataSource dataSource;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	@Override
	public List<Planning> getAllPlannings() {
		// TODO Auto-generated method stub
		return null;
	}




}
