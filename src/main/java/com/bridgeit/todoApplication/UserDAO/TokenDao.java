package com.bridgeit.todoApplication.UserDAO;

import com.bridgeit.todoApplication.model.Token;

public interface TokenDao {

	Token getRefreshToken(String refreshToken);

	void addToken(Token tokenNew);

	Token getAccessTokenByAccess(String accessToken);

}