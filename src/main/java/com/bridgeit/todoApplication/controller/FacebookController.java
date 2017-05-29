package com.bridgeit.todoApplication.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgeit.todoApplication.Social.Facebook;
import com.bridgeit.todoApplication.model.FacebookProfile;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.UserService;

@Controller
@RequestMapping("/")
public class FacebookController {
	@Autowired
	private UserService userservice;

	@RequestMapping(value="/loginwithfacebook")
	public void loginWithFB(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Facebook fb = new Facebook();

		String lsr = request.getRequestURL().toString();
		String appUrl = lsr.substring(0, lsr.lastIndexOf("/") );

		String unId  = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unId);

		String fbUrl = fb.getFBUrl( appUrl, unId );

		// redirect user to FB
		response.sendRedirect( fbUrl );
		return;
	}
	@RequestMapping(value="/postfacebooklogin")
	public void postFBLogin(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String stateFromFB = request.getParameter("state");
		if( sessionState == null || !sessionState.equals(stateFromFB) )
		{
			System.out.println("State is empty");
			// Redirect to FB again or show error to user
			response.sendRedirect("loginwithfacebook");
			return;
		}

		String error = request.getParameter("error");
		if( error!=null && error.trim().isEmpty())
		{
			response.sendRedirect("login");
			return;
		}

		String authCode = request.getParameter("code");
		String lsr = request.getRequestURL().toString();
		String appUrl = lsr.substring(0, lsr.lastIndexOf("/") );

		Facebook fb = new Facebook();
		FacebookProfile profile = fb.authUser(authCode, appUrl);

		User user = userservice.getEntityByEmailId( profile.getEmail()  );
		if( user == null)
		{
			user = new User();
			user.setEmail(profile.getEmail());
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
