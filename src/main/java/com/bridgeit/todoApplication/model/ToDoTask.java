package com.bridgeit.todoApplication.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class ToDoTask  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="any", strategy="increment")
	@GeneratedValue(generator="any")
	private int id;
	private String title;
	private String description;
	
	private Date reminder;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_Id")
	private User user;
	
	public ToDoTask() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}	
}
