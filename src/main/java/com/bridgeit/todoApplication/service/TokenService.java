package com.bridgeit.todoApplication.service;

import com.bridgeit.todoApplication.model.Token;

public interface TokenService {

	Token getTokenByRefToken(String refreshToken);

	void addToken(Token tokenNew);

	Token getAccessTokenByAcc(String accessToken);

}