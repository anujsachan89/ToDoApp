package com.bridgeit.todoApplication.model;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class UserPicture {
	
	@Id
	@GenericGenerator(name = "any", strategy="assigned")
	int userid;
	
	byte [] file;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public byte [] getFile() {
		return file;
	}
	public void setFile(byte [] file) {
		this.file = file;
	}
}
