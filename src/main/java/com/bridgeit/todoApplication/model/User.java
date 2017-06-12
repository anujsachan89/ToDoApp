package com.bridgeit.todoApplication.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "TODO_USER")

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="abc",strategy="increment")
	@GeneratedValue(generator="abc")
	@Column
	private long id;
	@Column
	private String fullName;
	@Column
	private String mobile;
	@Column
	private String email;
	@Column
	private String password;

	private String picture;
	
	public User(){
		id=0;
	}

	public User(int id, String fullName, String mobile, String email, String password) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullnName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", mobile=" + mobile + ", email=" + email + ", password="
				+ password + "]";
	}

	public void setPicture(String picture) {
		this.picture = picture;
	
	}

}
