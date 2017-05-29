package com.bridgeit.todoApplication.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.todoApplication.Json.ErrorResponse;
import com.bridgeit.todoApplication.Json.LoginResponse;
import com.bridgeit.todoApplication.Json.Response;
import com.bridgeit.todoApplication.Json.TokenResponse;
import com.bridgeit.todoApplication.Social.Facebook;
import com.bridgeit.todoApplication.model.FacebookProfile;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.UserService;


@Controller
@RequestMapping("/")
public class LoginController 
{

	@Autowired
	private UserService userservice;
	
	
	static final Logger log = Logger.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Response getUserById(@RequestBody Map<String, String> loginMap, HttpServletRequest request,
			HttpServletResponse response) 
	{
		User user = null;

		HttpSession session = request.getSession();
		try
		{
			user = userservice.authUser(loginMap.get("email"), loginMap.get("password"));
			log.info("user autorised and exist in DB");
			
		} 
		catch (Exception e) 
		{
			
			ErrorResponse serror = new ErrorResponse();
			serror.setStatus(-1);
			serror.setMessage("Internal server error, please try again.");
			log.info("Internal Servor error");
			return serror;
		}

		if (user == null)
		{

			ErrorResponse eerror = new ErrorResponse();
			eerror.setStatus(-1);
			eerror.setMessage("Invalid credential, Please check email or password");
			log.warn("Invalid password or Email");
			return eerror;
		}
		
		session.setAttribute("user", user);
		LoginResponse lerror = new LoginResponse();
		lerror.setStatus(1);
		lerror.setMessage("User logged succesfully");
		TokenResponse terror = new TokenResponse();
		terror.getAccessToken();
		terror.getRefreshToken();
		terror.setStatus(1);

		
		return terror;
	}
}