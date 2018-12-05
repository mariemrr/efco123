package com.efco.formation.service;

import com.efco.formation.model.Client;

public interface ClientService {
	void insertClient(Client client);
	Client getClientByUserId(int user_id);
}
