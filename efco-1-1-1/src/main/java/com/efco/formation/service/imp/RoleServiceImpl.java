package com.efco.formation.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efco.formation.dao.RoleDao;
import com.efco.formation.model.Role;
import com.efco.formation.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleDao roleDao;
	
	@Override
	public Role getRoleById(int role_id) {
		// TODO Auto-generated method stub
		return roleDao.getRoleById(role_id);
		//return null;
	}

}
