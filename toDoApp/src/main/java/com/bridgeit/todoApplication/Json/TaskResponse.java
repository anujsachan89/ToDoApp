package com.bridgeit.todoApplication.Json;

import java.util.List;

import com.bridgeit.todoApplication.model.ToDoTask;

public class TaskResponse extends Response {
	private ToDoTask doTask;
	
	private List<ToDoTask> list;

	public ToDoTask getDoTask() {
		return doTask;
	}

	public void setDoTask(ToDoTask doTask) {
		this.doTask = doTask;
	}

	public List<ToDoTask> getList() {
		return list;
	}

	public void setList(List<ToDoTask> list) {
		this.list = list;
	}
	
	
}