package com.bridgeit.todoApplication.UserDAO;

import java.util.List;

import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.model.UserPicture;

public interface UserDAO {
	
		
		
		void addEntity(User user) throws Exception;

		User getEntityById(long id) throws Exception;

		List<User> getUserList() throws Exception;

		void deleteEntity(long id) throws Exception;

		User authUser(String email, String password);

		User getEntityByEmailId(String email);

		void savePicture(UserPicture picture);
		UserPicture getPicture(long userId);

	}
	
	
	


