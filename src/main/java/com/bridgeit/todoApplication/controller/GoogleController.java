package com.bridgeit.todoApplication.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgeit.todoApplication.Social.Google;
import com.bridgeit.todoApplication.model.GmailProfile;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.TokenService;
import com.bridgeit.todoApplication.service.UserService;

@Controller
@RequestMapping("/")
public class GoogleController{
	
	@Autowired
	private UserService userservice;
	@Autowired
	private TokenService tokenservice;
	
	
@RequestMapping(value="/loginwithgoogle")
public void loginWithGmail(HttpServletRequest request, HttpServletResponse response) throws IOException{
	
	System.out.println("Inside login with google");
	
	Google gmail = new Google();
	String lsr = request.getRequestURL().toString();
	String appUrl = lsr.substring(0, lsr.lastIndexOf("/"));
	String unId=UUID.randomUUID().toString();
	request.getSession().setAttribute("STATE", unId);
	String gmailUrl=gmail.getGmailUrl(appUrl,unId);
	response.sendRedirect(gmailUrl);
	
	return;
}
@RequestMapping(value="/postgmailLogin")
public void postGmailLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	System.out.println("Inside redirect url from google");
	
	String sessionState = (String) request.getSession().getAttribute("STATE");
	String stateFromGmail=request.getParameter("state");
	if(sessionState==null || !sessionState.equals(stateFromGmail)){
		
		response.sendRedirect("loginwithgoogle");
		return;
}
	
	String error = request.getParameter("error");
	
	if(error !=null && error.trim().isEmpty()){
		
		response.sendRedirect("login");
		return;
	}
	
	String authCode = request.getParameter("code");
	
	String lsr = request.getRequestURL().toString();
	String appUrl = lsr.substring(0, lsr.lastIndexOf("/") );
	
	Google gmail = new Google();
	GmailProfile profile = gmail.authUser(authCode, appUrl);
	
	System.out.println(profile.getName());
	User user = userservice.getEntityByEmailId( profile.getEmail() );
	if(user==null){
		
		user = new User();
		//user.setFullnName(profile.getName().getGivenName());
		
		//user.setPicture(profile.getImage().getUrl());
		//user.setEmail(profile.getEmails().get(0).getValue());
		
		userservice.addEntity(user);
	}
	String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
	String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");

	HttpSession session = request.getSession();
	session.setAttribute("user", user);
	response.sendRedirect(appUrl + "/#!/home/todo");
	return;
}
}