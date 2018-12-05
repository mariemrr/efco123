package com.efco.formation.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efco.formation.dao.ClientDao;
import com.efco.formation.model.Client;
import com.efco.formation.service.ClientService;
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDao clientDao;

	@Override
	public void insertClient(Client client) {
		// TODO Auto-generated method stub
		clientDao.insertClient(client);

	}

	@Override
	public Client getClientByUserId(int user_id) {
		// TODO Auto-generated method stub

		return clientDao.getClientByUserId(user_id);
	} 
}
