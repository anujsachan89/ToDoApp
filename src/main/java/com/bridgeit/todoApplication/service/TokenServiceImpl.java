package com.bridgeit.todoApplication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.todoApplication.UserDAO.TokenDao;
import com.bridgeit.todoApplication.model.Token;


@Service
public class TokenServiceImpl implements TokenService {

	@Autowired 
	private TokenDao tokendao;
	
	public Token getTokenByRefToken(String refreshToken) {
		return tokendao.getRefreshToken(refreshToken);
	}

	public void addToken(Token tokenNew) {

		tokendao.addToken(tokenNew);
	}

	public Token getAccessTokenByAcc(String accessToken) {
		
		return tokendao.getAccessTokenByAccess(accessToken);
	}

}