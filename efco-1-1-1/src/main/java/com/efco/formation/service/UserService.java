package com.efco.formation.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.efco.formation.model.User;


public interface UserService {

	int insertUser(User user);

	void insertUsers(List<User> users);

	List<User> getAllUsers();

	User getUserById(int userId);

	User getUserByEmailAndPassword(String email,String password);

	User getUserByEmail(String email);

	List<User> getUsersByRole(int role_id);

	User acceptUser(int user_id,String password);

	User deleteUser(int user_id);
	
	void deleteStag(long stag_id);

}
