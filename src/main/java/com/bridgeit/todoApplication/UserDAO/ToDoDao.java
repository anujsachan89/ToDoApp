package com.bridgeit.todoApplication.UserDAO;
import java.util.List;

import org.hibernate.HibernateException;

import com.bridgeit.todoApplication.model.ToDoTask;


public interface ToDoDao {

	void addToDoTask(ToDoTask todo) throws HibernateException;

	List<ToDoTask> getToDoListByUserId(long id) throws Exception;

	void deleteTaskByTODoId(int taskId) throws Exception;
	
	List<ToDoTask> getArchivedTOdoTask(long id) throws Exception;

} 

















