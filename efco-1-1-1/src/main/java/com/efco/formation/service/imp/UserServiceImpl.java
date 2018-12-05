package com.efco.formation.service.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efco.formation.dao.UserDao;
import com.efco.formation.model.User;
import com.efco.formation.service.UserService;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);

	}

	@Override
	public void insertUsers(List<User> users) {
		// TODO Auto-generated method stub
		userDao.insertUsers(users);

	}


	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.getAllUsers();
		//return null;
	}



	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return userDao.getUserById(userId);
		//	return null;
	}


	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return userDao.getUserByEmailAndPassword(email, password);
		//return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.getUserByEmail(email);
		//return null;
	}

	@Override
	public List<User> getUsersByRole(int role_id) {
		// TODO Auto-generated method stub
		return userDao.getUsersByRole(role_id);
		//return null;
	}

	@Override
	public User acceptUser(int user_id,String password) {
		// TODO Auto-generated method stub
		return userDao.acceptUser(user_id,password);
		//return null;
	}

	@Override
	public User deleteUser(int user_id) {
		// TODO Auto-generated method stub
		userDao.deletetUser(user_id);
		return null;
	}

	@Override
	public void deleteStag(long stag_id) {
		// TODO Auto-generated method stub
		userDao.deleteStag(stag_id);

	}




}