package com.bridgeit.todoApplication.UserDAO;

import java.util.List;

import com.bridgeit.todoApplication.model.User;

public interface UserDAO {
	
		
		
		void addEntity(User user) throws Exception;

		User getEntityById(int id) throws Exception;

		List<User> getUserList() throws Exception;

		void deleteEntity(int id) throws Exception;

		User authUser(String email, String password);

		User getEntityByEmailId(String email);

	}
	
	
	


