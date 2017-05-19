package com.bridgeit.todoApplication.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.todoApplication.UserDAO.ToDoDao;
import com.bridgeit.todoApplication.model.ToDoTask;

@Service
public class ToDoServiceImpl implements ToDoService{

	@Autowired
	private ToDoDao tododao;
	
	public void addToDoTask(ToDoTask todo) throws HibernateException {
		tododao.addToDoTask(todo);
	}

	public List<ToDoTask> getToDoList(long id) throws Exception {
		return tododao.getToDoListByUserId(id);
	}

	public void deleteTaskByToDoId(int taskId) throws Exception {
		tododao.deleteTaskByTODoId(taskId);
	}

}