package com.bridgeit.todoApplication.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="any", strategy="increment")
	@GeneratedValue(generator="any")
	private long id;
	private int userid;
	private String accessToken;
	private String refreshToken;
	private Date createdOn;

	public Token() {
	}

	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}