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

import com.bridgeit.todoApplication.Facebook.Facebook;
import com.bridgeit.todoApplication.Json.ErrorResponse;
import com.bridgeit.todoApplication.Json.LoginResponse;
import com.bridgeit.todoApplication.Json.Response;
import com.bridgeit.todoApplication.Json.TokenResponse;
import com.bridgeit.todoApplication.model.FacebookProfile;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.UserService;


@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private UserService userservice;
	

	static final Logger log = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Response getEmployeeById(@RequestBody Map<String, String> loginMap, HttpServletRequest request,
			HttpServletResponse response) {
		User user = null;

		HttpSession session = request.getSession();
		try {
			user = userservice.authUser(loginMap.get("email"), loginMap.get("password"));
			
		} catch (Exception e) {
			
			ErrorResponse er = new ErrorResponse();
			er.setStatus(-1);
			er.setMessage("Internal server error, please try again.");
			return er;
		}

		if (user == null) {

			ErrorResponse er = new ErrorResponse();
			er.setStatus(-1);
			er.setMessage("Invalid credential, Please check email or password");
			return er;
		}
		session.setAttribute("user", user);
		LoginResponse lr = new LoginResponse();
		lr.setStatus(1);
		lr.setMessage("User logged succesfully");
		TokenResponse tr = new TokenResponse();
		tr.getAccessToken();
		tr.getRefreshToken();
		tr.setStatus(1);

		
		return tr;
	}
	
	@RequestMapping(value="/loginwithfacebook")
	public void loginWithFB(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException 
	{
		Facebook fb = new Facebook();
		
		String lsr = pRequest.getRequestURL().toString();
		String appUrl = lsr.substring(0, lsr.lastIndexOf("/") );
		
		String unId  = UUID.randomUUID().toString();
		pRequest.getSession().setAttribute("STATE", unId);
		
		String fbUrl = fb.getFBUrl( appUrl, unId );
		
		// redirect user to FB
		pResponse.sendRedirect( fbUrl );
		return;
	}
	
	@RequestMapping(value="/postfacebooklogin")
	public void postFBLogin(HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception 
	{
		String sessionState = (String) pRequest.getSession().getAttribute("STATE");
		String stateFromFB = pRequest.getParameter("state");
		if( sessionState == null || !sessionState.equals(stateFromFB) )
		{
			System.out.println("State is empty");
			// Redirect to FB again or show error to user
			pResponse.sendRedirect("loginwithfacebook");
			return;
		}
		
		String error = pRequest.getParameter("error");
		if( error!=null && error.trim().isEmpty())
		{
			pResponse.sendRedirect("login");
			return;
		}
		
		String authCode = pRequest.getParameter("code");
		
		String lsr = pRequest.getRequestURL().toString();
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

		HttpSession session = pRequest.getSession();
		
				
		
		
		session.setAttribute("user", user);
		pResponse.sendRedirect(appUrl + "/#!/home/todo");
		return;
	}
}