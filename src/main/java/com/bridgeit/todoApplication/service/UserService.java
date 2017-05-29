package com.bridgeit.todoApplication.service;

import java.util.List;

import com.bridgeit.todoApplication.model.User;
public interface UserService {

	
	void addEntity(User user) throws Exception;

	User getEntityById(long id) throws Exception;

	List<User> getEntityList() throws Exception;

	void deleteEntity(long id) throws Exception;

	User authUser(String email, String password);

	User getEntityByEmailId(String email);

}
