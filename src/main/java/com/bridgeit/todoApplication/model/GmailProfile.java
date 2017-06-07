package com.bridgeit.todoApplication.model;


import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GmailProfile {
	
	private String id;
	private List<GmailEmails> emails;
	private GmailName name;
	private GmailImage image;
	private String email;
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<GmailEmails> getEmails() {
		return emails;
	}
	public void setEmails(List<GmailEmails> emails) {
		this.emails = emails;
	}
	public GmailName getName() {
		return name;
	}
	public void setName(GmailName name) {
		this.name = name;
	}
	public GmailImage getImage() {
		return image;
	}
	public void setImage(GmailImage image) {
		this.image = image;
	}
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}
	
}

