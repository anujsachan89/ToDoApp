package com.bridgeit.todoApplication.service;
import java.util.List;

import org.hibernate.HibernateException;

import com.bridgeit.todoApplication.model.ToDoTask;


public interface ToDoService {

	void addToDoTask(ToDoTask todo) throws HibernateException;

	List<ToDoTask> getToDoList(long l)throws Exception;

	void deleteTaskByToDoId(int taskId)throws Exception;

}
