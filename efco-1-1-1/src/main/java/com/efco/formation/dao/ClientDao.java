package com.efco.formation.dao;

import com.efco.formation.model.Client;

public interface ClientDao {
	void insertClient(Client client);
	Client getClientByUserId(int client_id);
}
