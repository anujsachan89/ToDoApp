package com.bridgeit.todoApplication.controller;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.todoApplication.Json.ErrorResponse;
import com.bridgeit.todoApplication.Json.LoginResponse;
import com.bridgeit.todoApplication.Json.Response;
import com.bridgeit.todoApplication.Json.TokenResponse;
import com.bridgeit.todoApplication.model.Token;
import com.bridgeit.todoApplication.service.TokenService;
import com.bridgeit.todoApplication.service.UserService;



@Controller
@RequestMapping("/")
public class TokenController {

	@Autowired
	private UserService userservice;

	@Autowired
	private TokenService tokenservice;

	@RequestMapping(value = "generateTokens", method = RequestMethod.GET)
	public @ResponseBody Response generateNewTokens(@RequestParam("refresh_token") String refreshToken,
			HttpServletRequest request, HttpServletResponse response) {

		Date currentDate = new Date();

		Token token = tokenservice.getTokenByRefToken(refreshToken);

		if (token == null) {

			ErrorResponse er = new ErrorResponse();
			er.setStatus(-1);
			er.setMessage("Token not found");

			return er;
		}

		Date date = token.getCreatedOn();

		long diff = currentDate.getTime() - date.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff);
		if (diffInSeconds > 60*60) // 1hour
		{
			ErrorResponse er = new ErrorResponse();
			er.setStatus(-1);
			er.setMessage("Refresh Token Expired");

			return er;
		}

		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println("accessToken: " + accessToken);
		String refreshTokenNew = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println("refreshToken: " + refreshTokenNew);

		Token tokenNew = new Token();
		tokenNew.setCreatedOn(new Date());
		tokenNew.setAccessToken(accessToken);
		tokenNew.setRefreshToken(refreshTokenNew);
		tokenNew.setId(token.getId());
		tokenservice.addToken(tokenNew);

		LoginResponse lr = new LoginResponse();
		lr.setStatus(1);
		lr.setMessage("successfully logged in");

		TokenResponse tr = new TokenResponse();
		tr.setAccessToken(tokenNew.getAccessToken());
		tr.setRefreshToken(tokenNew.getRefreshToken());
		tr.setStatus(1);

		Cookie ck = new Cookie("access_token", token.getAccessToken());
		response.addCookie(ck);
		System.out.println(ck.getValue());
		return tr;

	}

}
