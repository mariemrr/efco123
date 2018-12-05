package com.efco.formation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efco.formation.model.User;


public interface UserDao {
	int insertUser(User cus);
	void insertUsers(List<User> users);
	List<User> getAllUsers();
	User getUserById(int userId);
	User getUserByEmailAndPassword(String email,String password);
	User getUserByEmail(String email);
	List<User> getUsersByRole(int role_id);
	User acceptUser(int user_id,String password);
	User deletetUser(int user_id);
	void deleteStag(Long stag_id);

}