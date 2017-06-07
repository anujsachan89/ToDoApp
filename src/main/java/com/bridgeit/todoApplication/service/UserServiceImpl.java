package com.bridgeit.todoApplication.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todoApplication.UserDAO.UserDAO;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.model.UserPicture;


public class UserServiceImpl implements UserService{
	public UserServiceImpl() {}
	@Autowired
	UserDAO userDAO;


	
private static final AtomicLong counter = new AtomicLong();
	public void addEntity(User user) throws Exception {
		userDAO.addEntity(user);

	}
	public User getEntityById(int id) throws Exception{
		return userDAO.getEntityById(id);
	}

	public List<User> getEntityList() throws Exception {
		return userDAO.getUserList();
	}

	public void deleteEntity(int id) throws Exception {
		userDAO.deleteEntity(id);
	}

	public User authUser(String email, String password) {
		return userDAO.authUser(email, password);
	}
	public User getEntityByEmailId(String email) {
		return userDAO.getEntityByEmailId(email);
	}


	public void savePicture(UserPicture picture){
		userDAO.savePicture(picture);
		
	}

	public UserPicture getPicture(int userId)
	{
		return userDAO.getPicture(userId);
	}
	
	}

